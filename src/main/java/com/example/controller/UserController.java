package com.example.controller;

import java.io.IOException;
import java.util.List;

import com.example.model.Role;
import com.example.model.RoleDAO;
import com.example.model.User;
import com.example.model.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/user")
public class UserController extends HttpServlet {
    private UserDAO dao = new UserDAO();
    private RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if("edit".equals(action)){
                int id = Integer.parseInt(req.getParameter("id"));
                List<Role> roleList = roleDAO.getAllRoles();
                User formUser = dao.getById(id);
                req.setAttribute("formUser", formUser);
                req.setAttribute("roleList", roleList);
                req.getRequestDispatcher("/pages/user-form.jsp").forward(req, resp);
            }else if("delete".equals(action)){
                int id = Integer.parseInt(req.getParameter("id"));
                dao.delete(id);
                resp.sendRedirect("user");
            }else if("new".equals(action)){
                List<Role> roleList = roleDAO.getAllRoles();
                req.setAttribute("roleList", roleList);
                req.getRequestDispatcher("/pages/user-form.jsp").forward(req, resp);
            }else{
                List<User> list = dao.getAllUsers();
                req.setAttribute("list", list);
                req.getRequestDispatcher("/pages/user-list.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException("Error retrieving user list", e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        try{
            String idStr=req.getParameter("id");
            String username=req.getParameter("username");
            String password=req.getParameter("password");
            int roleId=Integer.parseInt(req.getParameter("role_id"));

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            
            Role role = new Role();
            role.setId(roleId);
            user.setRole(role);
            if(idStr ==null || idStr.isEmpty()){
                dao.insert(user);
            }else{
                user.setId(Integer.parseInt(idStr));
                dao.update(user);
            }
            resp.sendRedirect("user");
        }catch (Exception e) {
            throw new ServletException("Error processing user form", e);
        }
    }
}