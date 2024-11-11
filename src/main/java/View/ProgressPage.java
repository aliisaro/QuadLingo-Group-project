package View;

import Config.LanguageConfig;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import DAO.ProgressDaoImpl;
import DAO.UserDaoImpl;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;

import Controller.UserController;


public class ProgressPage extends BasePage implements setMarginButton, UpdateProgress {

    private final int maxScore;
    private final int userScore;
    private final int completedQuizzes;
    private final int allQuizzes;
    private final int masteredFlashcards;
    private final int allFlashcards;
    private final ProgressDaoImpl progressDao = new ProgressDaoImpl();
    private static final ProgressBar progressBar1 = new ProgressBar();
    private static final ProgressBar progressBar2 = new ProgressBar();
    private static final ProgressBar progressBar3 = new ProgressBar();
    private ResourceBundle bundle;
    private String languageCode;


    //Displays the progress page
    public ProgressPage(Stage stage) {
        UserDaoImpl userDao = new UserDaoImpl();
        UserController userController = UserController.getInstance(userDao);
        ProgressDaoImpl progressDao = new ProgressDaoImpl();
        int userID = userController.getCurrentUserId();
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

        // Retrieve the current language code
        this.languageCode = LanguageConfig.getInstance().getCurrentLocale().getLanguage();

        // Get the user's score, max score, completed quizzes and all quizzes
        userScore = progressDao.getUserScore(userID, languageCode);
        maxScore = progressDao.getMaxScore(languageCode);
        completedQuizzes = progressDao.getAllCompletedQuizzes(userID, languageCode);
        allQuizzes = progressDao.getQuizAmount(languageCode);
        masteredFlashcards = progressDao.getMasteredFlashcards(userID);
        allFlashcards = progressDao.getFlashcardAmount();

        //Button to go to the profile page
        Button profileButton = new Button(bundle.getString("profileButton"));
        setMargin(profileButton, 10, 10, 10, 5);
        profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));

        //Button to go back to the homepage
        Button buttonHome = new Button(bundle.getString("homeButton"));
        setMargin(buttonHome, 10, 10, 10, 5);
        buttonHome.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        Label progressLabel1 = new Label(bundle.getString("progressTitle"));
        progressLabel1.setStyle("-fx-font-size: 24px; -fx-padding: 10px;");
        Label progressLabel2 = new Label(bundle.getString("userScore")); // Your score
        Label progressLabel3 = new Label(bundle.getString("quizProgress")); // Your quiz progress
        Label progressLabel5 = new Label(bundle.getString("flashcardProgress")); // Your flashcard progress
        Label progressLabel4 = new Label(MessageFormat.format(bundle.getString("quizzesCompleted"), completedQuizzes, allQuizzes)); // You have completed {completedQuizzes} out of {allQuizzes} quizzes
        Label progressLabel6 = new Label(MessageFormat.format(bundle.getString("flashcardsMastered"), masteredFlashcards, allFlashcards)); // You have mastered {masteredFlashcards} out of {allFlashcards} flashcards
        //Styling the progress bars
        progressBar1.setStyle("-fx-accent: #FF8E72; -fx-control-inner-background: #9b9FB5;");
        progressBar1.setPrefWidth(200);
        progressBar1.setPrefHeight(20);
        progressBar2.setStyle("-fx-accent: #FF8E72; -fx-control-inner-background: #9b9FB5;");
        progressBar2.setPrefWidth(200);
        progressBar2.setPrefHeight(20);
        progressBar3.setStyle("-fx-accent: #FF8E72; -fx-control-inner-background: #9b9FB5;");
        progressBar3.setPrefWidth(200);
        progressBar3.setPrefHeight(20);

        progressBar1.getStyleClass().add("progress-bar");
        progressBar2.getStyleClass().add("progress-bar");
        progressBar3.getStyleClass().add("progress-bar");

        //Outer box for the progress bar
        VBox progressBarBox1 = new VBox();
        progressBarBox1.getStyleClass().add("progressBarBox");
        setMarginVbox(progressBarBox1, 5, 10, 5, 10);

        //Inner box for the progress bar
        VBox innerBox1 = new VBox();
        innerBox1.setPadding(new Insets(0));
        innerBox1.getChildren().addAll(progressLabel2, progressBar1);

        progressBarBox1.getChildren().add(innerBox1);

        //Outer box for the progress bar
        VBox progressBarBox2 = new VBox();
        progressBarBox2.getStyleClass().add("progressBarBox");
        setMarginVbox(progressBarBox2, 5, 10, 5, 10);

        //Inner box for the progress bar
        VBox innerBox2 = new VBox();
        innerBox2.setPadding(new Insets(0));
        innerBox2.getChildren().addAll(progressLabel3, progressBar2);

        progressBarBox2.getChildren().add(innerBox2);

        //Outer box for the progress bar
        VBox progressBarBox3 = new VBox();
        progressBarBox3.getStyleClass().add("progressBarBox");
        setMarginVbox(progressBarBox3, 5, 10, 5, 10);

        //Inner box for the progress bar
        VBox innerBox3 = new VBox();
        innerBox3.setPadding(new Insets(0));
        innerBox3.getChildren().addAll(progressLabel4, progressBar3);

        progressBarBox3.getChildren().add(innerBox3);

        //Update the progress bars
        updateQuizProgress(progressBar1);
        updateScoreProgress(progressBar2);
        updateFlashcardProgress(progressBar3);


        VBox mainBox = new VBox();

        //Adding all the elements to the main box
        mainBox.getChildren().addAll(
                progressLabel1,
                profileButton,
                buttonHome,
                progressBarBox1,
                progressLabel4,
                progressBarBox2,
                progressLabel5,
                progressBarBox3,
                progressLabel6

        );

        this.getChildren().add(mainBox);
    }

    //Getters for the progress bars
    public static ProgressBar getProgressBar1() {
        return progressBar1;
    }
    public static ProgressBar getProgressBar2() {
        return progressBar2;
    }
    public static ProgressBar getProgressBar3() {
        return progressBar3;
    }

    @Override
    //Set the margin of a button
    public void setMargin(Button button, int top, int right, int bottom, int left) {
        VBox.setMargin(button, new Insets(top, right, bottom, left));
    }

    @Override
    //Update the quiz progress
    public void updateQuizProgress(ProgressBar progressBar) {
        double progressPercentage = (double) completedQuizzes / allQuizzes;
        progressBar.setProgress(progressPercentage);
    }

    @Override
    //Update the score progress
    public void updateScoreProgress(ProgressBar progressBar) {
        double progressPercentage = (double) userScore / maxScore;
        progressBar.setProgress(progressPercentage);
    }

    @Override
    //Update the flashcard progress
    public void updateFlashcardProgress(ProgressBar progressBar) {
        double progressPercentage = (double) masteredFlashcards / allFlashcards;
        progressBar.setProgress(progressPercentage);
    }

    //Set the margin of a VBox
    public void setMarginVbox(VBox vBox, int top, int right, int bottom, int left) {
        VBox.setMargin(vBox, new Insets(top, right, bottom, left));
    }


}