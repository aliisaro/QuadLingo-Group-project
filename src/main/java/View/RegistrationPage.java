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

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Register button action logic
        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            // Basic validation
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                errorLabel.setText("All fields are required.");
                return;
            }

            // Call the UserController to register the user
            User user= userController.createUser(username, password, email);

            if (user != null) {
                // Set the current user in session (login user)
                SessionManager.getInstance().setCurrentUser(user);

                System.out.println("Registration successful: " + user.getUsername());

                // Navigate to the homepage
                stage.setScene(new Homepage(stage).createScene());
            } else {
                errorLabel.setText("Registration failed. Please try again.");
                System.out.println("Registration failed.");
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
                errorLabel,
                indexPageButton
        );
    }
}
