package com.example;

import java.io.IOException;

import javafx.fxml.FXML;

public class StartScreenController {

    @FXML
    private void startGame() throws IOException {
        if (App.getStore().getCurrentPlayer() == null) {
            ErrorDialog.showErrorDialog("Error", "Player is not selected", "Setup player in \"Players\"");

            return;
        }

        App.setRoot("gameProcess");
    }

    @FXML
    private void openPlayers() throws IOException {
        App.setRoot("players");
    }

    @FXML
    private void loadQuestions() throws IOException {
        App.setRoot("editQuestions");
    }
}
