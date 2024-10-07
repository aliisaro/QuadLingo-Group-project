package View;

import DAO.QuizDao;
import Main.SessionManager;
import Model.Question;
import Model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.paint.Color; // For color changes
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
    private Label feedbackLabel;

    public QuizPage(QuizDao quizDao, int quizId, Stage stage) {
        this.quizDao = quizDao;
        this.questions = quizDao.getQuestionsForQuiz(quizId); // Load questions based on quizId
        this.quizId = quizId;

        // Check if the user is logged in
        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        setLayout(stage); // Initialize UI components
        loadQuestion(); // Load the first question
    }

    private void setLayout(Stage stage) {
        // Apply padding directly to 'this' (inheriting VBox)
        this.setPadding(new Insets(20)); // 20px padding around the VBox

        // Initialize question label
        questionLabel = new Label();
        questionLabel.setWrapText(true);
        questionLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

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

        // Label for immediate feedback once answer is submitted
        feedbackLabel = new Label();
        feedbackLabel.setVisible(false); // Initially hidden

        // Initialize Submit Button
        Button submitButton = new Button("Submit Answer");
        submitButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 175;");
        submitButton.setOnAction(e -> handleSubmitAnswer());

        // Initialize Cancel Button (to cancel the current quiz and go back)
        Button cancelButton = new Button("Cancel Quiz");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 150;");
        cancelButton.setOnAction(e -> {
            // Navigate back to Quiz Library without saving or finishing the quiz
            stage.setScene(new QuizLibrary(stage).createScene());
        });

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 125;");
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new IndexPage(stage).createScene());
        });

        // Group buttons in an HBox for better alignment
        HBox buttonBox = new HBox(10); // Set spacing between buttons (10px)
        buttonBox.setPadding(new Insets(10, 0, 10, 0));
        buttonBox.setStyle("-fx-alignment: center;"); // Center the buttons horizontally
        buttonBox.getChildren().addAll(submitButton, cancelButton, logoutButton);

        // Initialize error label
        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Add all components to the layout
        this.getChildren().addAll(questionLabel, answersBox, feedbackLabel, errorLabel, buttonBox);
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
                feedbackLabel.setText("Correct!"); // Set feedback for correct answer
                feedbackLabel.setTextFill(Color.GREEN); // Set text color to green
            } else {
                feedbackLabel.setText("Incorrect! The correct answer is: " + currentQuestion.getCorrectAnswer()); // Display correct answer
                feedbackLabel.setTextFill(Color.RED); // Set text color to red
            }

            feedbackLabel.setVisible(true); // Show feedback label

            // Add a brief pause before loading the next question
            PauseTransition pause = new PauseTransition(Duration.seconds(1)); // Adjust the duration as needed
            pause.setOnFinished(event -> {
                currentQuestionIndex++; // Move to next question
                loadQuestion(); // Load the next question
            });
            pause.play(); // Start the pause transition
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

        // Get current user and quiz ID
        User currentUser = SessionManager.getInstance().getCurrentUser(); // Get the current user
        int userId = currentUser.getUserId(); // Get User ID

        // Check if the user has already completed the quiz
        boolean hasCompletedQuiz = quizDao.hasUserCompletedQuiz(userId, quizId);

        // Always update the score for the quiz
        quizDao.recordQuizCompletion(userId, quizId, score); // Save score to the database

        // Increment completed quizzes count if the user has not taken the quiz before
        if (!hasCompletedQuiz) {
            quizDao.incrementCompletedQuizzes(userId); // Increment quiz count only if the user has not taken the quiz
        }

        // Add score labels to the layout
        this.getChildren().addAll(scoreLabel, resultLabel);

        // Button to return to Quiz Library
        Button backButton = new Button("Back to Quiz Library");
        backButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;-fx-pref-width: 180;");
        backButton.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new QuizLibrary(stage).createScene());
        });

        // Add the back button to the layout
        this.getChildren().add(backButton);
    }

    private void saveScore(int userId, int quizId, int score) {
        quizDao.recordQuizCompletion(userId, quizId, score); // Call the method to record the quiz completion
    }
}