package com.example.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseUtil {
    private static final String DB_FILE = "dumbAndDumberGame.db";
    private static final String URL = "jdbc:sqlite:" + DB_FILE;

    public static Connection connect() {
        Connection conn = null;
        try {
            File dbFile = new File(DB_FILE);
            boolean databaseExists = dbFile.exists();

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);

            if (!databaseExists) {
                System.out.println("Database does not exist. Creating database...");
                System.out.println("Current working directory: " + System.getProperty("user.dir"));

                String pathToDir = "demo/src/main/java/com/example/db/sql/";

                executeSQLFile(pathToDir + "install.sql");
                executeSQLFile(pathToDir + "install_data.sql");
                System.out.println("Database and initial data have been created.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static void executeSQLFile(String filePath) {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line);
                if (line.trim().endsWith(";")) {
                    stmt.execute(sql.toString());
                    sql.setLength(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}