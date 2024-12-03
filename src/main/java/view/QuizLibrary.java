package view;

import Config.LanguageConfig;
import Controller.UserController;
import Controller.QuizController;
import Dao.ProgressDaoImpl;
import Dao.UserDaoImpl;
import Dao.QuizDaoImpl;
import Database.MariaDbConnection;
import Main.SessionManager;
import Model.Quiz;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;

import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuizLibrary extends BasePage implements UpdateProgress {
    private static final Logger LOGGER = Logger.getLogger(QuizLibrary.class.getName());

    private final UserController userController = new UserController(new UserDaoImpl());
    private QuizController quizController;
    private final ProgressBar progressBar1 = ProgressPage.getProgressBar1();
    private final ProgressBar progressBar2 = ProgressPage.getProgressBar2();
    private final ProgressDaoImpl progressDao = new ProgressDaoImpl();
    private final int userID = userController.getCurrentUserId();
    private ResourceBundle bundle;
    private String languageCode;

    public QuizLibrary(Stage stage) {
        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        // Initialize QuizController with database connection
        Connection connection = MariaDbConnection.getConnection();
        quizController = new QuizController(new QuizDaoImpl(connection));

        // Use the global locale from Config
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

        // Retrieve the current language code
        this.languageCode = LanguageConfig.getInstance().getCurrentLocale().getLanguage();

        // Set layout to the stage
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);

        Label pageTitle = new Label(bundle.getString("quizLibraryButton"));
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button backButton = new Button(bundle.getString("backToHomeButton"));
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        backButton.setMaxWidth(Double.MAX_VALUE);
        backButton.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        String normalButtonStyle = "-fx-background-color: #e86c6c; -fx-font-size: 14px; -fx-padding: 10px;";
        String hoveredButtonStyle = "-fx-background-color: #d9534f; -fx-font-size: 14px; -fx-padding: 10px;";

        Button logoutButton = new Button(bundle.getString("logoutButton"));
        logoutButton.setStyle(normalButtonStyle);
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(hoveredButtonStyle));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(normalButtonStyle));
        logoutButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new LoggedOutPage(stage).createScene());
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backButton, logoutButton);

        LOGGER.log(Level.INFO, "Language Code: {0}", languageCode);
        List<Quiz> quizzes = quizController.getAllQuizzes(languageCode);
        LOGGER.log(Level.INFO, "Number of quizzes retrieved: {0}", quizzes.size());

        VBox quizzesBox = new VBox(10);
        quizzesBox.setPadding(new Insets(10));
        quizzesBox.setStyle("-fx-padding: 10px; -fx-spacing: 10px;");
        quizzesBox.setAlignment(Pos.CENTER);

        for (Quiz quiz : quizzes) {
            Button quizButton = new Button(quiz.getQuizTitle());
            quizButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");
            quizButton.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(quizButton, Priority.ALWAYS);
            quizButton.setOnAction(e -> {
                QuizPage quizPage = new QuizPage(quizController.getQuizDao(), quiz.getQuizId(), stage);
                stage.setScene(quizPage.createScene());
            });
            quizzesBox.getChildren().add(quizButton);
        }

        HBox.setHgrow(backButton, Priority.ALWAYS);
        HBox.setHgrow(logoutButton, Priority.ALWAYS);

        updateQuizProgress(progressBar1);
        updateScoreProgress(progressBar2);

        Image helpImage = new Image(getClass().getResourceAsStream("/helpButton3.png"));
        ImageView helpImageView = new ImageView(helpImage);
        helpImageView.setFitWidth(50);
        helpImageView.setFitHeight(50);

        Button helpButton = new Button();
        helpButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        helpButton.setGraphic(helpImageView);
        helpButton.setId("helpButton");
        helpButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("help"));
            alert.setHeaderText(null);

            Text content = new Text(bundle.getString("helpQuizLibrary"));
            content.setWrappingWidth(400);
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        });

        this.getChildren().addAll(pageTitle, quizzesBox, buttonBox, helpButton);
    }

    @Override
    public void updateQuizProgress(ProgressBar progressBar) {
        int completedQuizzes = userController.getQuizzesCompleted(userID, languageCode);
        int allQuizzes = quizController.getAllQuizzes(languageCode).size();
        double progress = (double) completedQuizzes / allQuizzes;
        progressBar.setProgress(progress);
    }

    @Override
    public void updateScoreProgress(ProgressBar progressBar) {
        int userScore = progressDao.getUserScore(userID, languageCode);
        int maxScore = progressDao.getMaxScore(languageCode);
        double progress = (double) userScore / maxScore;
        progressBar.setProgress(progress);
    }

    @Override
    public void updateFlashcardProgress(ProgressBar progressBar) {
        // Not implemented
    }
}
