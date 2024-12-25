package com.example.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/dumbAndDumberGame";
    private static final String USER = "dumbUser";
    private static final String PASSWORD = "pass";

    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

