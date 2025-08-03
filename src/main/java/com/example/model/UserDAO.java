package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.util.DBUtil;

public class UserDAO {
    public User checkLogin(String username, String password){
        String query= "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn=DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                User user=new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (Exception e) {
            
        }
        return null;
    }

    public User checkLogins(String username, String password) {
        String query= "SELECT u.*, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.username = ? AND u.password = ?";
        try (Connection conn=DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                User user=new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));

                Role role = new Role();
                role.setId(rs.getInt("role_id"));
                role.setName(rs.getString("role_name"));
                user.setRole(role);

                //Get permissions
                Set<String> permissions = new HashSet<>();
                String psql = "SELECT p.name FROM role_permissions rp JOIN permissions p ON rp.permission_id = p.id WHERE rp.role_id = ?";

                try (PreparedStatement ps = conn.prepareStatement(psql)) {
                    ps.setInt(1, rs.getInt("role_id"));
                    ResultSet prs = ps.executeQuery();
                    while (prs.next()){
                        permissions.add(prs.getString("name"));
                    }
                }
                user.setPermissions(permissions);

                return user;
            }
        } catch (Exception e) {
            
        }
        return null;
    }

    // public ResultSet getAllUsers() throws Exception {
    //     Connection conn = DBUtil.getConnection();
    //     String sql = "SELECT u.*, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id";
    //     PreparedStatement stmt = conn.prepareStatement(sql);
    //     return stmt.executeQuery();
    // }

    public List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.*, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id";
        
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password")
                );

                Role role = new Role();
                role.setId(rs.getInt("role_id"));
                role.setName(rs.getString("role_name"));
                user.setRole(role);

                users.add(user);
            }
        }
        return users;
    }


    public User getById(int id) throws Exception {
        String sql = "SELECT u.*, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password")
                );

                Role role = new Role();
                role.setId(rs.getInt("role_id"));
                role.setName(rs.getString("role_name"));

                user.setRole(role);
                return user;
            }
        }
        return null;
    }

    public void insert(User user) throws Exception {
        String sql = "INSERT INTO users (username, password, role_id) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getRole().getId());
            pstmt.executeUpdate();
        }
    }

    public void update(User user) throws Exception {
        String sql = "UPDATE users SET username = ?, password = ?, role_id = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getRole().getId());
            pstmt.setInt(4, user.getId());
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

}
