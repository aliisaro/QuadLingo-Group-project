package View;

import Controller.FlashCardController;
import Controller.UserController;
import DAO.FCImplement;
import DAO.UserDaoImpl;
import Database.MariaDbConnection;
import Main.SessionManager;
import Model.FlashCard;
import Model.User;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.List;
import javafx.scene.layout.VBox;

public class FlashCardLibrary extends BasePage {
    private final UserController userController = new UserController(new UserDaoImpl());
    private final int userID = userController.getCurrentUserId();
    private FlashCardController flashCardController;

    public FlashCardLibrary(Stage stage) {
        //Get current user
        User currentUser = SessionManager.getInstance().getCurrentUser();

        //If user is not logged in, redirect to index page
        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        // Initialize FlashCardController with database connection
        Connection connection = MariaDbConnection.getConnection();
        flashCardController = new FlashCardController(new FCImplement(connection));

        // Set layout to the stage
        setLayout(stage, currentUser, connection);
    }

    private void setLayout(Stage stage, User currentUser, Connection connection) {
        //Page title
        Label pageTitle = new Label("Flashcard Library");
        pageTitle.setStyle("-fx-font-size: 24px; -fx-padding: 10px;");

        // Return to the homepage
        Button backButton = new Button("Back to Homepage");
        backButton.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        // Logout button: clears session and redirects to IndexPage
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new IndexPage(stage).createScene());
        });

        // Fetch FlashCard topics
        List<FlashCard> topics = flashCardController.getTopics();

        // Create a VBox to hold the topics
        VBox topicBox = new VBox();
        topicBox.setPadding(new Insets(10));
        topicBox.setStyle("-fx-background-color: #FFFFFF; -fx-padding: 10px; -fx-spacing: 10px;");

        // Add each topic to the VBox
        for (FlashCard topic : topics) {
            Button topicButton = new Button(topic.getTopic());
            topicButton.maxWidth(350);

            // Set the action for the topic button
            topicButton.setOnAction(e -> {
                FlashCardsPage flashCardsPage = new FlashCardsPage(flashCardController.getFlashCardDao(), topic.getTopic(), stage);
                stage.setScene(flashCardsPage.createScene());
            });
            topicBox.getChildren().add(topicButton);
        }

        // Add all components to the layout
        this.getChildren().addAll(pageTitle, backButton, logoutButton, topicBox);
    }
}