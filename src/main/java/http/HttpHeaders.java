package http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {

    private Map<String, String> headers = new HashMap<String, String>();

    public void add(String line) {
        String[] headerToken = line.split(": ");
        if (headerToken.length == 2) {
            headers.put(headerToken[0], headerToken[1]);
        }
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public int getContentLength() {
        if (headers.get("Content-Length") == null) {
            return 0;
        }
        return Integer.parseInt(headers.get("Content-Length"));
    }
}
