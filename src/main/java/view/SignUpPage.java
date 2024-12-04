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

public class SignUpPage extends BasePage {
  private static final Logger LOGGER = Logger.getLogger(SignUpPage.class.getName());
  private final UserController userController;
  private final ResourceBundle bundle;

  public SignUpPage(Stage stage) {
    userController = new UserController(new UserDaoImpl());
    this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());
    setLayout(stage);
  }

  private void setLayout(Stage stage) {
    this.setPadding(new Insets(10));
    this.setSpacing(5);
    this.setAlignment(Pos.CENTER);

    Label pageTitle = new Label(bundle.getString("signUp"));
    pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

    HBox titleContainer = new HBox(pageTitle);
    titleContainer.setAlignment(Pos.CENTER);

    Label usernameLabel = new Label(bundle.getString("usernameLabel"));
    TextField usernameField = new TextField();

    Label emailLabel = new Label(bundle.getString("emailLabel"));
    TextField emailField = new TextField();

    Label passwordLabel = new Label(bundle.getString("passwordLabel"));
    PasswordField passwordField = new PasswordField();

    Button signUpButton = new Button(bundle.getString("signUp"));
    signUpButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
    signUpButton.setMaxWidth(Double.MAX_VALUE);
    signUpButton.setOnAction(event -> handleRegisterAction(usernameField, emailField, passwordField, stage));

    Button indexPageButton = new Button(bundle.getString("goBackButton"));
    indexPageButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
    indexPageButton.setMaxWidth(Double.MAX_VALUE);
    indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

    Label hasAccountLabel = new Label(bundle.getString("hasAccountLabel"));

    Button loginButton = new Button(bundle.getString("login"));
    loginButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165");
    loginButton.setMaxWidth(Double.MAX_VALUE);
    loginButton.setOnAction(e -> stage.setScene(new LoginPage(stage).createScene()));

    HBox buttonContainer = new HBox(10);
    buttonContainer.setPadding(new Insets(10, 0, 0, 0));
    buttonContainer.getChildren().addAll(signUpButton, indexPageButton);
    HBox.setHgrow(signUpButton, Priority.ALWAYS);
    HBox.setHgrow(indexPageButton, Priority.ALWAYS);

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

    String errorMessages = validateInputs(username, email, password);

    if (!errorMessages.isEmpty()) {
      showErrorAlert(errorMessages);
    } else {
      processRegistration(username, email, password, stage);
    }
  }

  private String validateInputs(String username, String email, String password) {
    StringBuilder errorMessages = new StringBuilder();

    if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
      errorMessages.append(bundle.getString("allFieldsRequired")).append("\n");
    } else {
      if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
        errorMessages.append(bundle.getString("invalidEmail")).append("\n");
      }
      if (userController.doesEmailExist(email)) {
        errorMessages.append(bundle.getString("accountExists")).append("\n");
      }
      if (!password.isEmpty()) {
        validatePassword(password, errorMessages);
      }
    }

    return errorMessages.toString();
  }

  private void validatePassword(String password, StringBuilder errorMessages) {
    if (!password.matches(".*[A-Z].*")) {
      errorMessages.append(bundle.getString("oneUppercaseLetter")).append("\n");
    }
    if (!password.matches(".*\\d.*")) {
      errorMessages.append(bundle.getString("oneNumber")).append("\n");
    }
    if (password.length() < 8) {
      errorMessages.append(bundle.getString("atLeastEight")).append("\n");
    }
  }

  private void processRegistration(String username, String email, String password, Stage stage) {
    User user = userController.createUser(username, password, email);

    if (user != null) {
      SessionManager.getInstance().setCurrentUser(user);
      stage.setScene(new LoginPage(stage).createScene());
      LOGGER.log(Level.INFO, "Sign up successful: {0}", user.getUsername());
    } else {
      showErrorAlert(bundle.getString("errorContext"));
      LOGGER.log(Level.WARNING, "Sign up failed.");
    }
  }

  private void showErrorAlert(String errorMessage) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(bundle.getString("signUpErrorTitle"));
    alert.setHeaderText(null);
    alert.setContentText(errorMessage);
    alert.showAndWait();
  }
}
