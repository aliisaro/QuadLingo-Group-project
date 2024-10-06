package View;

import Controller.UserController;
import Controller.QuizController; // Ensure you have a QuizController
import DAO.ProgressDaoImpl;
import DAO.UserDaoImpl;
import DAO.QuizDaoImpl;
import Database.MariaDbConnection;
import Main.SessionManager;
import Model.User;
import Model.Quiz;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;

import java.sql.Connection;
import java.util.List;

public class QuizLibrary extends BasePage implements UpdateProgress {
    private final UserController userController = new UserController(new UserDaoImpl()); // UserController object
    private QuizController quizController; // QuizController object
    private final ProgressBar progressBar1 = ProgressPage.getProgressBar1();
    private final ProgressBar progressBar2 = ProgressPage.getProgressBar2();
    private final ProgressDaoImpl progressDao = new ProgressDaoImpl();
    private final int userID = userController.getCurrentUserId();

    public QuizLibrary(Stage stage) {
        // Initialize UserDaoImpl and UserController objects

        // Get the current logged-in user from the session
        User currentUser = SessionManager.getInstance().getCurrentUser();

        // If user is not logged in, redirect to index page
        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        // Initialize QuizController with database connection
        Connection connection = MariaDbConnection.getConnection();
        quizController = new QuizController(new QuizDaoImpl(connection));

        // Set layout to the stage
        setLayout(stage, currentUser, connection);
    }

    private void setLayout(Stage stage, User currentUser, Connection connection) {
        // Page title
        Label pageTitle = new Label("Quiz Library");
        pageTitle.setStyle("-fx-font-size: 24px; -fx-padding: 10px;");

        // Set VBox alignment to center the title
        VBox.setMargin(pageTitle, new Insets(0, 0, 0, 100));

        // Return to the homepage
        Button backButton = new Button("Back to Homepage");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165;");
        backButton.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        // Logout button: clears session and redirects to IndexPage
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165;");
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new IndexPage(stage).createScene());
        });

        // Group buttons in an HBox for better alignment
        HBox buttonBox = new HBox(10); // Set spacing between buttons (10px)
        buttonBox.setPadding(new Insets(10, 0, 10, 0));
        buttonBox.setStyle("-fx-alignment: center;"); // Center the buttons horizontally
        buttonBox.getChildren().addAll(backButton, logoutButton);

        // Fetch all quizzes from the database
        List<Quiz> quizzes = quizController.getAllQuizzes();

        // VBox to hold quiz buttons
        VBox quizzesBox = new VBox(10);
        quizzesBox.setPadding(new Insets(10));
        quizzesBox.setStyle("-fx-padding: 10px; -fx-spacing: 10px;");

        for (Quiz quiz : quizzes) {
            Button quizButton = new Button(quiz.getQuizTitle());
            quizButton.setMaxWidth(350); // Make buttons stretch horizontally
            quizButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");

            // Set action to navigate to the QuizPage
            quizButton.setOnAction(e -> {
                QuizPage quizPage = new QuizPage(quizController.getQuizDao(), quiz.getQuizId(), stage);
                stage.setScene(quizPage.createScene());
            });

            quizzesBox.getChildren().add(quizButton);
        }

        updateQuizProgress(progressBar1);
        updateScoreProgress(progressBar2);

        // Add all components to the layout
        // Add all components to the layout
        this.getChildren().addAll(
                pageTitle,
                quizzesBox,
                buttonBox
        );

    }

    @Override
    public void updateQuizProgress(ProgressBar progressBar) {
        int completedQuizzes = userController.getQuizzesCompleted(userID);
        int allQuizzes = quizController.getAllQuizzes().size();
        double progress = (double) completedQuizzes / allQuizzes;
        progressBar.setProgress(progress);
    }

    @Override
    public void updateScoreProgress(ProgressBar progressBar) {
        int userScore = progressDao.getUserScore(userID);
        int maxScore = progressDao.getMaxScore(userID);
        double progress = (double) userScore / maxScore;
        progressBar.setProgress(progress);
    }

    @Override
    public void updateFlashcardProgress(ProgressBar progressBar) {
        // Not implemented
    }
}
