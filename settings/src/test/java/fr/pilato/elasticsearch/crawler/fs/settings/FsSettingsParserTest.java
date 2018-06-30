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

package fr.pilato.elasticsearch.crawler.fs.settings;

import fr.pilato.elasticsearch.crawler.fs.framework.Percentage;
import fr.pilato.elasticsearch.crawler.fs.framework.TimeValue;
import fr.pilato.elasticsearch.crawler.fs.test.framework.AbstractFSCrawlerTestCase;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class FsSettingsParserTest extends AbstractFSCrawlerTestCase {

    private static final Ocr OCR_FULL = Ocr.builder().setLanguage("eng").build();

    private static final Fs FS_EMPTY = Fs.builder().build();

    private static final CustomTikaParser CTS = CustomTikaParser.builder()
            .setClassName("org.test.aParser")
            .setPathToJar("./a_parser.jar")
            .setMimeTypes(new ArrayList<>(Arrays.asList("text/json")))
            .build();

    private static final Fs FS_FULL = Fs.builder()
            .setUrl("/path/to/docs")
            .setStoreSource(true)
            .setAddFilesize(true)
            .addExclude("resume.doc")
            .addInclude("*.doc")
            .addInclude("*.xls")
            .setFilenameAsId(true)
            .setIndexedChars(new Percentage(10000))
            .setRemoveDeleted(true)
            .setUpdateRate(TimeValue.timeValueMinutes(5))
            .setIndexContent(true)
            .setOcr(OCR_FULL)
            .setTikaCustomParsers(new ArrayList<>(Arrays.asList(CTS)))
            .build();

    private static final Elasticsearch ELASTICSEARCH_EMPTY = Elasticsearch.builder().build();
    private static final Elasticsearch ELASTICSEARCH_FULL = Elasticsearch.builder()
            .addNode(Elasticsearch.Node.builder()
                    .setHost("127.0.0.1")
                    .setPort(9200)
                    .build())
            .setUsername("elastic")
            .setPassword("changeme")
            .setBulkSize(1000)
            .setFlushInterval(TimeValue.timeValueSeconds(5))
            .setIndex("docs")
            .setPipeline("pipeline-id-if-any")
            .build();
    private static final Server SERVER_EMPTY = Server.builder().build();
    private static final Server SERVER_FULL = Server.builder()
            .setHostname("127.0.0.1")
            .setUsername("dadoonet")
            .setPassword("WhATDidYOUexPECt?")
            .setPort(22)
            .setProtocol("SSH")
            .setPemPath("/path/to/pemfile")
            .build();
    private static final Rest REST_FULL = Rest.builder()
            .setHost("127.0.0.1")
            .setPort(8080)
            .setScheme(Rest.Scheme.HTTP)
            .setEndpoint("fscrawler")
            .build();

    private void settingsTester(FsSettings source) throws IOException {
        String json = FsSettingsParser.toJson(source);

        logger.info("-> testing settings: [{}]", json);
        FsSettings generated = FsSettingsParser.fromJson(json);
        Matcher mmatch = is(source);
        assertThat(generated, mmatch);
    }

    @Test
    public void testWithSimplestJsonJobFile() throws IOException {
        String json = "{ \"name\" : \"test\" }";
        logger.info("-> testing settings: [{}]", json);
        FsSettings settings = FsSettingsParser.fromJson(json);

        // We enrich missing needed values
        FsCrawlerValidator.validateSettings(logger, settings, false);

        // We need to check the default expected settings
        assertThat(settings, notNullValue());
        assertThat(settings.getName(), is("test"));
        assertThat(settings.getServer(), nullValue());
        assertThat(settings.getRest(), nullValue());

        assertThat(settings.getElasticsearch(), notNullValue());
        assertThat(settings.getElasticsearch().getBulkSize(), is(100));
        assertThat(settings.getElasticsearch().getFlushInterval(), is(TimeValue.timeValueSeconds(5)));
        assertThat(settings.getElasticsearch().getIndex(), is("test"));
        assertThat(settings.getElasticsearch().getIndexFolder(), is("test_folder"));
        assertThat(settings.getElasticsearch().getNodes(), iterableWithSize(1));
        assertThat(settings.getElasticsearch().getNodes().get(0).getHost(), is("127.0.0.1"));
        assertThat(settings.getElasticsearch().getNodes().get(0).getPort(), is(9200));
        assertThat(settings.getElasticsearch().getNodes().get(0).getScheme(), is(Elasticsearch.Node.Scheme.HTTP));

        assertThat(settings.getElasticsearch().getUsername(), is(nullValue()));
        assertThat(settings.getElasticsearch().getPassword(), is(nullValue()));
        assertThat(settings.getElasticsearch().getPipeline(), is(nullValue()));

        assertThat(settings.getFs(), notNullValue());
        assertThat(settings.getFs().getChecksum(), nullValue());
        assertThat(settings.getFs().getIncludes(), nullValue());
        assertThat(settings.getFs().getExcludes(), contains("~*"));
        assertThat(settings.getFs().getIndexedChars(), nullValue());
        assertThat(settings.getFs().getUpdateRate(), is(TimeValue.timeValueMinutes(15)));
        assertThat(settings.getFs().getUrl(), is("/tmp/es"));
        assertThat(settings.getFs().isAddFilesize(), is(true));
        assertThat(settings.getFs().isAttributesSupport(), is(false));
        assertThat(settings.getFs().isAddAsInnerObject(), is(false));
        assertThat(settings.getFs().isFilenameAsId(), is(false));
        assertThat(settings.getFs().isIndexContent(), is(true));
        assertThat(settings.getFs().isIndexFolders(), is(true));
        assertThat(settings.getFs().isJsonSupport(), is(false));
        assertThat(settings.getFs().isLangDetect(), is(false));
        assertThat(settings.getFs().isRawMetadata(), is(true));
        assertThat(settings.getFs().isRemoveDeleted(), is(true));
        assertThat(settings.getFs().isStoreSource(), is(false));
        assertThat(settings.getFs().isXmlSupport(), is(false));
    }

    @Test
    public void testParseEmptySettings() throws IOException {
        settingsTester(
                FsSettings.builder(getCurrentTestName())
                        .build()
        );
    }

    @Test
    public void testParseSettingsEmptyFs() throws IOException {
        settingsTester(
                FsSettings.builder(getCurrentTestName())
                        .setFs(FS_EMPTY)
                        .build()
        );
    }

    @Test
    public void testParseSettingsFs() throws IOException {
        settingsTester(
                FsSettings.builder(getCurrentTestName())
                        .setFs(FS_FULL)
                        .build()
        );
    }

    @Test
    public void testParseSettingsEmptyElasticsearch() throws IOException {
        settingsTester(
                FsSettings.builder(getCurrentTestName())
                        .setElasticsearch(ELASTICSEARCH_EMPTY)
                        .build()
        );
    }

    @Test
    public void testParseSettingsElasticsearchOneNode() throws IOException {
        settingsTester(
                FsSettings.builder(getCurrentTestName())
                        .setElasticsearch(Elasticsearch.builder()
                                .addNode(Elasticsearch.Node.builder()
                                        .setHost("127.0.0.1")
                                        .setPort(9200)
                                        .build())
                                .build())
                        .build()
        );
    }

    @Test
    public void testParseSettingsElasticsearchTwoNodes() throws IOException {
        settingsTester(
                FsSettings.builder(getCurrentTestName())
                        .setElasticsearch(Elasticsearch.builder()
                                .addNode(Elasticsearch.Node.builder()
                                        .setHost("127.0.0.1")
                                        .setPort(9200)
                                        .build())
                                .addNode(Elasticsearch.Node.builder()
                                        .setHost("localhost")
                                        .setPort(9201)
                                        .build())
                                .build())
                        .build()
        );
    }

    @Test
    public void testParseSettingsElasticsearchTwoNodesWithHttps() throws IOException {
        settingsTester(
                FsSettings.builder(getCurrentTestName())
                        .setElasticsearch(Elasticsearch.builder()
                                .addNode(Elasticsearch.Node.builder()
                                        .setHost("127.0.0.1")
                                        .setPort(9200)
                                        .build())
                                .addNode(Elasticsearch.Node.builder()
                                        .setHost("localhost")
                                        .setPort(9243)
                                        .setScheme(Elasticsearch.Node.Scheme.HTTPS)
                                        .build())
                                .build())
                        .build()
        );
    }

    @Test
    public void testParseSettingsElasticsearchIndexSettings() throws IOException {
        settingsTester(
                FsSettings.builder(getCurrentTestName())
                        .setElasticsearch(ELASTICSEARCH_FULL)
                        .build()
        );
    }

    @Test
    public void testParseSettingsEmptyServer() throws IOException {
        settingsTester(
                FsSettings.builder(getCurrentTestName())
                        .setServer(SERVER_EMPTY)
                .build()
        );
    }

    @Test
    public void testParseSettingsServer() throws IOException {
        settingsTester(
                FsSettings.builder(getCurrentTestName())
                        .setServer(SERVER_FULL)
                        .build()
        );
    }

    @Test
    public void testParseCompleteSettings() throws IOException {
        settingsTester(
                FsSettings.builder(getCurrentTestName())
                        .setElasticsearch(ELASTICSEARCH_FULL)
                        .setServer(SERVER_FULL)
                        .setFs(FS_FULL)
                        .setRest(REST_FULL)
                        .build()
        );
    }

    @Test
    public void testPasswordIsReadableFromSettings() throws IOException {
        String json = "{\n" +
                "  \"name\" : \"test_password_is_readable\",\n" +
                "  \"fs\" : {\n" +
                "    \"url\" : \"/path/to/docs\"\n" +
                "  },\n" +
                "  \"server\" : {\n" +
                "    \"username\" : \"dadoonet\",\n" +
                "    \"password\" : \"" + SERVER_FULL.getPassword() + "\"\n" +
                "  },\n" +
                "  \"elasticsearch\" : {\n" +
                "    \"username\" : \"username\",\n" +
                "    \"password\" : \"" + ELASTICSEARCH_FULL.getPassword() + "\"\n" +
                "  }\n" +
                "}";

        logger.info("-> testing settings: [{}]", json);
        FsSettings generated = FsSettingsParser.fromJson(json);
        assertThat(generated.getElasticsearch().getPassword(), is(ELASTICSEARCH_FULL.getPassword()));
        assertThat(generated.getServer().getPassword(), is(SERVER_FULL.getPassword()));

        // Generate the JSON and check that we don't render the passwords
        String filteredJson = FsSettingsParser.toJson(generated);
        assertThat(filteredJson, not(containsString(ELASTICSEARCH_FULL.getPassword())));
        assertThat(filteredJson, not(containsString(SERVER_FULL.getPassword())));
    }
}
