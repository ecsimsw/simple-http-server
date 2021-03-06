package nextstep.jwp.http.response;

public enum ContentType {

    CSS_UTF8("text/css;charset=utf-8"),
    HTML_UTF8("text/html;charset=utf-8"),
    PLAIN_UTF8("text/plain;charset=utf-8"),
    SVG_UTF8("image/svg+xml;charset=utf-8");

    private final String type;

    ContentType(String type) {
        this.type = type;
    }

    public static ContentType fromExtension(String extension) {
        if (extension.equals("css")) {
            return CSS_UTF8;
        }
        if (extension.equals("html")) {
            return HTML_UTF8;
        }
        if (extension.equals("svg")) {
            return SVG_UTF8;
        }
        return PLAIN_UTF8;
    }

    public static ContentType fromFileName(String fileName) {
        if (fileName.contains(".")) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            return ContentType.fromExtension(extension);
        }
        return empty();
    }

    public static ContentType empty() {
        return PLAIN_UTF8;
    }

    public String value() {
        return type;
    }
}
