package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.db.player.PlayerRepository;
import com.example.entities.Player;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FinalController implements Initializable {

    @FXML private Label scoreLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Integer score = App.getStore().getSessionScore();
        Player currentPlayer = App.getStore().getCurrentPlayer();

        scoreLabel.setText(score.toString());

        if (score > currentPlayer.getScore()) {
            currentPlayer.setScore(score);
        }

        new PlayerRepository().update(currentPlayer);
    }

    @FXML
    private void onToStartClick() throws IOException {
        App.setRoot("startScreen");
    }
    
}
