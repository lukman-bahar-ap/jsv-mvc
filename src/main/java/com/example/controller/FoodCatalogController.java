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


@WebServlet("/foodlist")
public class FoodCatalogController extends HttpServlet {
    private FoodDAO dao = new FoodDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
                List<Food> list = dao.getAll();
                req.setAttribute("list", list);
                req.getRequestDispatcher("/pages/food-list-catalog.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error retrieving food list", e);
        }
    }
}