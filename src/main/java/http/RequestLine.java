package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestLine {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private Map<String, String> params = new HashMap<String, String>();
    private String method;
    private String path;

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
        if (index == -1) {
            path = tokens[1];
        } else {
            path = tokens[1].substring(0, index);
            params = HttpRequestUtils.parseQueryString(tokens[1].substring(index + 1));
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
