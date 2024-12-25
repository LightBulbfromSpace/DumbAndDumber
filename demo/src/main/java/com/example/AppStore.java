package com.example;

import java.util.ArrayList;
import com.example.entities.Player;
import com.example.entities.QuizQuestion;

public class AppStore {
    private Player currentPlayer;
    private ArrayList<QuizQuestion> questions;

    public AppStore() {
        questions = new ArrayList<>();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<QuizQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuizQuestion> questions) {
        this.questions = questions;
    }
}