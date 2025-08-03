package com.example.controller;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        String username = (String) session.getAttribute("username");
        request.setAttribute("message", "Hello " + username + ", welcome to JSP MVC with JDK 17 and Tomcat 10.1!");
        request.getRequestDispatcher("/pages/dashboard.jsp").forward(request, response);
    }
}