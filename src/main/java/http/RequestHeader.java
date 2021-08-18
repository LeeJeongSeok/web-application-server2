package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private static final Logger log = LoggerFactory.getLogger(RequestHeader.class);

    private Map<String, String> headers = new HashMap<String, String>();

    public RequestHeader(BufferedReader br, String headerLine) throws IOException {
        while (!headerLine.equals("")) {
            headerLine = br.readLine();
            String[] headerTokens = headerLine.split(": ");
            if (headerTokens.length == 2) {
                headers.put(headerTokens[0], headerTokens[1]);
            }
        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
