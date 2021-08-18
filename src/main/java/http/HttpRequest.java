package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> params = new HashMap<String, String>();
    private String method;
    private String path;

    public HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            HttpRequestHeader(br);

        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    private void HttpRequestHeader(BufferedReader br) throws IOException {
        String line = br.readLine();

        if (line == null) {
            return;
        }

        log.debug("requestLine : {}", line);
        String[] tokens = line.split(" ");
        method = tokens[0];
        path = tokens[1];

        // GET인 경우
        int index = path.indexOf("?");

        // ?가 없을 경우
        if (index == -1) {
            path = tokens[1];
        } else {
            path = tokens[1].substring(0, index);
            params = HttpRequestUtils.parseQueryString(tokens[1].substring(index + 1));
        }

        // TODO 헤더 값을 다 읽어와야함
        while (!line.equals("")) {
             line = br.readLine();
             String[] headerTokens = line.split(": ");
             if (headerTokens.length == 2) {
                 headers.put(headerTokens[0], headerTokens[1]);
             }
        }
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public String getParameter(String name) {
        return params.get(name);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }
}
