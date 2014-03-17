package fr.xebia.gae.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class OAuth2callback extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {


        System.out.println(" ------ ");
        System.out.println(" OAuth2callback ");
        System.out.println(" ------ ");

    }
}
