package com.example.entities;

public class Player {
    private String name;
    private Integer score;

    public Player(String name, Integer score) {
        this.name = name;
        this.score = score;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScores() {
        return this.score;
    }

    public void setScores(Integer score) {
        this.score = score;
    }
}
