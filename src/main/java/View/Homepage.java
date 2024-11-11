package View;

import Config.LanguageConfig;
import Controller.UserController;
import Main.SessionManager;
import Model.User;
import Controller.QuizController;
import DAO.QuizDaoImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.util.ResourceBundle;

import Database.MariaDbConnection;

public class Homepage extends BasePage {

    private QuizController quizController; // Declare the QuizController
    private UserController userController; // UserController object
    private ResourceBundle bundle;

    private String normalButtonStyle;
    private String hoveredButtonStyle;

    public Homepage(Stage stage) {
        // Get the current logged-in user from the session
        User currentUser = SessionManager.getInstance().getCurrentUser();

        // If user is not logged in, redirect to index page
        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        // Use the global locale from Config
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

        // Initialize the QuizController with a new instance of QuizDaoImpl and a database connection
        Connection connection = MariaDbConnection.getConnection(); // Get the database connection
        this.quizController = new QuizController(new QuizDaoImpl(connection)); // Pass the connection

        // Set up the layout
        setLayout(stage, currentUser);

        // Close the connection when the application is exiting (or you might want to manage it elsewhere)
        stage.setOnCloseRequest(event -> {
            MariaDbConnection.terminate(connection);
        });
    }

    private void setLayout(Stage stage, User currentUser) {
        // Apply padding and spacing to the VBox
        this.setPadding(new Insets(10));

        // Set the alignment of the entire page to center
        this.setAlignment(Pos.CENTER);

        // Welcome message
        Label welcomeLabel = new Label(bundle.getString("welcomeLabel")+ currentUser.getUsername());
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Quiz Library button
        Button quizLibraryButton = new Button(bundle.getString("quizLibraryButton"));
        quizLibraryButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        quizLibraryButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        quizLibraryButton.setOnAction(e -> stage.setScene(new QuizLibrary(stage).createScene()));

        // Flashcard Library button
        Button flashcardLibraryButton = new Button(bundle.getString("flashcardLibraryButton"));
        flashcardLibraryButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        flashcardLibraryButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        flashcardLibraryButton.setOnAction(e -> stage.setScene(new FlashCardLibrary(stage).createScene()));

        // Achievements button
        Button achieButton = new Button(bundle.getString("achievementsButton"));
        achieButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        achieButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        achieButton.setOnAction(e -> stage.setScene(new AchiePage(stage).createScene()));

        // Profile button
        Button profileButton = new Button(bundle.getString("profileButton"));
        profileButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
        profileButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));

        // Logout button
        normalButtonStyle = "-fx-background-color: #e86c6c; -fx-font-size: 16px; -fx-padding: 10px;";
        hoveredButtonStyle = "-fx-background-color: #d9534f; -fx-font-size: 16px; -fx-padding: 10px;";

        Button logoutButton = new Button(bundle.getString("logoutButton"));
        logoutButton.setStyle(normalButtonStyle);
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(hoveredButtonStyle));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(normalButtonStyle));

        logoutButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new LoggedOutPage(stage).createScene());
        });

        // Create a container (vbox) for the buttons
        VBox buttonContainer = new VBox(10);
        buttonContainer.getChildren().addAll(quizLibraryButton, flashcardLibraryButton, achieButton, profileButton, logoutButton);

        buttonContainer.setAlignment(Pos.CENTER);
        VBox.setVgrow(buttonContainer, Priority.ALWAYS);  // Allow the VBox to take the full height

        // Add all components to the layout
        this.getChildren().addAll(
                welcomeLabel,
                buttonContainer
        );
    }
}
