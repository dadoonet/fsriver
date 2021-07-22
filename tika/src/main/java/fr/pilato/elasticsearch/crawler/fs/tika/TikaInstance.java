/*
 * Licensed to David Pilato (the "Author") under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Author licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package fr.pilato.elasticsearch.crawler.fs.tika;


import fr.pilato.elasticsearch.crawler.fs.settings.Fs;
import fr.pilato.elasticsearch.crawler.fs.settings.FsSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.config.ServiceLoader;
import org.apache.tika.exception.TikaConfigException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.exception.ZeroByteFileException;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaTypeRegistry;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.ocr.TesseractOCRParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

import static org.apache.tika.langdetect.optimaize.OptimaizeLangDetector.getDefaultLanguageDetector;

/**
 *
 */
public class TikaInstance {

    private static final Logger logger = LogManager.getLogger(TikaInstance.class);

    private static Parser parser;
    private static ParseContext context;
    private static LanguageDetector detector;
    private static boolean ocrActivated = false;

    /* For tests only */
    public static void reloadTika() {
        parser = null;
        context = null;
        ocrActivated = false;
    }

    /**
     * This initialize if needed a parser and a parse context for tika
     * @param fs fs settings
     */
    private static void initTika(Fs fs) {
        initContext(fs);
        initParser(fs);
    }

    private static void initParser(Fs fs) {
        if (parser == null) {
            PDFParser pdfParser = new PDFParser();
            DefaultParser defaultParser;
            TesseractOCRParser ocrParser;

            ocrActivated = fs.getOcr().isEnabled();

            if (ocrActivated) {
                logger.debug("OCR is activated.");
                ocrParser = new TesseractOCRParser();
                if (fs.getOcr().getPath() != null) {
                    logger.debug("Tesseract Path set to [{}].", fs.getOcr().getPath());
                    ocrParser.setTesseractPath(fs.getOcr().getPath());
                }
                if (fs.getOcr().getDataPath() != null) {
                    logger.debug("Tesseract Data Path set to [{}].", fs.getOcr().getDataPath());
                    ocrParser.setTessdataPath(fs.getOcr().getDataPath());
                }
                try {
                    if (ocrParser.hasTesseract()) {
                        logger.debug("OCR strategy for PDF documents is [{}] and tesseract was found.", fs.getOcr().getPdfStrategy());
                        pdfParser.setOcrStrategy(fs.getOcr().getPdfStrategy());
                    } else {
                        logger.debug("But Tesseract is not installed so we won't run OCR.");
                        ocrActivated = false;
                        pdfParser.setOcrStrategy("no_ocr");
                    }
                } catch (TikaConfigException e) {
                    logger.debug("Tesseract is not correctly set up so we won't run OCR. Error is: {}", e.getMessage());
                    logger.debug("Fullstack trace error for Tesseract", e);
                    ocrActivated = false;
                    pdfParser.setOcrStrategy("no_ocr");
                }
            }

            if (ocrActivated) {
                logger.info("OCR is enabled. This might slowdown the process.");
                // We are excluding the pdf parser as we built one that we want to use instead.
                defaultParser = new DefaultParser(
                        MediaTypeRegistry.getDefaultRegistry(),
                        new ServiceLoader(),
                        Collections.singletonList(PDFParser.class));
            } else {
                logger.info("OCR is disabled.");
                TesseractOCRConfig config = context.get(TesseractOCRConfig.class);
                if (config != null) {
                    config.setSkipOcr(true);
                }
                // We are excluding the pdf parser as we built one that we want to use instead
                // and the OCR Parser as it's explicitly disabled.
                defaultParser = new DefaultParser(
                        MediaTypeRegistry.getDefaultRegistry(),
                        new ServiceLoader(),
                        Arrays.asList(PDFParser.class, TesseractOCRParser.class));
            }
            parser = new AutoDetectParser(defaultParser, pdfParser);
        }
    }

    private static void initContext(Fs fs) {
        if (context == null) {
            context = new ParseContext();
            context.set(Parser.class, parser);
            if (ocrActivated) {
                logger.debug("OCR is activated so we need to configure Tesseract in case we have specific settings.");
                TesseractOCRConfig config = new TesseractOCRConfig();
                logger.debug("Tesseract Language set to [{}].", fs.getOcr().getLanguage());
                config.setLanguage(fs.getOcr().getLanguage());
                if (fs.getOcr().getOutputType() != null) {
                    logger.debug("Tesseract Output Type set to [{}].", fs.getOcr().getOutputType());
                    config.setOutputType(fs.getOcr().getOutputType());
                }
                context.set(TesseractOCRConfig.class, config);
            }
        }
    }

    static String extractText(FsSettings fsSettings, int indexedChars, InputStream stream, Metadata metadata) throws IOException,
            TikaException {
        initTika(fsSettings.getFs());
        WriteOutContentHandler handler = new WriteOutContentHandler(indexedChars);
        try (stream) {
            parser.parse(stream, new BodyContentHandler(handler), metadata, context);
        } catch (SAXException e) {
            throw new TikaException("Unexpected SAX processing failure", e);
        } catch (ZeroByteFileException e) {
            String resourceName = metadata.get("resourceName");
            logger.debug("Got an empty file for {}, so we are just skipping it.", resourceName);
        }
        return handler.toString();
    }

    static LanguageDetector langDetector() {
        if (detector == null) {
            try {
                detector = getDefaultLanguageDetector();
                detector.loadModels();
            } catch (IOException e) {
                logger.warn("Can not load lang detector models", e);
            }
        }
        return detector;
    }
}
