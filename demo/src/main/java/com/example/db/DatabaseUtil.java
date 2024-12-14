package com.example.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/dumbAndDumberGame?useSSL=false&serverTimezone=UTC";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

