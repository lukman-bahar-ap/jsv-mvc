package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/jsvmvc";
    private static final String USER = "root";
    private static final String PASSWORD = "bismillah";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
