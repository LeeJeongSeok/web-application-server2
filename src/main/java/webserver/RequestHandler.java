package webserver;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;


public class RequestHandler extends Thread{

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리를 구현
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);

            if (request.getPath().equals("/user/create")) {
                User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));
                log.debug("User info : {}", user);
                DataBase.addUser(user);
                response.sendRedirect("/index.html");
            } else if (request.getPath().equals("/user/login")) {
                User user = DataBase.findUserById(request.getParameter("userId"));
                if (user == null) {
                    log.debug("유저를 찾을 수 없습니다.");
                    response.addHeader("Set-Cookie", "logined=false");
                    response.sendRedirect("/user/login_failed.html");
                } else if (user.login(request.getParameter("password"))) {
                    log.debug("로그인 성공");
                    response.addHeader("Set-Cookie", "logined=true");
                    response.sendRedirect("/index.html");
                } else {
                    log.debug("로그인 실패");
                    response.addHeader("Set-Cookie", "logined=false");
                    response.sendRedirect("/user/login_failed.html");
                }
            } else if (request.getPath().equals("/user/list")) {
                if (request.getHeader("Cookie").equals("logined=true")) {
                    response.sendRedirect("/index.html");
                } else {
                    response.sendRedirect("/user/login.html");
                }
            }

            response.forward(request.getPath());
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }
}
