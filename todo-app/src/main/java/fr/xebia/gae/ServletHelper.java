package fr.xebia.gae;

import javax.servlet.ServletRequest;

public class ServletHelper {

    public static String getRoot(ServletRequest request) {
        if (request.getServerName().contains("localhost")) {
            return "http://" + request.getServerName() +  ":9090/_ah/api";
        } else {
            return "https://todo-api-dot-" + request.getServerName() + "/_ah/api";
        }
    }

}
