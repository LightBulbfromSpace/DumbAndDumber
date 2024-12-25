package com.example.entities;

import java.util.ArrayList;

public class QuizQuestion {
    private Integer id;
    private String question;
    private ArrayList<Answer> answers;
    private Integer rightAnswerId;

    public QuizQuestion(Integer id, String question, ArrayList<Answer> answers, Integer rightAnswerId) {
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.rightAnswerId = rightAnswerId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Answer> getAnswers() {
        return this.answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    public Integer getRightAnswerId() {
        return this.rightAnswerId;
    }

    public void setRightAnswerId(Integer rightAnswerId) {
        this.rightAnswerId = rightAnswerId;
    }
}
