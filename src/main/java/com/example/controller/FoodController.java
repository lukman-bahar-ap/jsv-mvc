package com.example.controller;

import java.io.IOException;
import java.util.List;

import com.example.model.Food;
import com.example.model.FoodDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/food")
public class FoodController extends HttpServlet {
    private FoodDAO dao = new FoodDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if("edit".equals(action)){
                int id = Integer.parseInt(req.getParameter("id"));
                Food food = dao.getById(id);
                req.setAttribute("food", food);
                req.getRequestDispatcher("/pages/food-form.jsp").forward(req, resp);
            }else if("delete".equals(action)){
                int id = Integer.parseInt(req.getParameter("id"));
                dao.delete(id);
                resp.sendRedirect("food");
            }else if("new".equals(action)){
                req.getRequestDispatcher("/pages/food-form.jsp").forward(req, resp);
            }else{
                List<Food> list = dao.getAll();
                req.setAttribute("list", list);
                req.getRequestDispatcher("/pages/food-list.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException("Error retrieving food list", e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        try{
            String idStr=req.getParameter("id");
            String name=req.getParameter("name");
            int price=Integer.parseInt(req.getParameter("price"));

            Food food = new Food();
            food.setName(name);
            food.setPrice(price);
            if(idStr ==null || idStr.isEmpty()){
                dao.insert(food);
            }else{
                food.setId(Integer.parseInt(idStr));
                dao.update(food);
            }
            resp.sendRedirect("food");
        }catch (Exception e) {
            throw new ServletException("Error processing food form", e);
        }
    }
}