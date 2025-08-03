package com.example.controller;

import java.io.IOException;

import com.example.model.User;
import com.example.model.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {

   // private static final long serialVersionUID = 1L;
    private final UserDAO dao=new UserDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET request for login
        request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST request for login
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = dao.checkLogins(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            user.setUsername(username);
            // user = userDAO.getUserByUsername(username);
            session.setAttribute("user", user); // Simpan objek User

            session.setAttribute("username", user.getUsername());
            response.sendRedirect("home");
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }
    
}
