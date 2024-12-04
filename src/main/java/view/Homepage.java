package view;

import Config.LanguageConfig;
import Main.SessionManager;
import Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.util.Objects;
import java.util.ResourceBundle;

import Database.MariaDbConnection;

public class Homepage extends BasePage {

  private static final String DEFAULT_BUTTON_STYLE = "-fx-font-size: 16px; -fx-padding: 10px;";
  private static final String NORMAL_LOGOUT_STYLE = "-fx-background-color: #e86c6c; " + DEFAULT_BUTTON_STYLE;
  private static final String HOVERED_LOGOUT_STYLE = "-fx-background-color: #d9534f; " + DEFAULT_BUTTON_STYLE;

  private ResourceBundle bundle;

  public Homepage(Stage stage) {
    User currentUser = SessionManager.getInstance().getCurrentUser();

    if (!SessionManager.getInstance().isLoggedIn()) {
      stage.setScene(new IndexPage(stage).createScene());
      return;
    }

    this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

    Connection connection = MariaDbConnection.getConnection();

    setLayout(stage, currentUser);

    stage.setOnCloseRequest(event -> MariaDbConnection.terminate(connection));
  }

  private void setLayout(Stage stage, User currentUser) {
    this.setPadding(new Insets(10));
    this.setAlignment(Pos.CENTER);

    Label welcomeLabel = new Label(bundle.getString("welcomeLabel") + currentUser.getUsername());
    welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

    Button quizLibraryButton = new Button(bundle.getString("quizLibraryButton"));
    quizLibraryButton.setStyle(DEFAULT_BUTTON_STYLE);
    quizLibraryButton.setMaxWidth(Double.MAX_VALUE);
    quizLibraryButton.setOnAction(e -> stage.setScene(new QuizLibrary(stage).createScene()));

    Button flashcardLibraryButton = new Button(bundle.getString("flashcardLibraryButton"));
    flashcardLibraryButton.setStyle(DEFAULT_BUTTON_STYLE);
    flashcardLibraryButton.setMaxWidth(Double.MAX_VALUE);
    flashcardLibraryButton.setOnAction(e -> stage.setScene(new FlashCardLibrary(stage).createScene()));

    Button achieButton = new Button(bundle.getString("achievementsButton"));
    achieButton.setStyle(DEFAULT_BUTTON_STYLE);
    achieButton.setMaxWidth(Double.MAX_VALUE);
    achieButton.setOnAction(e -> stage.setScene(new AchiePage(stage).createScene()));

    Button profileButton = new Button(bundle.getString("profileButton"));
    profileButton.setStyle(DEFAULT_BUTTON_STYLE);
    profileButton.setMaxWidth(Double.MAX_VALUE);
    profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));

    Button logoutButton = new Button(bundle.getString("logoutButton"));
    logoutButton.setStyle(NORMAL_LOGOUT_STYLE);
    logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(HOVERED_LOGOUT_STYLE));
    logoutButton.setOnMouseExited(e -> logoutButton.setStyle(NORMAL_LOGOUT_STYLE));
    logoutButton.setMaxWidth(Double.MAX_VALUE);
    logoutButton.setOnAction(e -> {
      SessionManager.getInstance().logout();
      stage.setScene(new LoggedOutPage(stage).createScene());
    });

    VBox buttonContainer = new VBox(10);
    buttonContainer.getChildren().addAll(quizLibraryButton, flashcardLibraryButton, achieButton, profileButton, logoutButton);
    buttonContainer.setAlignment(Pos.CENTER);
    VBox.setVgrow(buttonContainer, Priority.ALWAYS);

    Image helpImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/helpButton3.png")));
    ImageView helpImageView = new ImageView(helpImage);
    helpImageView.setFitWidth(50);
    helpImageView.setFitHeight(50);

    Button helpButton = new Button();
    helpButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
    helpButton.setGraphic(helpImageView);
    helpButton.setId("helpButton");
    helpButton.setOnAction(e -> {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle(bundle.getString("help"));
      alert.setHeaderText(null);

      Text content = new Text(bundle.getString("helpHomepage"));
      content.setWrappingWidth(400);

      alert.getDialogPane().setContent(content);
      alert.showAndWait();
    });

    this.getChildren().addAll(welcomeLabel, buttonContainer, helpButton);
  }
}
