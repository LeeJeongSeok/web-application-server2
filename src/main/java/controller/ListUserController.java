package controller;

import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListUserController extends AbstractController{

    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        if (!isLogined(request.getSession())) {
            response.sendRedirect("/index.html");
        } else {
            response.sendRedirect("/user/login.html");
        }
    }

    private static boolean isLogined(HttpSession session) {
        Object user = session.getAttribute("user");
        if (user == null) {
            return false;
        }
        return true;
    }
}
