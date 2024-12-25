package com.example.db.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.db.Repository;
import com.example.entities.Player;

public class PlayerRepository extends Repository<Player> {

    @Override
    public Integer add(Player entity) {
        try {
            PreparedStatement st = conn.prepareStatement(
                "INSERT INTO Players(nickname) VALUES(?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            st.setString(1, entity.getName());

            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                // generated id
                return rs.getInt(1);
            }
    
            rs.close();
            st.close();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement st = conn.prepareStatement("DELETE FROM Players WHERE id = ?");
            st.setInt(1, id);

            st.executeUpdate();

            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
