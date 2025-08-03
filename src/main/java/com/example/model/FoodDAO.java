package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.util.DBUtil;

public class FoodDAO {
    public List<Food>getAll() throws Exception {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM food";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price")
                ));
            }
            return list;
        }
    }
    
    public Food getById(int id) throws Exception {
        String sql = "SELECT * FROM food WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price")
                );
            }
        }
        return null;
    }

    public void insert(Food food) throws Exception {
        String sql = "INSERT INTO food (name, price) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, food.getName());
            pstmt.setInt(2, food.getPrice());
            pstmt.executeUpdate();    
        }
    }

    public void update(Food food) throws Exception {
        String sql = "UPDATE food SET name=?, price=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, food.getName());
            pstmt.setInt(2, food.getPrice());
            pstmt.setInt(3, food.getId());
            pstmt.executeUpdate();    
        }
    }
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM food WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
}
