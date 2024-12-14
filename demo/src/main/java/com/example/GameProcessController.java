package com.example;

import java.io.IOException;

import javafx.fxml.FXML;

public class GameProcessController {

    @FXML
    private void onAnswerClick() throws IOException {
        App.setRoot("tertiary");
    }
}