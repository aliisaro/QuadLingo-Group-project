package View;

import Controller.UserController;
import DAO.UserDaoImpl; // Import UserDaoImpl
import Main.SessionManager;
import Model.User;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginPage extends BasePage {
    private UserController userController;

    public LoginPage(Stage stage) {
        // Initialize UserDaoImpl and UserController
        userController = new UserController(new UserDaoImpl());

        // Set layout to the stage
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        // Create and configure the login page UI components
        Label titleLabel = new Label("Login");

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Handle login button click
        loginButton.setOnAction(e -> {
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
                    System.out.println("Login successful: " + user.getUsername());
                    return; // Exit early on success
                }
            }

            errorLabel.setText(errorMessages.toString()); // Display error messages
        });



        // Go back to the index page
        Button indexPageButton = new Button("Go back to Index page");
        indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

        // Add elements to layout
        getChildren().addAll(
                titleLabel,
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
                loginButton,
                errorLabel,
                indexPageButton);
    }
}

