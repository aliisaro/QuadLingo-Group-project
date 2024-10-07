package View;

import Controller.UserController;
import DAO.UserDaoImpl; // Import UserDaoImpl
import Main.SessionManager;
import Model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistrationPage extends BasePage {
    private UserController userController; // UserController object

    public RegistrationPage(Stage stage) {
        // Initialize UserDaoImpl and UserController objects
        userController = new UserController(new UserDaoImpl());

        // Set layout to the stage
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        // Apply padding directly to 'this' (inheriting VBox)
        this.setPadding(new Insets(10));
        this.setSpacing(5); // Add spacing between all child elements

        // Elements
        Label titleLabel = new Label("Sign Up");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Set VBox margin for the title
        VBox.setMargin(titleLabel, new Insets(0, 0, 0, 130));

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        // Handle registerButton click
        Button registerButton = new Button("Sign up");
        registerButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165");
        registerButton.setOnAction(event -> handleRegisterAction(usernameField, emailField, passwordField, stage));

        // Back to Index Page button
        Button indexPageButton = new Button("Go back");
        indexPageButton .setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165");
        indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

        // Add an empty label for spacing
        Label spacerLabel = new Label();
        spacerLabel.setMinHeight(10); // Set a minimum height for the spacer

        Label hasAccountLabel = new Label("Already have an account? Login instead:");

        // Go to the login page
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165");
        loginButton.setOnAction(e -> stage.setScene(new LoginPage(stage).createScene()));

        // Create a container (HBox) for buttons
        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(registerButton, indexPageButton);

        // Add elements to layout
        this.getChildren().addAll(
                titleLabel,
                usernameLabel,
                usernameField,
                emailLabel,
                emailField,
                passwordLabel,
                passwordField,
                spacerLabel,
                buttonContainer,
                hasAccountLabel,
                loginButton
        );
    }

    private void handleRegisterAction(TextField usernameField, TextField emailField, PasswordField passwordField, Stage stage) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        StringBuilder errorMessages = new StringBuilder(); // Object to store error messages

        // Basic validation: Check if fields are empty
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            errorMessages.append("All fields are required.\n");
        }
        // Email format validation
        else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errorMessages.append("Invalid email format.\n");
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

        // If there are errors, display them in an alert
        if (errorMessages.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText(null);
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        } else {
            // If validation passes, call the UserController to register the user
            User user = userController.createUser(username, password, email);
            if (user != null) {
                SessionManager.getInstance().setCurrentUser(user); // Start a new session
                stage.setScene(new Homepage(stage).createScene()); // Redirect to the homepage
                System.out.println("Registration successful: " + user.getUsername() + "\n");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error");
                alert.setHeaderText(null);
                alert.setContentText("Registration failed. Please try again.");
                alert.showAndWait();
                System.out.println("Registration failed.\n");
            }
        }
    }
}
