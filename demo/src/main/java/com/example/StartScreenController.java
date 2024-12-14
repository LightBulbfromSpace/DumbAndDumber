package com.example;

import java.io.IOException;

import javafx.fxml.FXML;

public class StartScreenController {

    @FXML
    private void startGame() throws IOException {
        App.setRoot("gameProcess");
    }

    @FXML
    private void openPlayers() throws IOException {
        App.setRoot("players");
    }

    @FXML
    private void loadQuestions() throws IOException {
        App.setRoot("loadQuestions");
    }
}
