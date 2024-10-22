package View;

import Controller.UserController;
import DAO.UserDaoImpl; // Import UserDaoImpl
import Main.SessionManager;
import Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPage extends BasePage {
    private UserController userController; // UserController object

    public LoginPage(Stage stage) {
        // Initialize UserDaoImpl and UserController objects
        userController = new UserController(new UserDaoImpl());

        // Set layout to the stage
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        // Apply padding directly to 'this' (inheriting VBox)
        this.setPadding(new Insets(10));
        this.setSpacing(5); // Add spacing between all child elements
        this.setAlignment(Pos.CENTER);

        // Create and configure the login page UI components
        Label pageTitle = new Label("Login");
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create an HBox for the title and center it
        HBox titleContainer = new HBox(pageTitle);
        titleContainer.setAlignment(Pos.CENTER);  // Center the title horizontally

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        // Handle login button click
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        loginButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        loginButton.setOnAction(e -> handleLoginAction(usernameField, passwordField, stage));

        // Go back to the index page
        Button indexPageButton = new Button("Go back");
        indexPageButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        indexPageButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

        Label noAccountLabel = new Label("Don't have an account? Sign up instead:");

        // Go to the registration page
        Button registerButton = new Button("Go to Sign Up Page");
        registerButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        registerButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        registerButton.setOnAction(e -> stage.setScene(new RegistrationPage(stage).createScene()));

        // Create a container (HBox) for buttons
        HBox buttonContainer = new HBox(10);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.getChildren().addAll(loginButton, indexPageButton);

        // Allow the buttons to grow with the HBox
        HBox.setHgrow(loginButton, Priority.ALWAYS);
        HBox.setHgrow(indexPageButton, Priority.ALWAYS);

        // Add elements to layout
        getChildren().addAll(
                titleContainer,
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
                buttonContainer,
                noAccountLabel,
                registerButton);
    }

    private void handleLoginAction(TextField usernameField, PasswordField passwordField, Stage stage) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        StringBuilder errorMessages = new StringBuilder(); // Object to store error messages

        // Basic validation: Check if fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            errorMessages.append("All fields are required.\n");
        } else if (!userController.doesUsernameExist(username)) { // Check if the user exists
            errorMessages.append("User not found. Please check your username.\n");
        } else {
            // Attempt to log in
            User user = userController.loginUser(username, password);
            if (user == null) {
                errorMessages.append("Invalid password. Please try again.\n");
            } else {
                // Successful login
                SessionManager.getInstance().setCurrentUser(user); // Start a new session
                stage.setScene(new Homepage(stage).createScene()); // Redirect to the homepage
                System.out.println("Login successful: " + user.getUsername() + "\n");
                return;
            }
        }

        // If there are error messages, show them in an alert
        if (errorMessages.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        }
    }
}
