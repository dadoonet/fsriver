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

import fr.pilato.elasticsearch.crawler.fs.framework.ByteSizeValue;
import fr.pilato.elasticsearch.crawler.fs.framework.Percentage;
import fr.pilato.elasticsearch.crawler.fs.framework.TimeValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("SameParameterValue")
public class Fs {
    protected static final Logger logger = LogManager.getLogger(Fs.class);

    private String url;
    private TimeValue updateRate = TimeValue.timeValueMinutes(15);
    private List<String> includes = null;
    private List<String> excludes = null;
    private List<String> filters = null;
    private boolean jsonSupport = false;
    private boolean filenameAsId = false;
    private boolean addFilesize = true;
    private boolean removeDeleted = true;
    private boolean addAsInnerObject = false;
    private boolean storeSource = false;
    private boolean indexContent = true;
    private Percentage indexedChars = null;
    private boolean attributesSupport = false;
    private boolean rawMetadata = false;
    private boolean xmlSupport = false;
    private String checksum = null;
    private boolean indexFolders = true;
    private boolean langDetect = false;
    private boolean continueOnError = false;
    private Ocr ocr = new Ocr();
    private ByteSizeValue ignoreAbove = null;
    private boolean followSymlinks = false;
    private boolean skipTika = false;

    public static Builder builder() {
        return new Builder();
    }

    public static final String DEFAULT_DIR = Paths.get("/tmp/es").toString();
    public static final List<String> DEFAULT_EXCLUDED = Collections.singletonList("*/~*");
    public static final Fs DEFAULT = Fs.builder().setUrl(DEFAULT_DIR).setExcludes(DEFAULT_EXCLUDED).build();

    public static class Builder {
        private String url = DEFAULT_DIR;
        private TimeValue updateRate = TimeValue.timeValueMinutes(15);
        private List<String> includes = null;
        private List<String> excludes = null;
        private List<String> filters = null;
        private boolean jsonSupport = false;
        private boolean filenameAsId = false;
        private boolean addFilesize = true;
        private boolean removeDeleted = true;
        private boolean addAsInnerObject = false;
        private boolean storeSource = false;
        private boolean indexContent = true;
        private Percentage indexedChars = null;
        private boolean attributesSupport = false;
        private boolean rawMetadata = false;
        private String checksum = null;
        private boolean xmlSupport = false;
        private boolean indexFolders = true;
        private boolean langDetect = false;
        private boolean continueOnError = false;
        private Ocr ocr = new Ocr();
        private ByteSizeValue ignoreAbove = null;
        private boolean followSymlinks = false;
        private boolean skipTika = false;

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setUpdateRate(TimeValue updateRate) {
            this.updateRate = updateRate;
            return this;
        }

        public Builder setIncludes(List<String> includes) {
            this.includes = includes;
            return this;
        }

        public Builder addInclude(String include) {
            if (this.includes == null) {
                this.includes = new ArrayList<>();
            }

            // We refuse to add duplicates
            if (!this.includes.contains(include)) {
                this.includes.add(include);
            }

            return this;
        }

        public Builder setExcludes(List<String> excludes) {
            this.excludes = excludes;
            return this;
        }

        public Builder addExclude(String exclude) {
            if (this.excludes == null) {
                this.excludes = new ArrayList<>();
            }

            // We refuse to add duplicates
            if (!this.excludes.contains(exclude)) {
                this.excludes.add(exclude);
            }

            return this;
        }

        public Builder setFilters(List<String> filters) {
            this.filters = filters;
            return this;
        }

        public Builder addFilter(String filter) {
            if (this.filters == null) {
                this.filters = new ArrayList<>();
            }

            // We refuse to add duplicates
            if (!this.filters.contains(filter)) {
                this.filters.add(filter);
            }

            return this;
        }

        public Builder setJsonSupport(boolean jsonSupport) {
            this.jsonSupport = jsonSupport;
            return this;
        }

        public Builder setFilenameAsId(boolean filenameAsId) {
            this.filenameAsId = filenameAsId;
            return this;
        }

        public Builder setAddFilesize(boolean addFilesize) {
            this.addFilesize = addFilesize;
            return this;
        }

        public Builder setRemoveDeleted(boolean removeDeleted) {
            this.removeDeleted = removeDeleted;
            return this;
        }

        public Builder setAddAsInnerObject(boolean addAsInnerObject) {
            this.addAsInnerObject = addAsInnerObject;
            return this;
        }

        public Builder setStoreSource(boolean storeSource) {
            this.storeSource = storeSource;
            return this;
        }

        public Builder setIndexedChars(Percentage indexedChars) {
            this.indexedChars = indexedChars;
            return this;
        }

        public Builder setIndexContent(boolean indexContent) {
            this.indexContent = indexContent;
            return this;
        }

