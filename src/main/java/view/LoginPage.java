package view;

import Config.LanguageConfig;
import Controller.UserController;
import Dao.UserDaoImpl;
import Main.SessionManager;
import Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginPage extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(LoginPage.class.getName());
    private static final String BUTTON_STYLE = "-fx-font-size: 14px; -fx-padding: 10px;";

    private UserController userController;
    private ResourceBundle bundle;

    public LoginPage(Stage stage) {
        // Initialize UserDaoImpl and UserController objects
        userController = new UserController(new UserDaoImpl());

        // Use the global locale from Config
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

        // Set layout to the stage
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        this.setPadding(new Insets(10));
        this.setSpacing(5);
        this.setAlignment(Pos.CENTER);

        // Page title
        Label pageTitle = new Label(bundle.getString("login"));
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        HBox titleContainer = new HBox(pageTitle);
        titleContainer.setAlignment(Pos.CENTER);

        // Email field
        Label emailLabel = new Label(bundle.getString("emailLabel"));
        TextField emailField = new TextField();

        // Password field
        Label passwordLabel = new Label(bundle.getString("passwordLabel"));
        PasswordField passwordField = new PasswordField();

        // Login button
        Button loginButton = new Button(bundle.getString("login"));
        loginButton.setStyle(BUTTON_STYLE);
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setOnAction(e -> handleLoginAction(emailField, passwordField, stage));

        // Go back button
        Button indexPageButton = new Button(bundle.getString("goBackButton"));
        indexPageButton.setStyle(BUTTON_STYLE);
        indexPageButton.setMaxWidth(Double.MAX_VALUE);
        indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

        // No account label
        Label noAccountLabel = new Label(bundle.getString("noAccountMessage"));

        // Sign up button
        Button signUpButton = new Button(bundle.getString("signUp"));
        signUpButton.setStyle(BUTTON_STYLE);
        signUpButton.setMaxWidth(Double.MAX_VALUE);
        signUpButton.setOnAction(e -> stage.setScene(new SignUpPage(stage).createScene()));

        // Button container
        HBox buttonContainer = new HBox(10);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.getChildren().addAll(loginButton, indexPageButton);
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
                signUpButton
        );
    }

    private void handleLoginAction(TextField emailField, PasswordField passwordField, Stage stage) {
        String email = emailField.getText();
        String password = passwordField.getText();

        StringBuilder errorMessages = new StringBuilder();

        // Basic validation
        if (email.isEmpty() || password.isEmpty()) {
            errorMessages.append(bundle.getString("allFieldsRequired")).append("\n");
        } else if (!userController.doesEmailExist(email)) {
            errorMessages.append(bundle.getString("userDoesNotExist")).append("\n");
        } else {
            User user = userController.loginUser(email, password);
            if (user == null) {
                errorMessages.append(bundle.getString("incorrectPassword")).append("\n");
            } else {
                // Successful login
                SessionManager.getInstance().setCurrentUser(user);
                stage.setScene(new Homepage(stage).createScene());
                LOGGER.log(Level.INFO, "Login successful: {0}", user.getUsername());
                return;
            }
        }

        // Display error messages
        if (errorMessages.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(bundle.getString("loginErrorTitle"));
            alert.setHeaderText(null);
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        }
    }
}
