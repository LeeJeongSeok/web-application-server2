package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestLine {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private String method;
    private String path;
    private String queryString;

    public RequestLine(String requestLine) {
        log.debug("requestLine : {}", requestLine);
        String[] tokens = requestLine.split(" ");
        method = tokens[0];

        // POST이면 바로 메소드 종료
        if ("POST".equals(method)) {
            path = tokens[1];
            return;
        }

        int index = tokens[1].indexOf("?");

        // GET이지만 파라미터 값이 없을 경우
        if (index != -1) {
            path = tokens[1].substring(0, index);
            queryString = tokens[1].substring(index + 1);
            return;
        }
        path = tokens[1];
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }
}
