package View;

import DAO.QuizDao;
import Main.SessionManager;
import Model.Question;
import Model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class QuizPage extends BasePage {
    private QuizDao quizDao;
    private List<Question> questions;
    private int quizId;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private Label questionLabel;
    private ToggleGroup answerGroup;
    private RadioButton[] answerButtons;
    private Label errorLabel;

    public QuizPage(QuizDao quizDao, int quizId, Stage stage) {
        this.quizDao = quizDao;
        this.questions = quizDao.getQuestionsForQuiz(quizId); // Load questions based on quizId
        this.quizId = quizId;

        // Check if the user is logged in
        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        initializeUI(stage); // Initialize UI components
        loadQuestion(); // Load the first question
    }

    private void initializeUI(Stage stage) {
        // Initialize question label
        questionLabel = new Label();
        questionLabel.setWrapText(true);
        questionLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Initialize ToggleGroup for answers
        answerGroup = new ToggleGroup();

        // Initialize RadioButtons for answer options
        answerButtons = new RadioButton[4]; // Assuming a maximum of 4 options
        VBox answersBox = new VBox(10);
        answersBox.setPadding(new Insets(10, 0, 10, 0));

        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new RadioButton();
            answerButtons[i].setToggleGroup(answerGroup);
            answerButtons[i].setWrapText(true);
            answerButtons[i].setStyle("-fx-font-size: 16px;");
            answersBox.getChildren().add(answerButtons[i]);
        }

        // Initialize Submit Button
        Button submitButton = new Button("Submit Answer");
        submitButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        submitButton.setOnAction(e -> handleSubmitAnswer());

        // Initialize error label
        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new IndexPage(stage).createScene());
        });

        // Add all components to the layout
        this.getChildren().addAll(questionLabel, answersBox, submitButton, errorLabel, logoutButton);
    }

    private void loadQuestion() {
        // Reset the answer selection for the new question
        answerGroup.selectToggle(null);
        errorLabel.setText(""); // Clear any previous error message

        // Check if there are more questions to load
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex); // Get the current question
            questionLabel.setText((currentQuestionIndex + 1) + ". " + question.getQuestionText()); // Display question text

            List<String> answerOptions = question.getAnswerOptions(); // Get answer options

            // Load the answer options into radio buttons
            for (int i = 0; i < answerButtons.length; i++) {
                if (i < answerOptions.size()) {
                    answerButtons[i].setText(answerOptions.get(i)); // Set text for radio button
                    answerButtons[i].setVisible(true); // Show radio button
                } else {
                    answerButtons[i].setVisible(false); // Hide if there are no more options
                }
            }
        } else {
            showFinalScore(); // Show final score if no more questions
        }
    }

    private void handleSubmitAnswer() {
        RadioButton selectedButton = (RadioButton) answerGroup.getSelectedToggle(); // Get selected button
        if (selectedButton != null) {
            String selectedAnswer = selectedButton.getText(); // Get text of selected answer
            Question currentQuestion = questions.get(currentQuestionIndex); // Get current question

            // Check if the selected answer is correct
            if (quizDao.checkAnswer(currentQuestion.getQuestionId(), selectedAnswer)) {
                score++; // Increment score for correct answer
            }

            currentQuestionIndex++; // Move to next question
            loadQuestion(); // Load the next question
        } else {
            // Display error message in the UI instead of an alert
            errorLabel.setText("Please select an answer before submitting.");
        }
    }

    private void showFinalScore() {
        // Clear existing content
        this.getChildren().clear();
        this.setPadding(new Insets(20));

        // Display final score
        Label scoreLabel = new Label("Quiz Finished!");
        scoreLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label resultLabel = new Label("Your score: " + score + " out of " + questions.size());
        resultLabel.setStyle("-fx-font-size: 18px;");

        // Save score to the database
        User currentUser = SessionManager.getInstance().getCurrentUser(); // Get the current user
        saveScore(currentUser.getUserId(), quizId, score); // Pass quizId here

        // Button to return to Quiz Library
        Button backButton = new Button("Back to Quiz Library");
        backButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        backButton.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new QuizLibrary(stage).createScene());
        });

        // Button to return to Homepage
        Button homeButton = new Button("Return to Homepage");
        homeButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        homeButton.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new Homepage(stage).createScene());
        });

        // Add components to the layout
        this.getChildren().addAll(scoreLabel, resultLabel, backButton, homeButton);
    }

    private void saveScore(int userId, int quizId, int score) {
        quizDao.recordQuizCompletion(userId, quizId, score); // Call the method to record the quiz completion
    }
}
