package View;

import Controller.UserController;
import DAO.UserDaoImpl;
import Main.SessionManager;
import Model.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Profile extends BasePage {
    private UserController userController; // UserController object

    public Profile(Stage stage) {
        // Initialize UserDaoImpl and UserController objects
        userController = new UserController(new UserDaoImpl());

        // Get the current logged-in user from the session
        User currentUser = SessionManager.getInstance().getCurrentUser();

        // If user is not logged in, redirect to index page
        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        // Set layout to the stage
        setLayout(stage, currentUser);
    }

    private void setLayout(Stage stage, User currentUser) {
        // Page title
        Label pageTitle = new Label("Profile");

        // Display current user's username, email, and password
        Label usernameLabel = new Label("Username: " + currentUser.getUsername());
        Label emailLabel = new Label("Email: " + currentUser.getEmail());
        Label passwordLabel = new Label("Password: **********");

        // Change username field
        Label changeUsernameLabel = new Label("Change username:");
        TextField usernameTextField = new TextField();

        // Change email field
        Label changeEmailLabel = new Label("Change email:");
        TextField emailTextField = new TextField();

        // Change password field
        Label changePasswordLabel = new Label("Change password:");
        PasswordField passwordTextField = new PasswordField();

        // Labels for error and success messages
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Label successLabel = new Label();
        successLabel.setStyle("-fx-text-fill: green;");

        // Save Button to handle saving the profile information
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> handleSaveAction(usernameTextField, emailTextField, passwordTextField, currentUser, errorLabel, successLabel));

        // Return to the homepage
        Button backButton = new Button("Back to Main");
        backButton.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        // Go to the Progress page
        Button buttonProgress = new Button("Go to Progress");
        buttonProgress.setOnAction(e -> stage.setScene(new ProgressPage(stage).createScene()));

        // Logout button: clears session and redirects to IndexPage
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new IndexPage(stage).createScene());
        });

        // Layout setup
        this.getChildren().addAll(
                pageTitle,
                usernameLabel,
                emailLabel,
                passwordLabel,
                changeUsernameLabel,
                usernameTextField,
                changeEmailLabel,
                emailTextField,
                changePasswordLabel,
                passwordTextField,
                saveButton,
                backButton,
                buttonProgress,
                logoutButton,
                errorLabel,
                successLabel
        );
    }

    // Logic to handle saving the profile information
    private void handleSaveAction(TextField usernameTextField, TextField emailTextField, TextField passwordTextField, User currentUser, Label errorLabel, Label successLabel) {
        UserController userController = UserController.getInstance(UserDaoImpl.getInstance()); // Initialize UserController

        StringBuilder errorMessages = new StringBuilder(); // Object to store error messages

        // Validate username if provided
        String username = usernameTextField.getText();
        if (!username.isEmpty() && !username.equals(currentUser.getUsername())) {
            currentUser.setUsername(username);
        }

        // Validate email if provided
        String email = emailTextField.getText();
        if (!email.isEmpty() && !email.equals(currentUser.getEmail())) {
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                errorMessages.append("Invalid email format.\n");
            }
            // Check if the email is already registered
            else if (userController.doesEmailExist(email)) {
                errorMessages.append("An account with this email already exists.\n");
            } else {
                currentUser.setEmail(email);
            }
        }

        // Validate password if provided
        String password = passwordTextField.getText();
        if (!password.isEmpty()) {
            if (!password.matches(".*[A-Z].*")) {
                errorMessages.append("Password must include at least 1 uppercase letter.\n");
            }
            if (!password.matches(".*\\d.*")) {
                errorMessages.append("Password must include at least 1 number.\n");
            }
            if (password.length() < 8) {
                errorMessages.append("Password must be at least 8 characters.\n");
            } else {
                currentUser.setPassword(password, true);
            }
        }

        // If there are errors, display them
        if (errorMessages.length() > 0) {
            errorLabel.setText(errorMessages.toString());
            successLabel.setText(""); // Clear success message
        } else {
            // If no errors, update the user
            boolean isUpdated = userController.updateUser(currentUser);
            if (isUpdated) {
                successLabel.setText("Profile updated successfully.");
                errorLabel.setText(""); // Clear error message

                System.out.println("Profile updated successfully:");
                System.out.println("Username: " + currentUser.getUsername());
                System.out.println("Email: " + currentUser.getEmail());
                System.out.println("Password: " + currentUser.getPassword() + "\n");

                // Clear the input fields
                usernameTextField.clear();
                emailTextField.clear();
                passwordTextField.clear();

                // Refresh the page to display updated info
                refreshProfilePage();
            } else {
                errorLabel.setText("Failed to update profile.");
                successLabel.setText(""); // Clear success message
                System.out.println("Failed to update profile.\n");
            }
        }
    }

    // Method to refresh the profile page
    private void refreshProfilePage() {
        Stage currentStage = (Stage) this.getScene().getWindow();
        currentStage.setScene(new Profile(currentStage).createScene());
    }
}
