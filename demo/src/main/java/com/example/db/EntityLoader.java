package com.example.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.entities.Player;

public class EntityLoader {
    public static Player loadPlayer(int id) {
        ResultSet rs = EntityLoader.fetch("SELECT * FROM Players WHERE id =" + id);

        try {
            String nickname = rs.getString("nickname");
            Integer score = rs.getInt("score");

            if (rs.next()) {
                return new Player(nickname, score);
            }
            return new Player(nickname, score);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Player> loadAllPlayers() {
        ResultSet rs = EntityLoader.fetch("SELECT * FROM Players");

        ArrayList<Player> players = new ArrayList<>();

        try {
            for (;rs.next();) {
                String nickname = rs.getString("nickname");
                Integer score = rs.getInt("score");
    
                players.add(new Player(nickname, score));
            }
    
            return players;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    private static ResultSet fetch(String query) {
        try (Connection conn = DatabaseUtil.connect();
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

