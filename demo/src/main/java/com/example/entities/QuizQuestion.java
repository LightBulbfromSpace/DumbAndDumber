package com.example.entities;

import java.util.ArrayList;

public class QuizQuestion {
    private String question;
    private ArrayList<String> answers;
    private Integer rightQuestionId;

    public QuizQuestion(String question, ArrayList<String> answers, Integer rightQuestionId) {
        this.question = question;
        this.answers = answers;
        this.rightQuestionId = rightQuestionId;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return this.answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public Integer getRightQuestionId() {
        return this.rightQuestionId;
    }

    public void setRightQuestionId(Integer rightQuestionId) {
        this.rightQuestionId = rightQuestionId;
    }    
}
