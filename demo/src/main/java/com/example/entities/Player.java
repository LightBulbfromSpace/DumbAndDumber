package com.example.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Player {
    final private SimpleIntegerProperty id;
    final private SimpleIntegerProperty rating;
    final private SimpleStringProperty name;
    final private SimpleIntegerProperty score;

    public Player(String name) {
        this.id = new SimpleIntegerProperty(0);
        this.rating = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleIntegerProperty(0);
    }

    public Player(Integer id, Integer rating, String name, Integer score) {
        this.id = new SimpleIntegerProperty(id);
        this.rating = new SimpleIntegerProperty(rating);
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleIntegerProperty(score);
    }

    public Integer getId() {
        return id.get();
    }

    public Integer getRating() {
        return rating.get();
    }

    public void setRating(Integer newRating) {
        rating.set(newRating);
    }
    
    public String getName() {
        return name.get();
    }

    public void setName(String newName) {
        name.set(newName);
    }

    public Integer getScore() {
        return score.get();
    }

    public void setScore(Integer newScore) {
        score.set(newScore);
    }
}
