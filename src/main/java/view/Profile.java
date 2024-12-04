package view;

import config.LanguageConfig;
import controller.UserController;
import dao.UserDaoImpl;
import main.SessionManager;
import model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Profile extends BasePage {
  private static final Logger logger = Logger.getLogger(Profile.class.getName());
  private static final String BUTTON_STYLE = "-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 165";
  private static final String NORMAL_BUTTON_STYLE = "-fx-background-color: #e86c6c; " + BUTTON_STYLE;
  private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #d9534f; " + BUTTON_STYLE;
  private static final String ERROR_ALERT_TITLE = "errorAlertTitle";

  private ResourceBundle bundle;

  // UI Components (to allow dynamic text updates)
  private Label pageTitle;
  private Label usernameLabel;
  private Label emailLabel;
  private Label passwordLabel;
  private Label changeUsernameLabel;
  private Label changeEmailLabel;
  private Label changePasswordLabel;
  private Label changeLanguageLabel;
  private Button saveButton;
  private Button buttonProgress;
  private Button backButton;
  private Button logoutButton;

  public Profile(Stage stage) {
    User currentUser = SessionManager.getInstance().getCurrentUser();

    // Redirect to the IndexPage if the user is not logged in
    if (!SessionManager.getInstance().isLoggedIn()) {
      stage.setScene(new IndexPage(stage).createScene());
      return;
    }

    this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());
    setLayout(stage, currentUser);
  }

  private void setLayout(Stage stage, User currentUser) {
    this.setPadding(new Insets(10));

    // Page title
    pageTitle = new Label(bundle.getString("profilePageTitle"));
    pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
    HBox titleContainer = new HBox(pageTitle);
    titleContainer.setAlignment(Pos.CENTER);

    // User details
    usernameLabel = new Label(bundle.getString("currentUsernameLabel") + currentUser.getUsername());
    emailLabel = new Label(bundle.getString("currentEmailLabel") + currentUser.getEmail());
    passwordLabel = new Label(bundle.getString("currentPasswordLabel"));

    // Change fields
    changeUsernameLabel = new Label(bundle.getString("changeUsernameLabel"));
    TextField usernameTextField = new TextField();

    changeEmailLabel = new Label(bundle.getString("changeEmailLabel"));
    TextField emailTextField = new TextField();

    changePasswordLabel = new Label(bundle.getString("changePasswordLabel"));
    PasswordField passwordTextField = new PasswordField();

    // Save changes button
    saveButton = new Button(bundle.getString("saveChangesButton"));
    saveButton.setStyle(BUTTON_STYLE);
    saveButton.setOnAction(e -> handleSaveAction(usernameTextField, emailTextField, passwordTextField, currentUser));

    // Progress page button
    buttonProgress = new Button(bundle.getString("progressPageButton"));
    buttonProgress.setStyle(BUTTON_STYLE);
    buttonProgress.setOnAction(e -> stage.setScene(new ProgressPage(stage).createScene()));

    // Back button
    backButton = new Button(bundle.getString("backToHomeButton"));
    backButton.setStyle(BUTTON_STYLE);
    backButton.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

    // Logout button
    logoutButton = new Button(bundle.getString("logoutButton"));
    logoutButton.setStyle(NORMAL_BUTTON_STYLE);
    logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(HOVERED_BUTTON_STYLE));
    logoutButton.setOnMouseExited(e -> logoutButton.setStyle(NORMAL_BUTTON_STYLE));
    logoutButton.setOnAction(e -> {
      SessionManager.getInstance().logout();
      stage.setScene(new LoggedOutPage(stage).createScene());
    });

    // Language selection ComboBox
    changeLanguageLabel = new Label(bundle.getString("changeLanguageLabel") + "\n");
    ComboBox<String> languageComboBox = new ComboBox<>();
    languageComboBox.getItems().addAll("English", "French", "Chinese", "Arabic");
    languageComboBox.setValue(bundle.getString("language.key"));
    languageComboBox.setOnAction(e -> switchLanguage(languageComboBox.getValue()));

    HBox buttonContainer1 = new HBox(10, saveButton, buttonProgress);
    buttonContainer1.setPadding(new Insets(20, 0, 5, 0));

    HBox buttonContainer2 = new HBox(10, backButton, logoutButton);
    buttonContainer2.setPadding(new Insets(5, 0, 5, 0));

    HBox languageContainer = new HBox(10, changeLanguageLabel, languageComboBox);

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
            languageContainer
    );
  }

  private void handleSaveAction(TextField usernameTextField, TextField emailTextField, PasswordField passwordTextField, User currentUser) {
    UserController userController = UserController.getInstance(UserDaoImpl.getInstance());

    String username = usernameTextField.getText().trim();
    String email = emailTextField.getText().trim();
    String password = passwordTextField.getText().trim();

    if (areFieldsEmpty(username, email, password)) {
      showAlert(bundle.getString(ERROR_ALERT_TITLE), bundle.getString("emptyFieldsAlert"));
      return;
    }

    StringBuilder errorMessages = new StringBuilder();

    validateAndSetUsername(username, currentUser);
    validateAndSetEmail(email, currentUser, errorMessages, userController);
    validateAndSetPassword(password, currentUser, errorMessages);

    if (errorMessages.length() > 0) {
      showAlert(bundle.getString(ERROR_ALERT_TITLE), errorMessages.toString());
    } else {
      processProfileUpdate(usernameTextField, emailTextField, passwordTextField, currentUser, userController);
    }
  }

  private boolean areFieldsEmpty(String username, String email, String password) {
    return username.isEmpty() && email.isEmpty() && password.isEmpty();
  }

  private void validateAndSetUsername(String username, User currentUser) {
    if (!username.isEmpty() && !username.equals(currentUser.getUsername())) {
      currentUser.setUsername(username);
    }
  }

  private void validateAndSetEmail(String email, User currentUser, StringBuilder errorMessages, UserController userController) {
    if (!email.isEmpty() && !email.equals(currentUser.getEmail())) {
      if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
        errorMessages.append(bundle.getString("invalidEmail")).append("\n");
      } else if (userController.doesEmailExist(email)) {
        errorMessages.append(bundle.getString("accountExists")).append("\n");
      } else {
        currentUser.setEmail(email);
      }
    }
  }

  private void validateAndSetPassword(String password, User currentUser, StringBuilder errorMessages) {
    if (!password.isEmpty()) {
      if (!password.matches(".*[A-Z].*")) {
        errorMessages.append(bundle.getString("oneUppercaseLetter")).append("\n");
      }
      if (!password.matches(".*\\d.*")) {
        errorMessages.append(bundle.getString("oneNumber")).append("\n");
      }
      if (password.length() < 8) {
        errorMessages.append(bundle.getString("atLeastEight")).append("\n");
      } else {
        currentUser.setPassword(password, true);
      }
    }
  }

  private void processProfileUpdate(TextField usernameTextField, TextField emailTextField, PasswordField passwordTextField, User currentUser, UserController userController) {
    if (userController.updateUser(currentUser)) {
      showAlert(bundle.getString("successAlertTitle"), bundle.getString("profileUpdateSuccessAlert"));
      logger.info(() -> String.format("Profile updated successfully: %s", currentUser));
      usernameTextField.clear();
      emailTextField.clear();
      passwordTextField.clear();
      refreshProfilePage();
    } else {
      showAlert(bundle.getString(ERROR_ALERT_TITLE), bundle.getString("profileUpdateFailedAlert"));
      logger.severe("Failed to update profile.");
    }
  }


  private void switchLanguage(String language) {
    Locale locale = switch (language) {
      case "French" -> new Locale("fr");
      case "Chinese" -> new Locale("ch");
      case "Arabic" -> new Locale("ar");
      default -> Locale.ENGLISH;
    };

    LanguageConfig.getInstance().setCurrentLocale(locale);
    bundle = ResourceBundle.getBundle("bundle", locale);
    updateTexts(); // Update the UI texts dynamically
  }

  private void updateTexts() {
    User currentUser = SessionManager.getInstance().getCurrentUser();

    pageTitle.setText(bundle.getString("profilePageTitle"));
    usernameLabel.setText(bundle.getString("currentUsernameLabel") + currentUser.getUsername());
    emailLabel.setText(bundle.getString("currentEmailLabel") + currentUser.getEmail());
    passwordLabel.setText(bundle.getString("currentPasswordLabel"));
    changeUsernameLabel.setText(bundle.getString("changeUsernameLabel"));
    changeEmailLabel.setText(bundle.getString("changeEmailLabel"));
    changePasswordLabel.setText(bundle.getString("changePasswordLabel"));
    saveButton.setText(bundle.getString("saveChangesButton"));
    backButton.setText(bundle.getString("backToHomeButton"));
    buttonProgress.setText(bundle.getString("progressPageButton"));
    logoutButton.setText(bundle.getString("logoutButton"));
    changeLanguageLabel.setText(bundle.getString("changeLanguageLabel"));
  }

  private void refreshProfilePage() {
    Stage currentStage = (Stage) this.getScene().getWindow();
    currentStage.setScene(new Profile(currentStage).createScene());
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
