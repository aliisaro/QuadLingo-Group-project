package view;

import Config.LanguageConfig;
import Dao.QuizDao;
import Main.SessionManager;
import Model.Question;
import Model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import java.util.List;
import java.util.ResourceBundle;

public class QuizPage extends BasePage {
    private final QuizDao quizDao;
    private final List<Question> questions;
    private final int quizId;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private Label questionLabel;
    private ToggleGroup answerGroup;
    private RadioButton[] answerButtons;
    private Label errorLabel;
    private Label feedbackLabel;
    private ResourceBundle bundle;

    public QuizPage(QuizDao quizDao, int quizId, Stage stage) {
        this.quizDao = quizDao;
        this.questions = quizDao.getQuestionsForQuiz(quizId); // Load questions based on quizId
        this.quizId = quizId;

        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

        setLayout(stage); // Initialize UI components
        loadQuestion(); // Load the first question
    }

    private void setLayout(Stage stage) {
        this.setPadding(new Insets(20));

        questionLabel = new Label();
        questionLabel.setWrapText(true);
        questionLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold;");

        answerGroup = new ToggleGroup();
        answerButtons = new RadioButton[4];
        VBox answersBox = new VBox(10);
        answersBox.setPadding(new Insets(10, 0, 10, 0));

        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new RadioButton();
            answerButtons[i].setToggleGroup(answerGroup);
            answerButtons[i].setWrapText(true);
            answerButtons[i].setStyle("-fx-font-size: 16px;");
            answersBox.getChildren().add(answerButtons[i]);
        }

        feedbackLabel = new Label();
        feedbackLabel.setVisible(false);

        Button submitButton = new Button(bundle.getString("submitAnswer"));
        submitButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 175;");
        submitButton.setOnAction(e -> handleSubmitAnswer());

        Button cancelButton = new Button(bundle.getString("cancelQuiz"));
        cancelButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 150;");
        cancelButton.setOnAction(e -> stage.setScene(new QuizLibrary(stage).createScene()));

        String normalButtonStyle = "-fx-background-color: #e86c6c; -fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 125";
        String hoveredButtonStyle = "-fx-background-color: #d9534f; -fx-font-size: 14px; -fx-padding: 10px; -fx-pref-width: 125";

        Button logoutButton = new Button(bundle.getString("logoutButton"));
        logoutButton.setStyle(normalButtonStyle);
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(hoveredButtonStyle));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle(normalButtonStyle));
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new LoggedOutPage(stage).createScene());
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));
        buttonBox.setStyle("-fx-alignment: center;");
        buttonBox.getChildren().addAll(submitButton, cancelButton, logoutButton);

        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        this.getChildren().addAll(questionLabel, answersBox, feedbackLabel, errorLabel, buttonBox);
    }

    private void loadQuestion() {
        answerGroup.selectToggle(null);
        errorLabel.setText("");

        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionLabel.setText((currentQuestionIndex + 1) + ". " + question.getQuestionText());

            List<String> answerOptions = question.getAnswerOptions();
            for (int i = 0; i < answerButtons.length; i++) {
                if (i < answerOptions.size()) {
                    answerButtons[i].setText(answerOptions.get(i));
                    answerButtons[i].setVisible(true);
                } else answerButtons[i].setVisible(false);
            }
        } else showFinalScore();
    }

    private void handleSubmitAnswer() {
        RadioButton selectedButton = (RadioButton) answerGroup.getSelectedToggle();
        if (selectedButton != null) {
            String selectedAnswer = selectedButton.getText();
            Question currentQuestion = questions.get(currentQuestionIndex);

            if (quizDao.checkAnswer(currentQuestion.getQuestionId(), selectedAnswer)) {
                score++;
                feedbackLabel.setText(bundle.getString("correct"));
                feedbackLabel.setTextFill(Color.GREEN);
            } else {
                feedbackLabel.setText(bundle.getString("incorrect") + currentQuestion.getCorrectAnswer());
                feedbackLabel.setTextFill(Color.RED);
            }

            feedbackLabel.setVisible(true);

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                currentQuestionIndex++;
                loadQuestion();
            });
            pause.play();
        } else errorLabel.setText(bundle.getString("selectAnswerError"));
    }

    private void showFinalScore() {
        this.getChildren().clear();
        this.setPadding(new Insets(20));

        Label scoreLabel = new Label(bundle.getString("quizFinished"));
        scoreLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label resultLabel = new Label(bundle.getString("yourScore") + score + bundle.getString("outOf") + questions.size());
        resultLabel.setStyle("-fx-font-size: 18px;");

        User currentUser = SessionManager.getInstance().getCurrentUser();
        int userId = currentUser.getUserId();

        quizDao.recordQuizCompletion(userId, quizId, score);

        this.getChildren().addAll(scoreLabel, resultLabel);

        Button backButton = new Button(bundle.getString("quizLibraryButton"));
        backButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;-fx-pref-width: 180;");
        backButton.setOnAction(e -> {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(new QuizLibrary(stage).createScene());
        });

        this.getChildren().add(backButton);
    }
}
