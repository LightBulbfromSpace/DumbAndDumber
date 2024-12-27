package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

import com.example.db.quizQuestion.QuizQuestionLoader;
import com.example.db.quizQuestion.QuizQuestionRepository;
import com.example.entities.Answer;
import com.example.entities.QuizQuestion;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class EditQuestionsController implements Initializable {
    final private ArrayList<QuizQuestion> questions = new QuizQuestionLoader().loadAll();
    final private QuizQuestionRepository repository = new QuizQuestionRepository();

    private ArrayList<CheckBox> currentCheckBoxes = new ArrayList<>();
    private Integer currentQuestionIndex = 0;
    private Node editingNode = null;
    private HashMap<Integer, QuizQuestion> editedQuestions = new HashMap<>();

    @FXML private VBox root;
    @FXML private Label nothingSelectedLabel;
    @FXML private TextField editTextField;
    @FXML private Button editButton;
    @FXML private Label questionLabel;
    @FXML private VBox answersContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMode(false, null);

        setQuestion(currentQuestionIndex);
    }

    private void setQuestion(Integer index) {
        QuizQuestion q = questions.get(index);
        setupQuestionText(q);
        setupAnswers(q.getAnswers());
    }

    private void setupAnswers(ArrayList<Answer> answers) {
        answersContainer.getChildren().clear();
        currentCheckBoxes.clear();

        CheckBox[] previouslySelected = {null};
    
        for (Answer answer : answers) {
            CheckBox checkBox = new CheckBox(answer.getText());
            checkBox.setId(answer.getId().toString());

            checkBox.setOnMouseClicked(event -> {
                if (!checkBox.isSelected()) {
                    checkBox.setSelected(true);
                }

                if (event.getClickCount() == 2) {
                    for (CheckBox cb : currentCheckBoxes) {
                        cb.setSelected(false);
                    }

                    previouslySelected[0].setSelected(true);

                    try {
                        onEditableComponentClicked(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (event.getClickCount() == 1) {
                    javafx.application.Platform.runLater(() -> {
                        if (event.getClickCount() == 1) {
                            for (CheckBox cb : this.currentCheckBoxes) {
                                if (cb != checkBox) {
                                    cb.setSelected(false);
                                }
                            }

                            getCurrentQuestion().setRightAnswerId(Integer.valueOf(checkBox.getId()));

                            editedQuestions.put(getCurrentQuestion().getId(), getCurrentQuestion());

                            previouslySelected[0] = checkBox;
                        }
                    });
                }
            });

            if (Objects.equals(getCurrentQuestion().getRightAnswerId(), answer.getId())) {
                checkBox.setSelected(true);

                previouslySelected[0] = checkBox;
            }
    
            currentCheckBoxes.add(checkBox);
    
            checkBox.getStyleClass().add("answer");
            answersContainer.getChildren().add(checkBox);
        }
    }

    private void setupQuestionText(QuizQuestion question) {
        questionLabel.setText(question.getQuestion());
    }

    @FXML
    private void onToStartScreenClicked() throws IOException {
        ArrayList<QuizQuestion> finalEditedQuestions = new ArrayList<>();

        editedQuestions.forEach((id, question) -> {
            finalEditedQuestions.add(question);
        });

        for (QuizQuestion q : finalEditedQuestions) {
            repository.update(q);
        }

        // repository.updateMultiple(finalEditedQuestions);

        App.setRoot("startScreen");
    }

    @FXML
    private void onPreviousClicked() throws IOException {
        currentQuestionIndex--;

        if (currentQuestionIndex < 0) {
            currentQuestionIndex = questions.size() - 1;
        }

        setQuestion(currentQuestionIndex);

        setMode(false, null);
    }

    @FXML
    private void onNextClicked() throws IOException {
        currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();

        setQuestion(currentQuestionIndex);
        setMode(false, null);
    }

    @FXML
    private void onAddQuestionClicked() throws IOException {

    }

    @FXML
    private void onDeleteQuestionClicked() throws IOException {
        
    }

    @FXML
    private void onEditableComponentClicked(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        String componentId = source.getId();

        editingNode = root.lookup("#" + componentId);

        if (editingNode instanceof Label) {
            Label label = (Label) editingNode;
            
            setMode(true, label.getText());
        } else if (editingNode instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) editingNode;

            setMode(true, checkBox.getText());
        } else {
            System.err.println("Unknown component type: " + (editingNode != null ? editingNode.getClass().getSimpleName() : "null"));
        }
    }

    @FXML
    private void onCompleteEditClicked() throws IOException {
        if (editingNode instanceof Label) {
            Label label = (Label) editingNode;

            getCurrentQuestion().setQuestion(editTextField.getText());

            editedQuestions.put(getCurrentQuestion().getId(), getCurrentQuestion());
            
            label.setText(editTextField.getText());
        } else if (editingNode instanceof CheckBox) {
            CheckBox checkBox = (CheckBox) editingNode;

            for (Answer answer : getCurrentQuestion().getAnswers()) {
                if (answer.getId().toString().equals(checkBox.getId())) {
                    answer.setText(editTextField.getText());
                }
            }

            editedQuestions.put(getCurrentQuestion().getId(), getCurrentQuestion());

            checkBox.setText(editTextField.getText());
        } else {
            System.err.println("Unknown component type: " + (editingNode != null ? editingNode.getClass().getSimpleName() : "null"));
        }

        setMode(false, null);

        editingNode = null;
    }

    private void setMode(Boolean isEditMode, String labelText) {
        nothingSelectedLabel.setVisible(!isEditMode);
        nothingSelectedLabel.setMaxWidth(isEditMode ? 0 : Region.USE_COMPUTED_SIZE);
        
        editTextField.setVisible(isEditMode);
        editTextField.setMaxWidth(isEditMode ? Region.USE_COMPUTED_SIZE : 0);
        editTextField.setText(labelText);

        editButton.setVisible(isEditMode);
    }

    private QuizQuestion getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }
}