        public Builder setAttributesSupport(boolean attributesSupport) {
            this.attributesSupport = attributesSupport;
            return this;
        }

        public Builder setRawMetadata(boolean rawMetadata) {
            this.rawMetadata = rawMetadata;
            return this;
        }

        public Builder setChecksum(String checksum) {
            this.checksum = checksum;
            return this;
        }

        public Builder setXmlSupport(boolean xmlSupport) {
            this.xmlSupport = xmlSupport;
            return this;
        }

        public Builder setIndexFolders(boolean indexFolders) {
            this.indexFolders = indexFolders;
            return this;
        }

        public Builder setLangDetect(boolean langDetect) {
            this.langDetect = langDetect;
            return this;
        }

        public Builder setContinueOnError(boolean continueOnError) {
            this.continueOnError = continueOnError;
            return this;
        }

        public Builder setOcr(Ocr ocr) {
            this.ocr = ocr;
            return this;
        }

        public Builder setIgnoreAbove(ByteSizeValue ignoreAbove) {
            this.ignoreAbove = ignoreAbove;
            return this;
        }

        public Builder setFollowSymlinks(boolean followSymlinks) {
            this.followSymlinks = followSymlinks;
            return this;
        }

        public Builder setSkipTika(boolean skipTika) {
            this.skipTika = skipTika;
            return this;
        }

        public Fs build() {
            return new Fs(url, updateRate, includes, excludes, filters, jsonSupport, filenameAsId, addFilesize,
                    removeDeleted, addAsInnerObject, storeSource, indexedChars, indexContent, attributesSupport, rawMetadata,
                    checksum, xmlSupport, indexFolders, langDetect, continueOnError, ocr, ignoreAbove, followSymlinks, skipTika);
        }
    }

    public Fs( ) {

    }

