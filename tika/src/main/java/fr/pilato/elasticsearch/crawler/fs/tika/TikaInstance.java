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
import fr.pilato.elasticsearch.crawler.fs.settings.CustomTikaParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.config.ServiceLoader;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MediaTypeRegistry;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParserDecorator;
import org.apache.tika.parser.external.ExternalParser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.ocr.TesseractOCRParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import static org.apache.tika.langdetect.OptimaizeLangDetector.getDefaultLanguageDetector;

/**
 *
 */
public class TikaInstance {

    private static final Logger logger = LogManager.getLogger(TikaInstance.class);

    private static Parser parser;
    private static ParseContext context;
    private static LanguageDetector detector;

    /* For tests only */
    public static void reloadTika() {
        parser = null;
        context = null;
    }

    /**
     * This initialize if needed a parser and a parse context for tika
     * @param fs fs settings
     */
    private static void initTika(Fs fs) {
        initParser(fs);
        initContext(fs);
    }

    private static void initParser(Fs fs) {
        if (parser == null) {
            PDFParser pdfParser = new PDFParser();
            DefaultParser defaultParser;

            if (fs.isPdfOcr()) {
                logger.debug("OCR is activated for PDF documents");
                if (ExternalParser.check("tesseract")) {
                    pdfParser.setOcrStrategy("ocr_and_text");
                } else {
                    logger.debug("But Tesseract is not installed so we won't run OCR.");
                }
                defaultParser = new DefaultParser();
            } else {
                logger.debug("OCR is disabled. Even though it's detected, it must be disabled explicitly");
                defaultParser = new DefaultParser(
                        MediaTypeRegistry.getDefaultRegistry(),
                        new ServiceLoader(),
                        Collections.singletonList(TesseractOCRParser.class));
            }

            // load custom parsers if defined in config
            if (fs.getCustomTikaParsers().size() > 0) {
                Integer counter = 0;
                Parser PARSERS[] = new Parser[fs.getCustomTikaParsers().size()+2];

                // to collect all Mediatypes handled by custom parser to exclude them form the DefaultParser
                List<MediaType> excludeMediaTypes = new ArrayList<MediaType>();


                for (CustomTikaParser customTikaParser : fs.getCustomTikaParsers()) {
                    counter += 1;
                    try {
                        URL[] jarUrl = { new URL("jar:file:" + customTikaParser.getPathToJar()+"!/") };
                        URLClassLoader urlClassLoader = URLClassLoader.newInstance(jarUrl);
                        Class customParserClass = urlClassLoader.loadClass(customTikaParser.getClassName());
                        Parser customParser = (Parser) customParserClass.newInstance();
                        String[] customMimeStrings = new String[customTikaParser.getMimeTypes().size()];
                        Set<MediaType> customMediaTypes = MediaType.set(customTikaParser.getMimeTypes().toArray(customMimeStrings));
                        excludeMediaTypes.addAll(customMediaTypes);
                        Parser customParserDecorated = ParserDecorator.withTypes(customParser, customMediaTypes);
                        PARSERS[counter] = customParserDecorated;

                    } catch (IOException|ClassNotFoundException|InstantiationException|IllegalAccessException e) {
                        logger.error("Caught {}: {}", e.getClass().getSimpleName(), e.getMessage());
                    }
                }
                if (excludeMediaTypes.size() > 0) {
                    MediaType[] excludedMediaTypeSet = new MediaType[excludeMediaTypes.size()];
                    PARSERS[0] = ParserDecorator.withoutTypes(defaultParser, new HashSet<MediaType>(excludeMediaTypes));
                } else {
                    PARSERS[0] = defaultParser;
                }
                PARSERS[counter+1] = pdfParser;
                parser = new AutoDetectParser(PARSERS);
            } else {
                Parser PARSERS[] = new Parser[2];
                PARSERS[0] = defaultParser;
                PARSERS[1] = pdfParser;
                parser = new AutoDetectParser(PARSERS);
            }

        }

    }

    private static void initContext(Fs fs) {
        if (context == null) {
            context = new ParseContext();
            context.set(Parser.class, parser);
            if (fs.isPdfOcr()) {
                logger.debug("OCR is activated");
                TesseractOCRConfig config = new TesseractOCRConfig();
                if (fs.getOcr().getPath() != null) {
                    config.setTesseractPath(fs.getOcr().getPath());
                }
                if (fs.getOcr().getDataPath() != null) {
                    config.setTessdataPath(fs.getOcr().getDataPath());
                }
                config.setLanguage(fs.getOcr().getLanguage());
                context.set(TesseractOCRConfig.class, config);
            }
        }
    }

    static String extractText(FsSettings fsSettings, int indexedChars, InputStream stream, Metadata metadata) throws IOException,
            TikaException {
        initTika(fsSettings.getFs());
        WriteOutContentHandler handler = new WriteOutContentHandler(indexedChars);
        try {
            parser.parse(stream, new BodyContentHandler(handler), metadata, context);
        } catch (SAXException e) {
            if (!handler.isWriteLimitReached(e)) {
                // This should never happen with BodyContentHandler...
                throw new TikaException("Unexpected SAX processing failure", e);
            }
        } finally {
            stream.close();
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
