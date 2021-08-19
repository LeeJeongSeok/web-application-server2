package controller;

import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListUserController extends AbstractController{

    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        if (request.getHeader("Cookie").equals("logined=true")) {
            response.sendRedirect("/index.html");
        } else {
            response.sendRedirect("/user/login.html");
        }
    }
}
