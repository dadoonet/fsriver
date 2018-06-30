package fr.pilato.elasticsearch.crawler.fs.settings;

import java.util.ArrayList;
import java.util.List;

public class CustomTikaParser {

    private String className = "";
    private String pathToJar = "";
    private ArrayList<String> mimeTypes = new ArrayList<String>();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String className = "";
        private String pathToJar = "";
        private ArrayList<String> mimeTypes = new ArrayList<String>();

        public Builder setClassName(String className) {
            this.className = className;
            return this;
        }

        public Builder setPathToJar(String pathToJar) {
            this.pathToJar = pathToJar;
            return this;
        }

        public Builder setMimeTypes(ArrayList<String> mimeTypes) {
            this.mimeTypes = mimeTypes;
            return this;
        }

        public CustomTikaParser build() {
            return new CustomTikaParser(className, pathToJar, mimeTypes);
        }
    }

    public CustomTikaParser() {

    }

    private CustomTikaParser(String className, String pathToJar, ArrayList<String> mimeTypes) {

        this.className = className;
        this.pathToJar = pathToJar;
        this.mimeTypes = mimeTypes;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPathToJar() {
        return pathToJar;
    }

    public void setPathToJar(String pathToJar) {
        this.pathToJar = pathToJar;
    }

    public List<String> getMimeTypes() {
        return mimeTypes;
    }

    public void setMimeTypes(ArrayList<String> mimeTypes) {
        this.mimeTypes = mimeTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomTikaParser ctp = (CustomTikaParser) o;

        if (className != null ? !className.equals(ctp.className) : ctp.className != null) return false;
        if (pathToJar != null ? !pathToJar.equals(ctp.pathToJar) : ctp.pathToJar != null) return false;
        return mimeTypes != null ? mimeTypes.equals(ctp.mimeTypes) : ctp.mimeTypes == null;

    }

}