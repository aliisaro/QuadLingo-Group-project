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
        this.setAlignment(Pos.CENTER);

        // Elements
        Label pageTitle = new Label("Sign Up");
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create an HBox for the title and center it
        HBox titleContainer = new HBox(pageTitle);
        titleContainer.setAlignment(Pos.CENTER);  // Center the title horizontally

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        // Handle registerButton click
        Button registerButton = new Button("Sign up");
        registerButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        registerButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        registerButton.setOnAction(event -> handleRegisterAction(usernameField, emailField, passwordField, stage));

        // Back to Index Page button
        Button indexPageButton = new Button("Go back");
        indexPageButton .setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        indexPageButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

        Label hasAccountLabel = new Label("Already have an account? Login instead:");

        // Go to the login page
        Button loginButton = new Button("Go To Login Page");
        loginButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165");
        loginButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        loginButton.setOnAction(e -> stage.setScene(new LoginPage(stage).createScene()));

        // Create a container (HBox) for buttons
        HBox buttonContainer = new HBox(10);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.getChildren().addAll(registerButton, indexPageButton);

        // Allow the buttons to grow with the HBox
        HBox.setHgrow(registerButton, Priority.ALWAYS);
        HBox.setHgrow(indexPageButton, Priority.ALWAYS);

        // Add elements to layout
        this.getChildren().addAll(
                titleContainer,
                usernameLabel,
                usernameField,
                emailLabel,
                emailField,
                passwordLabel,
                passwordField,
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
