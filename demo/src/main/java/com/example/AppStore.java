package com.example;

import com.example.entities.Player;

public class AppStore {
    private Player currentPlayer;
    private Integer sessionScore = 0;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Integer getSessionScore() {
        return this.sessionScore;
    }

    public void setSessionScore(Integer sessionScore) {
        this.sessionScore = sessionScore;
    }
}