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

package fr.pilato.elasticsearch.crawler.fs.framework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FSCrawlerLogger {

    /**
     * This logger is for the console
     */
    private final static Logger consoleLogger = LogManager.getLogger("fscrawler.console");

    /**
     * This logger is used to log information related to documents
     */
    private final static Logger documentLogger = LogManager.getLogger("fscrawler.document");

    /**
     * This logger is used to help writing the test cases
     */
    private final static Logger metadataLogger = LogManager.getLogger("fscrawler.metadata");

    /**
     * Print something to the console
     * @param message   message to print
     * @param params    parameters if any
     */
    public static void console(String message, Object... params) {
        consoleLogger.info(message, params);
    }

    /**
     * Log information in Debug Level about documents
     * @param id        Document ID
     * @param path      Virtual path to the document
     * @param message   Message to display
     */
    public static void documentDebug(String id, String path, String message) {
        documentLogger.debug("[{}][{}] {}", id, path, message);
    }

    /**
     * Log information in Error Level about documents
     * @param id        Document ID
     * @param path      Virtual path to the document
     * @param error     Error to display
     */
    public static void documentError(String id, String path, String error) {
        documentLogger.error("[{}][{}] {}", id, path, error);
    }

    /**
     * Print the metadata in an useful format so they can be easily used in tests
     * @param message   message to print
     * @param params    parameters if any
     */
    public static void metadata(String message, Object... params) {
        metadataLogger.debug(message, params);
    }
}
