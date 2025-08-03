package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.util.DBUtil;

public class RoleDAO {
    public List<Role>getAllRoles() throws Exception {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setName(rs.getString("name"));
                list.add(role);
            }
        }
        return list;
    }
    
    public Role getById(int id) throws Exception {
        String sql = "SELECT * FROM roles WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Role(rs.getInt("id"), rs.getString("name"));
                }
            }
        }
        return null;
    }

    
}
