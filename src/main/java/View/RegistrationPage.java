package View;

import Controller.UserController;
import DAO.UserDaoImpl; // Import UserDaoImpl
import Main.SessionManager;
import Model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistrationPage extends BasePage {
    private UserController userController;

    public RegistrationPage(Stage stage) {
        // Initialize UserDaoImpl and UserController
        userController = new UserController(new UserDaoImpl());

        // Set layout to the stage
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        // Elements
        Label titleLabel = new Label("Create an account");

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        Button registerButton = new Button("Register");

        Label minimumRequirements = new Label("Minimum password requirements: \n1 uppercase, 1 number, 8 characters.");
        minimumRequirements.setStyle("-fx-text-fill: green;");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");


        // Handle registerButton click
        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            StringBuilder errorMessages = new StringBuilder(); // Object to store error messages

            // Basic validation: Check if fields are empty
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                errorMessages.append("All fields are required.\n");
            }
            // Check if the email is already registered
            else if (userController.doesEmailExist(email)) {
                errorMessages.append("An account with this email already exists.\n");
            }

            // Password validation
            if (!password.matches(".*[A-Z].*")) {
                errorMessages.append("Password must include at least 1 uppercase letter.\n");
            }
            if (!password.matches(".*\\d.*")) {
                errorMessages.append("Password must include at least 1 number.\n");
            }
            if (password.length() < 8) {
                errorMessages.append("Password must be at least 8 characters.\n");
            }

            // If there are errors, display them
            if (errorMessages.length() > 0) {
                errorLabel.setText(errorMessages.toString());
            } else {
                // If validation passes, call the UserController to register the user
                User user = userController.createUser(username, password, email);
                if (user != null) {
                    SessionManager.getInstance().setCurrentUser(user); // Start a new session
                    stage.setScene(new Homepage(stage).createScene()); // Redirect to the homepage
                    System.out.println("Registration successful: " + user.getUsername());
                } else {
                    errorLabel.setText("Registration failed. Please try again.");
                }
            }
        });


        // Back to Index Page button
        Button indexPageButton = new Button("Go to back to Index page");
        indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

        // Add elements to layout
        this.getChildren().addAll(
                titleLabel,
                usernameLabel,
                usernameField,
                emailLabel,
                emailField,
                passwordLabel,
                passwordField,
                registerButton,
                minimumRequirements,
                errorLabel,
                indexPageButton
        );
    }
}
