package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) {

        try(ServerSocket socket = new ServerSocket(DEFAULT_PORT)) {
            while (true) {
                Socket connection = socket.accept();
                RequestHandler requestHandler = new RequestHandler(connection);
                requestHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
