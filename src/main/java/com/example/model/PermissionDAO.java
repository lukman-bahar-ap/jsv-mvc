package com.example.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.util.DBUtil;

public class PermissionDAO {
    public List<Permission> getAllPermissions() throws Exception {
        List<Permission> list = new ArrayList<>();
        String sql = "SELECT * FROM permissions";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Permission p = new Permission();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                list.add(p);
            }
        }
        return list;
    }


    public void setPermissionForRole(int roleId, int permissionId, boolean enabled) throws Exception {
        String sqlCheck = "SELECT * FROM role_permissions WHERE role_id=? AND permission_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {

            stmtCheck.setInt(1, roleId);
            stmtCheck.setInt(2, permissionId);
            ResultSet rs = stmtCheck.executeQuery();

            if (enabled && !rs.next()) {
                String insert = "INSERT INTO role_permissions (role_id, permission_id) VALUES (?, ?)";
                try (PreparedStatement stmtInsert = conn.prepareStatement(insert)) {
                    stmtInsert.setInt(1, roleId);
                    stmtInsert.setInt(2, permissionId);
                    stmtInsert.executeUpdate();
                }
            } else if (!enabled && rs.next()) {
                String delete = "DELETE FROM role_permissions WHERE role_id=? AND permission_id=?";
                try (PreparedStatement stmtDelete = conn.prepareStatement(delete)) {
                    stmtDelete.setInt(1, roleId);
                    stmtDelete.setInt(2, permissionId);
                    stmtDelete.executeUpdate();
                }
            }
        }
    }

    public boolean getRoleHasPermission(int roleId, int permId) throws Exception {
        String sql = "SELECT 1 FROM role_permissions WHERE role_id=? AND permission_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, roleId);
            stmt.setInt(2, permId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

}
