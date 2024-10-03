package View;

import Controller.UserController;
import Controller.QuizController; // Ensure you have a QuizController
import DAO.UserDaoImpl;
import DAO.QuizDaoImpl;
import Database.MariaDbConnection;
import Main.SessionManager;
import Model.User;
import Model.Quiz;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.List;

public class QuizLibrary extends BasePage {
    private UserController userController; // UserController object
    private QuizController quizController; // QuizController object

    public QuizLibrary(Stage stage) {
        // Initialize UserDaoImpl and UserController objects
        userController = new UserController(new UserDaoImpl());

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

        // Return to the homepage
        Button backButton = new Button("Back to Main");
        backButton.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        // Logout button: clears session and redirects to IndexPage
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new IndexPage(stage).createScene());
        });

        // Fetch all quizzes from the database
        List<Quiz> quizzes = quizController.getAllQuizzes();

        // VBox to hold quiz buttons
        VBox quizzesBox = new VBox(10);
        quizzesBox.setPadding(new Insets(10));
        quizzesBox.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px; -fx-spacing: 10px;");

        for (Quiz quiz : quizzes) {
            Button quizButton = new Button(quiz.getQuizTitle());
            quizButton.setMaxWidth(Double.MAX_VALUE); // Make buttons stretch horizontally
            quizButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");

            // Set action to navigate to the QuizPage
            quizButton.setOnAction(e -> {
                QuizPage quizPage = new QuizPage(quizController.getQuizDao(), quiz.getQuizId(), stage);
                stage.setScene(quizPage.createScene());
            });

            quizzesBox.getChildren().add(quizButton);
        }

        // Add all components to the layout
        this.getChildren().addAll(
                pageTitle,
                quizzesBox,
                backButton,
                logoutButton
        );

    }
}
