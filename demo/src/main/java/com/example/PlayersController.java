package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.example.db.player.PlayerLoader;
import com.example.db.player.PlayerRepository;
import com.example.entities.Player;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PlayersController implements Initializable {
    final private PlayerLoader entityLoader = new PlayerLoader();
    final private PlayerRepository repository = new PlayerRepository();
    final private ObservableList<Player> tableData = FXCollections.observableArrayList(new ArrayList<>());

    final private static String EMPTY_USER_PHRASE = "Not Selected";

    @FXML private TableView<Player> playersTable;
    @FXML private Label playerNameLabel;
    @FXML private Button addUserButton;
    @FXML private Button deleteUserButton;
    @FXML private TextField playerNameTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createTable();
        setOnTableRowSelectedEvent();

        Player player = App.getStore().getCurrentPlayer();

        String name = player != null ? player.getName() : EMPTY_USER_PHRASE;

        switchToReadUserMode(name);
        updateTable();
        hide(deleteUserButton);
    }

    private void createTable() {

        TableColumn<Player, Integer> ratingCol = new TableColumn<>("Rating");
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        TableColumn<Player, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Player, String> scoreCol = new TableColumn<>("Best Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));


        playersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        playersTable.getColumns().addAll(ratingCol, nameCol, scoreCol);
    }

    private void updateTable() {
        ArrayList<Player> data = entityLoader.loadAll();

        tableData.clear();
        tableData.addAll(data);

        Platform.runLater(() -> playersTable.setItems(tableData));
    }

    private void addToTable(Player player) {
        tableData.add(player);

        Platform.runLater(() -> playersTable.setItems(tableData));
    }

    private void removeFromTable(Player player) {
        tableData.remove(player);

        Platform.runLater(() -> playersTable.setItems(tableData));
    }

    private void setOnTableRowSelectedEvent() {
        playersTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Player selectedPlayer = playersTable.getSelectionModel().getSelectedItem();
                if (selectedPlayer != null) {
                    App.getStore().setCurrentPlayer(selectedPlayer);
                    switchToReadUserMode(selectedPlayer.getName());
                    show(deleteUserButton, 200);
                }
            }
        });
    }

    @FXML
    private void onBackClicked() throws IOException {
        App.setRoot("startScreen");
    }

    @FXML
    private void onDeleteUserClicked() throws IOException {
        Player player = App.getStore().getCurrentPlayer();

        repository.delete(player.getId());
        App.getStore().setCurrentPlayer(null);

        removeFromTable(player);

        switchToReadUserMode(EMPTY_USER_PHRASE);
    }

    @FXML
    private void onAddUserClicked() throws IOException {
        if (playerNameLabel.isVisible()) {
            switchToEditUserMode("");
        } else {
            String value = playerNameTextField.getText();
            if (value.isEmpty()) {
                ErrorDialog.showErrorDialog("Error", "Name can't be empty", "");

                return;
            }

            switchToReadUserMode(value);
            Integer generatedId = repository.add(new Player(value));
            Player currentPlayer = null;

            if (generatedId != null) {
                currentPlayer = entityLoader.loadById(generatedId);

                addToTable(currentPlayer);
            }

            App.getStore().setCurrentPlayer(currentPlayer);
        }
    }

    private void switchToEditUserMode(String textFieldText) {
        playerNameTextField.setText(textFieldText);

        hide(playerNameLabel);
        show(playerNameTextField, 200);

        addUserButton.setText("OK");

        playerNameTextField.requestFocus();

        hide(deleteUserButton);
    }

    private void switchToReadUserMode(String labelText) {
        playerNameLabel.setText(labelText);

        hide(playerNameTextField);
        show(playerNameLabel, 200);

        addUserButton.setText("Add User");

        show(deleteUserButton, 200);
    }

    private <T extends javafx.scene.layout.Region> void hide(T item) {
        item.setVisible(false);
        item.setMaxWidth(0);
    }

    private <T extends javafx.scene.layout.Region> void show(T item, Integer width) {
        item.setVisible(true);
        item.setMaxWidth(width);
    }
}
