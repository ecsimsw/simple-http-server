package nextstep.jwp.http.response;

import nextstep.jwp.http.cookie.HttpCookie;
import nextstep.jwp.http.session.HttpSession;

public class HttpResponse {

    // TODO :: RESPONSE 에 너무 엮이는 것은 아닐까

    private static final String HTTP_STATUS_LINE_FORMAT = "HTTP/1.1 %s %s ";
    private static final String HEADER_FORMAT = "%s: %s ";
    private static final String SEPARATE_LINE = "";

    private final ResponseHeaders responseHeaders = new ResponseHeaders();
    private HttpStatus httpStatus;
    private String messageBody = "";

    public void addHeader(String key, String value) {
        responseHeaders.addAttribute(key, value);
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setContent(String content, String contentType) {
        responseHeaders.addAttribute("Content-Type", contentType);
        responseHeaders.addAttribute("Content-Length", content.getBytes().length);

        messageBody = content;
    }

    public void setCookie(HttpCookie cookie) {
        if (cookie.hasSessionId()) {
            addHeader("Cookie", cookie.asResponseLine());
            return;
        }

        HttpSession newSession = new HttpSession();
        String id = newSession.getId();
        addHeader("Set-Cookie", id);
    }

    public byte[] responseAsBytes() {
        return responseAsString().getBytes();
    }

    public String responseAsString() {
        String statusLine = String.format(HTTP_STATUS_LINE_FORMAT, httpStatus.code(), httpStatus.status());
        String headerLine = String.join("\r\n", responseHeaders.asLines(HEADER_FORMAT));
        return String.join("\r\n",
                statusLine,
                headerLine,
                SEPARATE_LINE,
                messageBody);
    }

    public String getHeaderAttribute(String name) {
        return responseHeaders.getAttribute(name);
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }
}
