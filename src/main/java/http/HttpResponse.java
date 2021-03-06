package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private DataOutputStream dos = null;
    private Map<String, String> header = new HashMap<String, String>();


    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    private void response200Header(int lengthOfBody) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    public void forward(String url) {
        try {
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            if (url.endsWith(".css")) {
                header.put("Content-Type", "text/css");
            } else if (url.endsWith(".js")) {
                header.put("Content-Type", "application/javascript");
            } else {
                header.put("Content-Type", "text/html;charset=UTF-8");
            }
            header.put("Content-Length", body.length + "");
            response200Header(body.length);
            responseBody(body);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    public void sendRedirect(String fileName) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Content-Type: text/html;charset=UTF-8 \r\n");
            dos.writeBytes("Location: " + fileName + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHeader(String key, String value) {
        header.put(key, value);
    }
}
