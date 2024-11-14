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
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class LoginPage extends BasePage {
    private UserController userController;
    private ResourceBundle bundle;

    public LoginPage(Stage stage) {
        // Initialize UserDaoImpl and UserController objects
        userController = new UserController(new UserDaoImpl());

        // Use the global locale from Config
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale()); // Initialize bundle here

        // Set layout to the stage
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        // Apply padding directly to 'this' (inheriting VBox)
        this.setPadding(new Insets(10));
        this.setSpacing(5); // Add spacing between all child elements
        this.setAlignment(Pos.CENTER);

        // Create and configure the login page UI components
        Label pageTitle = new Label(bundle.getString("login")); // Use bundle for page title
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create an HBox for the title and center it
        HBox titleContainer = new HBox(pageTitle);
        // Center the title horizontally
        titleContainer.setAlignment(Pos.CENTER);

        Label emailLabel = new Label(bundle.getString("emailLabel")); // Use bundle for label
        TextField emailField = new TextField();

        Label passwordLabel = new Label(bundle.getString("passwordLabel")); // Use bundle for label
        PasswordField passwordField = new PasswordField();

        // Handle login button click
        Button loginButton = new Button(bundle.getString("login")); // Use bundle for button text
        loginButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        loginButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        loginButton.setOnAction(e -> handleLoginAction(emailField, passwordField, stage));

        // Go back to the index page
        Button indexPageButton = new Button(bundle.getString("goBackButton")); // Use bundle for button text
        indexPageButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        indexPageButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

        Label noAccountLabel = new Label(bundle.getString("noAccountMessage")); // Use bundle for label text

        // Go to the registration page
        Button signUpButton = new Button(bundle.getString("signUp")); // Use bundle for button text
        signUpButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        signUpButton.setMaxWidth(Double.MAX_VALUE); // Allow the button to expand horizontally
        signUpButton.setOnAction(e -> stage.setScene(new SignUpPage(stage).createScene()));

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
                emailLabel,
                emailField,
                passwordLabel,
                passwordField,
                buttonContainer,
                noAccountLabel,
                signUpButton);
    }

    private void handleLoginAction(TextField emailField, PasswordField passwordField, Stage stage) {
        String email = emailField.getText();
        String password = passwordField.getText();

        StringBuilder errorMessages = new StringBuilder(); // Object to store error messages

        // Basic validation: Check if fields are empty
        if (email.isEmpty() || password.isEmpty()) {
            errorMessages.append(bundle.getString("allFieldsRequired")).append("\n"); // All fields are required.
        } else if (!userController.doesEmailExist(email)) { // Check if the user exists
            errorMessages.append(bundle.getString("userDoesNotExist")).append("\n"); // User does not exist.
        } else {
            // Attempt to log in
            User user = userController.loginUser(email, password);
            if (user == null) {
                errorMessages.append(bundle.getString("incorrectPassword")).append("\n"); // Incorrect password.
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
            alert.setTitle(bundle.getString("loginErrorTitle")); // Use bundle for alert title
            alert.setHeaderText(null);
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        }
    }
}
