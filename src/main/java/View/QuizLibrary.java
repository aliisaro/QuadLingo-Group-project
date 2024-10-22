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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
        // Apply padding and spacing to the VBox
        this.setPadding(new Insets(10));

        // Set the alignment of the entire page to center
        this.setAlignment(Pos.CENTER);

        // Page title
        Label pageTitle = new Label("Quiz Library");
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Return to the homepage button
        Button backButton = new Button("Back to Homepage");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        backButton.setMaxWidth(Double.MAX_VALUE); // Allow responsiveness, max width based on window size
        backButton.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        // Logout button: clears session and redirects to IndexPage
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        logoutButton.setMaxWidth(Double.MAX_VALUE); // Allow responsiveness, max width based on window size
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new IndexPage(stage).createScene());
        });

        // Group the buttons in an HBox
        HBox buttonBox = new HBox(10); // 10px spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backButton, logoutButton);

        // Fetch all quizzes from the database
        List<Quiz> quizzes = quizController.getAllQuizzes();

        // VBox to hold quiz buttons
        VBox quizzesBox = new VBox(10); // 10px spacing between quiz buttons
        quizzesBox.setPadding(new Insets(10));
        quizzesBox.setStyle("-fx-padding: 10px; -fx-spacing: 10px;");
        quizzesBox.setAlignment(Pos.CENTER);

        // Add quiz buttons
        for (Quiz quiz : quizzes) {
            Button quizButton = new Button(quiz.getQuizTitle());
            quizButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
            quizButton.setMaxWidth(Double.MAX_VALUE); // Allow quiz buttons to stretch fully in their container

            HBox.setHgrow(quizButton, Priority.ALWAYS);

            quizButton.setOnAction(e -> {
                QuizPage quizPage = new QuizPage(quizController.getQuizDao(), quiz.getQuizId(), stage);
                stage.setScene(quizPage.createScene());
            });
            quizzesBox.getChildren().add(quizButton);
        }

        // Allow the buttons to grow with the HBox
        HBox.setHgrow(backButton, Priority.ALWAYS);
        HBox.setHgrow(logoutButton, Priority.ALWAYS);

        // Update progress bars
        updateQuizProgress(progressBar1);
        updateScoreProgress(progressBar2);

        // Add components to the layout
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
