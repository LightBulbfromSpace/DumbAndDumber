package com.example.db.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.db.EntityLoader;
import com.example.entities.Player;

public class PlayerLoader extends EntityLoader<Player> {

    @Override
    public Player loadById(int id) {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT x.rating, x.nickname, x.score FROM (SELECT p.id, p.nickname, p.score, @rownum := @rownum + 1 AS rating FROM Players as p JOIN (SELECT @rownum := 0) r ORDER BY p.score DESC) x WHERE x.id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Integer rating = rs.getInt("rating");
                String nickname = rs.getString("nickname");
                Integer score = rs.getInt("score");

                st.close();
                rs.close();

                return new Player(id, rating, nickname, score);
            }

            st.close();
            rs.close();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Player> loadAll() {
        try {
            PreparedStatement st = conn.prepareStatement("SELECT ROW_NUMBER() OVER (ORDER BY score DESC) AS rating, id, nickname, score FROM Players ORDER BY score DESC");
            ResultSet rs = st.executeQuery();

            ArrayList<Player> players = new ArrayList<>();

            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer rating = rs.getInt("rating");
                Integer score = rs.getInt("score");
                String nickname = rs.getString("nickname");

                players.add(new Player(id, rating, nickname, score));
            }

            st.close();
            rs.close();
        
            return players;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
