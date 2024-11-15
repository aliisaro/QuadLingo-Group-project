package View;

import Config.LanguageConfig;
import Controller.UserController;
import DAO.UserDaoImpl; // Import UserDaoImpl
import Main.SessionManager;
import Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class SignUpPage extends BasePage {
    private final UserController userController; // UserController object
    private final ResourceBundle bundle;

    public SignUpPage(Stage stage) {
        // Initialize UserDaoImpl and UserController objects
        userController = new UserController(new UserDaoImpl());
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

        // Set layout to the stage
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        // Apply padding directly to 'this' (inheriting VBox)
        this.setPadding(new Insets(10));
        this.setSpacing(5); // Add spacing between all child elements
        this.setAlignment(Pos.CENTER);

        // Elements
        Label pageTitle = new Label(bundle.getString("signUp"));
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create an HBox for the title and center it
        HBox titleContainer = new HBox(pageTitle);
        titleContainer.setAlignment(Pos.CENTER);  // Center the title horizontally

        Label usernameLabel = new Label(bundle.getString("usernameLabel"));
        TextField usernameField = new TextField();

        Label emailLabel = new Label(bundle.getString("emailLabel"));
        TextField emailField = new TextField();

        Label passwordLabel = new Label(bundle.getString("passwordLabel"));
        PasswordField passwordField = new PasswordField();

        // Handle registerButton click
        Button signUpButton = new Button(bundle.getString("signUp"));
        signUpButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        signUpButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        signUpButton.setOnAction(event -> handleRegisterAction(usernameField, emailField, passwordField, stage));

        // Back to Index Page button
        Button indexPageButton = new Button(bundle.getString("goBackButton"));
        indexPageButton .setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        indexPageButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

        Label hasAccountLabel = new Label(bundle.getString("hasAccountLabel"));

        // Go to the login page
        Button loginButton = new Button(bundle.getString("login"));
        loginButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165");
        loginButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        loginButton.setOnAction(e -> stage.setScene(new LoginPage(stage).createScene()));

        // Create a container (HBox) for buttons
        HBox buttonContainer = new HBox(10);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.getChildren().addAll(signUpButton, indexPageButton);

        // Allow the buttons to grow with the HBox
        HBox.setHgrow(signUpButton, Priority.ALWAYS);
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
            errorMessages.append(bundle.getString("allFieldsRequired")).append("\n"); // All fields are required.
        } else {
            // Email format validation
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                errorMessages.append(bundle.getString("invalidEmail")).append("\n"); // Invalid email format.
            }
            // Check if the email is already registered
            if (userController.doesEmailExist(email)) {
                errorMessages.append(bundle.getString("accountExists")).append("\n"); // An account with this email already exists.
            }

            // Password validation (only if the password field is not empty)
            if (!password.isEmpty()) {
                if (!password.matches(".*[A-Z].*")) {
                    errorMessages.append(bundle.getString("oneUppercaseLetter")).append("\n"); // Password must include at least 1 uppercase letter.
                }
                if (!password.matches(".*\\d.*")) {
                    errorMessages.append(bundle.getString("oneNumber")).append("\n"); // Password must include at least 1 number.
                }
                if (password.length() < 8) {
                    errorMessages.append(bundle.getString("atLeastEight")).append("\n"); // Password must be at least 8 characters.
                }
            }
        }

        // If there are errors, display them in an alert
        if (errorMessages.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("signUpErrorTitle"));
            alert.setHeaderText(null);
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        } else {
            // If validation passes, call the UserController to register the user
            User user = userController.createUser(username, password, email);
            if (user != null) {
                SessionManager.getInstance().setCurrentUser(user); // Start a new session
                stage.setScene(new LoginPage(stage).createScene()); // Redirect to the login page
                System.out.println("Sign up successful: " + user.getUsername() + "\n");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(bundle.getString("signUpErrorTitle"));
                alert.setHeaderText(null);
                alert.setContentText(bundle.getString("errorContext")); // Register failed. Please try again.
                alert.showAndWait();
                System.out.println("Sign up failed.\n");
            }
        }
    }

}
