package com.example.entities;

public class Answer {
    private Integer id;
    private Integer questionId;
    private String text;

    public Answer(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public Answer(Integer id, Integer questionId, String text) {
        this.id = id;
        this.text = text;
        this.questionId = questionId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
}
