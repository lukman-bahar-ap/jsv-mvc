package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.model.Permission;
import com.example.model.PermissionDAO;
import com.example.model.Role;
import com.example.model.RoleDAO;
import com.example.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    private RoleDAO roleDAO = new RoleDAO();
    private PermissionDAO permDAO = new PermissionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null || !user.getRole().getName().equals("admin")) {
            resp.sendRedirect("login");
            return;
        }

        try {
            List<Role> roles = roleDAO.getAllRoles();
            List<Permission> perms = permDAO.getAllPermissions();

            Map<String, Boolean> perMap = new HashMap<>();
            for(Role r: roles){
                for(Permission p : perms){
                    perMap.put(r.getId()+"-"+p.getId(), permDAO.getRoleHasPermission(r.getId(), p.getId()));
                }
            }
            System.out.print(roles.size());
            req.setAttribute("roles", roles);
            req.setAttribute("permissions", perms);
            req.setAttribute("roleperm", perMap);
            req.getRequestDispatcher("/pages/admin.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        if (user == null || !user.getRole().getName().equals("admin")) {
            resp.sendRedirect("admin");
            return;
        }
        try {
            int roleId = Integer.parseInt(req.getParameter("roleId"));
            int permId = Integer.parseInt(req.getParameter("permId"));
            boolean enable = "on".equals(req.getParameter("enable"));
            permDAO.setPermissionForRole(roleId, permId, enable);
            resp.sendRedirect("admin");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