    private Fs(String url, TimeValue updateRate, List<String> includes, List<String> excludes, List<String> filters, boolean jsonSupport,
               boolean filenameAsId, boolean addFilesize, boolean removeDeleted, boolean addAsInnerObject, boolean storeSource,
               Percentage indexedChars, boolean indexContent, boolean attributesSupport, boolean rawMetadata, String checksum, boolean xmlSupport,
               boolean indexFolders, boolean langDetect, boolean continueOnError, Ocr ocr, ByteSizeValue ignoreAbove, boolean followSymlinks, boolean skipTika) {
        this.url = url;
        this.updateRate = updateRate;
        this.includes = includes;
        this.excludes = excludes;
        this.filters = filters;
        this.jsonSupport = jsonSupport;
        this.filenameAsId = filenameAsId;
        this.addFilesize = addFilesize;
        this.removeDeleted = removeDeleted;
        this.addAsInnerObject = addAsInnerObject;
        this.storeSource = storeSource;
        this.indexedChars = indexedChars;
        this.indexContent = indexContent;
        this.attributesSupport = attributesSupport;
        this.rawMetadata = rawMetadata;
        this.checksum = checksum;
        this.xmlSupport = xmlSupport;
        this.indexFolders = indexFolders;
        this.langDetect = langDetect;
        this.continueOnError = continueOnError;
        this.ocr = ocr;
        this.ignoreAbove = ignoreAbove;
        this.followSymlinks = followSymlinks;
        this.skipTika = skipTika;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TimeValue getUpdateRate() {
        return updateRate;
    }

    public void setUpdateRate(TimeValue updateRate) {
        this.updateRate = updateRate;
    }

    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public List<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public boolean isJsonSupport() {
        return jsonSupport;
    }

    public void setJsonSupport(boolean jsonSupport) {
        this.jsonSupport = jsonSupport;
    }

    public boolean isFilenameAsId() {
        return filenameAsId;
    }

    public void setFilenameAsId(boolean filenameAsId) {
        this.filenameAsId = filenameAsId;
    }

    public boolean isAddFilesize() {
        return addFilesize;
    }

    public void setAddFilesize(boolean addFilesize) {
        this.addFilesize = addFilesize;
    }

    public boolean isRemoveDeleted() {
        return removeDeleted;
    }

    public void setRemoveDeleted(boolean removeDeleted) {
        this.removeDeleted = removeDeleted;
    }

    public boolean isAddAsInnerObject() {
        return addAsInnerObject;
    }

    public void setAddAsInnerObject(boolean addAsInnerObject) {
        this.addAsInnerObject = addAsInnerObject;
    }

    public boolean isStoreSource() {
        return storeSource;
    }

    public void setStoreSource(boolean storeSource) {
        this.storeSource = storeSource;
    }

    public Percentage getIndexedChars() {
        return indexedChars;
    }

    public void setIndexedChars(Percentage indexedChars) {
        this.indexedChars = indexedChars;
    }

    public boolean isIndexContent() {
        return indexContent;
    }

    public void setIndexContent(boolean indexContent) {
        this.indexContent = indexContent;
    }

    public boolean isAttributesSupport() {
        return attributesSupport;
    }

    public void setAttributesSupport(boolean attributesSupport) {
        this.attributesSupport = attributesSupport;
    }

    public boolean isRawMetadata() {
        return rawMetadata;
    }

    public void setRawMetadata(boolean rawMetadata) {
        this.rawMetadata = rawMetadata;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public boolean isXmlSupport() {
        return xmlSupport;
    }

    public void setXmlSupport(boolean xmlSupport) {
        this.xmlSupport = xmlSupport;
    }

    public boolean isIndexFolders() {
        return indexFolders;
    }

    public void setIndexFolders(boolean indexFolders) {
        this.indexFolders = indexFolders;
    }

    public boolean isLangDetect() {
        return langDetect;
    }

    public void setLangDetect(boolean langDetect) {
        this.langDetect = langDetect;
    }

    public boolean isContinueOnError() {
        return continueOnError;
    }

    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }

    @Deprecated
    public void setPdfOcr(boolean pdfOcr) {
        String strategy;
        if (pdfOcr) {
            strategy = "ocr_and_text";
        } else {
            strategy = "no_ocr";
        }
        logger.warn("pdf_ocr setting has been deprecated and is replaced by ocr.pdf_strategy: {}.", strategy);
        if (this.ocr == null) {
            this.ocr = new Ocr();
        }

        this.ocr.setPdfStrategy(strategy);
    }

    public Ocr getOcr() {
        return ocr;
    }

    public void setOcr(Ocr ocr) {
        this.ocr = ocr;
    }

    public ByteSizeValue getIgnoreAbove() {
        return ignoreAbove;
    }

    public void setIgnoreAbove(ByteSizeValue ignoreAbove) {
        this.ignoreAbove = ignoreAbove;
    }

    public boolean isSkipTika() {
        return skipTika;
    }

    public boolean isFollowSymlinks() {
        return followSymlinks;
    }

    public void setFollowSymlinks(boolean followSymlinks) {
        this.followSymlinks = followSymlinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fs fs = (Fs) o;
        return jsonSupport == fs.jsonSupport &&
                filenameAsId == fs.filenameAsId &&
                addFilesize == fs.addFilesize &&
                removeDeleted == fs.removeDeleted &&
                addAsInnerObject == fs.addAsInnerObject &&
                storeSource == fs.storeSource &&
                indexContent == fs.indexContent &&
                attributesSupport == fs.attributesSupport &&
                rawMetadata == fs.rawMetadata &&
                xmlSupport == fs.xmlSupport &&
                indexFolders == fs.indexFolders &&
                langDetect == fs.langDetect &&
                continueOnError == fs.continueOnError &&
                followSymlinks == fs.followSymlinks &&
                Objects.equals(url, fs.url) &&
                Objects.equals(updateRate, fs.updateRate) &&
                Objects.equals(includes, fs.includes) &&
                Objects.equals(excludes, fs.excludes) &&
                Objects.equals(filters, fs.filters) &&
                Objects.equals(indexedChars, fs.indexedChars) &&
                Objects.equals(checksum, fs.checksum) &&
                Objects.equals(ocr, fs.ocr) &&
                Objects.equals(ignoreAbove, fs.ignoreAbove);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, updateRate, includes, excludes, filters, jsonSupport, filenameAsId, addFilesize,
                removeDeleted, addAsInnerObject, storeSource, indexContent, indexedChars, attributesSupport, rawMetadata, xmlSupport,
                checksum, indexFolders, langDetect, continueOnError, ocr, ignoreAbove, followSymlinks);
    }

    @Override
    public String toString() {
        return "Fs{" + "url='" + url + '\'' +
                ", updateRate=" + updateRate +
                ", includes=" + includes +
                ", excludes=" + excludes +
                ", filters=" + filters +
                ", jsonSupport=" + jsonSupport +
                ", filenameAsId=" + filenameAsId +
                ", addFilesize=" + addFilesize +
                ", removeDeleted=" + removeDeleted +
                ", addAsInnerObject=" + addAsInnerObject +
                ", storeSource=" + storeSource +
                ", indexContent=" + indexContent +
                ", indexedChars=" + indexedChars +
                ", attributesSupport=" + attributesSupport +
                ", rawMetadata=" + rawMetadata +
                ", xmlSupport=" + xmlSupport +
                ", checksum='" + checksum + '\'' +
                ", indexFolders=" + indexFolders +
                ", langDetect=" + langDetect +
                ", continueOnError=" + continueOnError +
                ", ocr=" + ocr +
                ", ignoreAbove=" + ignoreAbove +
                ", followSymlinks=" + followSymlinks +
                ", skipTika=" + skipTika +
                '}';
    }
}
