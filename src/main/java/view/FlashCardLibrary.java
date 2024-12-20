package view;

import config.LanguageConfig;
import controller.FlashcardController;
import controller.UserController;
import dao.FCImplement;
import dao.ProgressDaoImpl;
import dao.UserDaoImpl;
import database.MariaDbConnection;
import main.SessionManager;
import model.Flashcard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.layout.VBox;

public class FlashCardLibrary extends BasePage implements UpdateProgress {
  // Class to display the Flashcard Library page

  // Initialize controllers and variables
  private final UserController userController = new UserController(new UserDaoImpl());
  private final int userId = userController.getCurrentUserId();
  private FlashcardController flashCardController;
  private final ProgressBar progressBar3 = ProgressPage.getProgressBar3();
  private final ProgressDaoImpl progressDao = new ProgressDaoImpl();
  private ResourceBundle bundle;
  private String languageCode;

  // Constructor to initialize the FlashCardLibrary page
  public FlashCardLibrary(Stage stage) {
    this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

    // Retrieve the current language code
    this.languageCode = LanguageConfig.getInstance().getCurrentLocale().getLanguage();

    // If user is not logged in, redirect to index page
    if (!SessionManager.getInstance().isLoggedIn()) {
      stage.setScene(new IndexPage(stage).createScene());
      return;
    }

    // Initialize FlashCardController with database connection
    Connection connection = MariaDbConnection.getConnection();
    flashCardController = new FlashcardController(new FCImplement(connection));

    // Set layout to the stage
    setLayout(stage);
  }

  private void setLayout(Stage stage) {
    // Page title
    Label pageTitle = new Label(bundle.getString("flashcardTitle")); // Flashcard Library
    pageTitle.setStyle("-fx-font-size: 24px; -fx-padding: 10px;");

    // Return to the homepage
    Button backButton = new Button(bundle.getString("homeButton")); // Homepage
    backButton.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));
    backButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
    backButton.setMaxWidth(Double.MAX_VALUE);

    // Logout button: clears session and redirects to IndexPage
    Button logoutButton = new Button(bundle.getString("logoutButton")); // Logout
    logoutButton.setOnAction(e -> {
      SessionManager.getInstance().logout();
      stage.setScene(new LoggedOutPage(stage).createScene());
    });
    logoutButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
    logoutButton.setMaxWidth(Double.MAX_VALUE);

    Button unmasterAllButton = new Button(bundle.getString("unmasterAllButton")); // Unmaster All
    unmasterAllButton.setOnAction(e -> {
      flashCardController.unmasterAllFlashcards(userId, languageCode);
      updateFlashcardProgress(progressBar3);
    });

    //Styling for special button
    unmasterAllButton.setStyle("-fx-background-color: #e86c6c; -fx-font-size: 14px; -fx-padding: 10px;");
    unmasterAllButton.setMaxWidth(Double.MAX_VALUE);
    unmasterAllButton.setOnMouseEntered(e -> unmasterAllButton.setStyle("-fx-background-color: #d9534f;"));
    unmasterAllButton.setOnMouseExited(e -> unmasterAllButton.setStyle("-fx-background-color: #e86c6c;"));

    // Create an HBox to hold the buttons
    HBox buttonBox = new HBox(10); // 10px spacing between buttons
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.getChildren().addAll(backButton, logoutButton, unmasterAllButton);

    HBox.setHgrow(backButton, javafx.scene.layout.Priority.ALWAYS);
    HBox.setHgrow(logoutButton, javafx.scene.layout.Priority.ALWAYS);
    HBox.setHgrow(unmasterAllButton, javafx.scene.layout.Priority.ALWAYS);

    // Fetch FlashCard topics
    List<Flashcard> topics = flashCardController.getTopics(LanguageConfig.getInstance().getCurrentLocale().getLanguage());

    // Create a VBox to hold the topics
    VBox topicBox = new VBox();
    topicBox.setPadding(new Insets(10));
    topicBox.setStyle("-fx-background-color: #D6E3F8; -fx-padding: 10px; -fx-spacing: 10px;");

    // Add each topic to the VBox
    for (Flashcard topic : topics) {
      Button topicButton = new Button(topic.getTopic());
      topicButton.setMaxWidth(350);
      topicButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
      topicButton.setMaxWidth(Double.MAX_VALUE);

      HBox.setHgrow(topicButton, javafx.scene.layout.Priority.ALWAYS);

      topicButton.setOnAction(e -> {
        FlashCardsPage flashCardsPage = new FlashCardsPage(flashCardController.getFlashcardDao(), topic.getTopic(), stage);
        stage.setScene(flashCardsPage.createScene());
      });
      topicBox.getChildren().add(topicButton);
    }

    // Add a button for mastered flashcards
    Button masteredFlashcardsButton = new Button(bundle.getString("masteredFlashcardsButton")); // Mastered Flashcards
    masteredFlashcardsButton.setMaxWidth(350);
    masteredFlashcardsButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
    masteredFlashcardsButton.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(masteredFlashcardsButton, javafx.scene.layout.Priority.ALWAYS);
    masteredFlashcardsButton.setOnAction(e -> {
      FlashCardsPage flashCardsPage = new FlashCardsPage(flashCardController.getFlashcardDao(), "Mastered Flashcards", stage);
      stage.setScene(flashCardsPage.createScene());
    });
    topicBox.getChildren().add(masteredFlashcardsButton);

    updateFlashcardProgress(progressBar3);

    // Help button with custom PNG image
    Image helpImage = new Image(getClass().getResourceAsStream("/helpButton3.png"));
    ImageView helpImageView = new ImageView(helpImage);

    // Set the desired size for the ImageView
    helpImageView.setFitWidth(50); // Set the desired width
    helpImageView.setFitHeight(50); // Set the desired height

    Button helpButton = new Button();
    // Transparent background
    helpButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
    helpButton.setGraphic(helpImageView);
    helpButton.setId("helpButton");
    helpButton.setOnAction(e -> {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(bundle.getString("help"));
      alert.setHeaderText(null);

      // Create a Text node to wrap the content text
      Text content = new Text(bundle.getString("helpFlashcardLibrary"));
      content.setWrappingWidth(400); // Set the desired wrapping width

      alert.getDialogPane().setContent(content);
      alert.showAndWait();
    });

    // Add all components to the layout
    this.getChildren().addAll(pageTitle, topicBox, buttonBox, helpButton);
  }

  @Override
  public void updateQuizProgress(ProgressBar progressBar) {
    // Not implemented
  }

  @Override
  public void updateScoreProgress(ProgressBar progressBar) {
    // Not implemented
  }

  @Override
  public void updateFlashcardProgress(ProgressBar progressBar) {
    int masteredFlashcards = progressDao.getMasteredFlashcards(userId, languageCode);
    int allFlashcards = progressDao.getFlashcardAmount(languageCode);
    double progress = (double) masteredFlashcards / allFlashcards;
    progressBar.setProgress(progress);
  }
}