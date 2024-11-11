package View;

import Config.LanguageConfig;
import Controller.UserController;
import DAO.UserDaoImpl;
import Main.SessionManager;
import Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Profile extends BasePage {
    private UserController userController; // UserController object
    private ResourceBundle bundle;
    private ComboBox<String> languageComboBox;
    private String normalButtonStyle;
    private String hoveredButtonStyle;

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

        // Use the global locale from Config
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

        // Set layout to the stage
        setLayout(stage, currentUser);
    }

    private void setLayout(Stage stage, User currentUser) {
        // Apply padding directly to 'this' (inheriting VBox)
        this.setPadding(new Insets(10));

        // Page title
        Label pageTitle = new Label(bundle.getString("profilePageTitle"));
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create an HBox for the title and center it
        HBox titleContainer = new HBox(pageTitle);
        titleContainer.setAlignment(Pos.CENTER);  // Center the title horizontally

        // Display current user's username, email, and password
        Label usernameLabel = new Label(bundle.getString("currentUsernameLabel") + currentUser.getUsername());
        Label emailLabel = new Label(bundle.getString("currentEmailLabel")+ currentUser.getEmail());
        Label passwordLabel = new Label(bundle.getString("currentPasswordLabel"));

        // Change username field
        Label changeUsernameLabel = new Label(bundle.getString("changeUsernameLabel"));
        TextField usernameTextField = new TextField();

        // Change email field
        Label changeEmailLabel = new Label(bundle.getString("changeEmailLabel"));
        TextField emailTextField = new TextField();

        // Change password field
        Label changePasswordLabel = new Label(bundle.getString("changePasswordLabel"));
        PasswordField passwordTextField = new PasswordField();

        // Create a container (HBox) for save and logout buttons
        HBox buttonContainer1 = new HBox(10);
        buttonContainer1.setPadding(new Insets(20, 0, 5, 0));

        // Create a container (HBox) for back and progress buttons
        HBox buttonContainer2 = new HBox(10);
        buttonContainer2.setPadding(new Insets(5, 0,5 , 0));

        // Create a container (HBox) for choosing language
        HBox buttonContainer3 = new HBox(10);
        buttonContainer3.setPadding(new Insets(5, 0,5 , 0));

        // Language selection ComboBox
        languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("English", "French", "Chinese", "Arabic");
        languageComboBox.setValue(bundle.getString("language.key")); // Current selection
        System.out.println("selected language" + bundle.getString("language.key"));
        languageComboBox.setOnAction(e -> switchLanguage(languageComboBox.getValue()));

        // Layout language selector
        /* VBox buttonContainer = new VBox(10, languageComboBox);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.setAlignment(Pos.CENTER);
        */

        // Save Button to handle saving the profile information
        Button saveButton = new Button(bundle.getString("saveChangesButton"));
        saveButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165");
        saveButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        saveButton.setOnAction(e -> handleSaveAction(usernameTextField, emailTextField, passwordTextField, currentUser));

        // Go to the Progress page
        Button buttonProgress = new Button(bundle.getString("progressPageButton"));
        buttonProgress.setStyle("-fx-font-size: 14px; -fx-padding: 10px;-fx-pref-width: 165");
        buttonProgress.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        buttonProgress.setOnAction(e -> stage.setScene(new ProgressPage(stage).createScene()));

        // Logout button: clears session and redirects to Logged Out page
        normalButtonStyle = "-fx-background-color: #e86c6c; -fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165";
        hoveredButtonStyle = "-fx-background-color: #d9534f; -fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165";

        Button logoutButton = new Button(bundle.getString("logoutButton"));
        logoutButton.setStyle(normalButtonStyle);
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(hoveredButtonStyle));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(normalButtonStyle));
        logoutButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new LoggedOutPage(stage).createScene());
        });

        // Back to the homepage
        Button backButton = new Button(bundle.getString("backToHomeButton"));
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;-fx-pref-width: 165");
        backButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        backButton.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        // Add buttons to the first button container
        buttonContainer1.getChildren().addAll(saveButton, buttonProgress);


        // Add buttons to the second button container
        buttonContainer2.getChildren().addAll(backButton, logoutButton);

        // Add language options to third container
        buttonContainer3.getChildren().addAll(languageComboBox);

        // Allow the buttons to grow with the HBox
        HBox.setHgrow(backButton, Priority.ALWAYS);
        HBox.setHgrow(logoutButton, Priority.ALWAYS);
        HBox.setHgrow(buttonProgress, Priority.ALWAYS);
        HBox.setHgrow(saveButton, Priority.ALWAYS);

        // Add all components to the main layout
        this.getChildren().addAll(
                titleContainer,
                usernameLabel,
                emailLabel,
                passwordLabel,
                changeUsernameLabel,
                usernameTextField,
                changeEmailLabel,
                emailTextField,
                changePasswordLabel,
                passwordTextField,
                buttonContainer1,
                buttonContainer2,
                buttonContainer3
        );
    }

    // Logic to handle saving the profile information
    private void handleSaveAction(TextField usernameTextField, TextField emailTextField, PasswordField passwordTextField, User currentUser) {
        UserController userController = UserController.getInstance(UserDaoImpl.getInstance()); // Initialize UserController

        // Trim whitespace from inputs
        String username = usernameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        // Check if all fields are empty
        if (username.isEmpty() && email.isEmpty() && password.isEmpty()) {
            showAlert(bundle.getString("errorAlertTitle"), bundle.getString("emptyFieldsAlert"));
            return; // Exit the method early
        }

        StringBuilder errorMessages = new StringBuilder(); // Object to store error messages

        // Validate username if provided
        if (!username.isEmpty() && !username.equals(currentUser.getUsername())) {
            currentUser.setUsername(username);
        }

        // Validate email if provided
        if (!email.isEmpty() && !email.equals(currentUser.getEmail())) {
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                errorMessages.append(bundle.getString("invalidEmail")).append("\n"); // Invalid email format.
            } else if (userController.doesEmailExist(email)) {
                errorMessages.append(bundle.getString("accountExists")).append("\n"); // An account with this email already exists.
            } else {
                currentUser.setEmail(email);
            }
        }

        // Validate password if provided
        if (!password.isEmpty()) {
            if (!password.matches(".*[A-Z].*")) {
                errorMessages.append(bundle.getString("oneUppercaseLetter")).append("\n"); // Password must include at least 1 uppercase letter.
            }
            if (!password.matches(".*\\d.*")) {
                errorMessages.append(bundle.getString("oneNumber")).append("\n"); // Password must include at least 1 number.
            }
            if (password.length() < 8) {
                errorMessages.append(bundle.getString("atLeastEight")).append("\n"); // Password must be at least 8 characters.
            } else {
                currentUser.setPassword(password, true);
            }
        }

        // If there are errors, display them as an alert
        if (errorMessages.length() > 0) {
            showAlert(bundle.getString("errorAlertTitle"), errorMessages.toString());
        } else {
            // If no errors, update the user
            boolean isUpdated = userController.updateUser(currentUser);
            if (isUpdated) {
                showAlert(bundle.getString("successAlertTitle"), bundle.getString("profileUpdateSuccessAlert"));
                System.out.println("Profile updated successfully:");
                System.out.println("Username: " + currentUser.getUsername());
                System.out.println("Email: " + currentUser.getEmail());
                System.out.println("Password: ********* \n");

                // Clear the input fields
                usernameTextField.clear();
                emailTextField.clear();
                passwordTextField.clear();

                // Refresh the page to display updated info
                refreshProfilePage();
            } else {
                showAlert(bundle.getString("errorAlertTitle"), bundle.getString("profileUpdateFailedAlert"));
                System.out.println("Failed to update profile.\n");
            }
        }
    }

    private void switchLanguage(String language) {
        Locale locale;
        switch (language) {
            case "French":
                locale = new Locale("fr");
                break;
            case "Chinese":
                locale = new Locale("ch"); // Simplified Chinese
                break;
            case "Arabic":
                locale = new Locale("ar");
                break;
            default:
                locale = Locale.ENGLISH;
                break;
        }

        // Update the global locale
        LanguageConfig.getInstance().setCurrentLocale(locale);

        // Load the ResourceBundle with the selected locale
        bundle = ResourceBundle.getBundle("bundle", locale);
        // updateTexts(); // Update UI texts
    }

    // Method to refresh the profile page
    private void refreshProfilePage() {
        Stage currentStage = (Stage) this.getScene().getWindow();
        currentStage.setScene(new Profile(currentStage).createScene());
    }

    // Method to show an alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
