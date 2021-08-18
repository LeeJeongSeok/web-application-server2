package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private HttpHeaders headers;
    private RequestLine requestLine;
    private RequestParam requestParam = new RequestParam();


    public HttpRequest(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            requestLine = new RequestLine(checkRequestLine(br));
            requestParam.setQueryString(requestLine.getQueryString());
            headers = processHeaders(br);
            requestParam.setBody(IOUtils.readData(br, headers.getContentLength()));

        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    private String checkRequestLine(BufferedReader br) throws IOException {
        String line = br.readLine();

        if (line == null) {
            return null;
        }
        return line;
    }

    private HttpHeaders processHeaders(BufferedReader br) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String line;
        while (!(line = br.readLine()).equals("")) {
            headers.add(line);
        }

        return headers;
    }


    public String getPath() {
        return requestLine.getPath();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public String getHeader(String name) {
        return headers.getHeader(name);
    }

    public String getParameter(String name) {
        return requestParam.getParameter(name);
    }
}
