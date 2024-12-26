package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.db.quizQuestion.QuizQuestionLoader;
import com.example.entities.Answer;
import com.example.entities.QuizQuestion;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameProcessController implements Initializable {
    final private QuizQuestionLoader entityLoader = new QuizQuestionLoader();
    final private ArrayList<QuizQuestion> questions = entityLoader.loadAll();

    private Integer currentQuestionIndex = -1;
    private ArrayList<CheckBox> currentCheckBoxes = new ArrayList<>();

    @FXML private Label questionLabel;
    @FXML private VBox answersContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.getStore().setSessionScore(0);

        if (questions.isEmpty()) {
            try {
                App.setRoot("final");
                
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        setNextQuestion();
    }

    private void setupAnswers(ArrayList<Answer> answers) {
        answersContainer.getChildren().clear();

        currentCheckBoxes.clear();

        for (Answer answer : answers) {
            CheckBox checkBox = new CheckBox(answer.getText());
            checkBox.setId(answer.getId().toString());

            checkBox.setOnAction(event -> {
                for (CheckBox cb : this.currentCheckBoxes) {
                    if (cb != checkBox) {
                        cb.setSelected(false);
                    }
                }
            });

            currentCheckBoxes.add(checkBox);

            checkBox.getStyleClass().add("answer");
            answersContainer.getChildren().add(checkBox);
        }
    }

    private void setupQuestionText(QuizQuestion question) {
        questionLabel.setText(question.getQuestion());
    }

    @FXML
    private void onAnswerClick() throws IOException {

        for (CheckBox checkbox : currentCheckBoxes) {
            if (!checkbox.isSelected()) {
                continue;
            }

            String checkboxAnswerId = checkbox.getId();
            Integer rightAnswerId = getCurrentQuestion().getRightAnswerId();

            if (checkboxAnswerId == null || rightAnswerId == null) {
                throw new IllegalArgumentException("Answer id is not initialized");
            }

            if (checkboxAnswerId.equals(rightAnswerId.toString())) {
                AppStore store = App.getStore();

                store.setSessionScore(
                    store.getSessionScore() + 1
                );
            }
        }

        if (currentQuestionIndex >= questions.size() - 1) {
            App.setRoot("final");

            return;
        }

        setNextQuestion();
    }

    @FXML
    private void onToStartScreenClicked() throws IOException {
        App.setRoot("startScreen");
    }

    private void setNextQuestion() {
        currentQuestionIndex++;

        QuizQuestion question = getCurrentQuestion();

        setupQuestionText(question);
        setupAnswers(question.getAnswers());
    }

    private QuizQuestion getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }
}