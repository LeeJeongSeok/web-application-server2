package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private static final Logger log = LoggerFactory.getLogger(WebServer.class);

    public static void main(String[] args) {

        try(ServerSocket socket = new ServerSocket(8080)) {
            while (true) {
                Socket connection = socket.accept();
                Thread thread = new RequestHandler(connection);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
