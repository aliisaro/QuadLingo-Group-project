package View;

import DAO.ProgressDaoImpl;
import DAO.UserDaoImpl;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;

import Controller.UserController;

public class ProgressPage extends BasePage implements setMarginButton, UpdateProgress, setMarginVBox {

    private final int maxScore;
    private final int userScore;
    private final int completedQuizzes;
    private final int allQuizzes;

    public ProgressPage(Stage stage) {
        UserDaoImpl userDao = new UserDaoImpl();
        UserController userController = UserController.getInstance(userDao);
        ProgressDaoImpl progressDao = new ProgressDaoImpl();
        int userID = userController.getCurrentUserId();

        userScore = progressDao.getUserScore(userID);
        maxScore = progressDao.getMaxScore(userID);
        completedQuizzes = progressDao.getAllCompletedQuizzes(userID);
        allQuizzes = progressDao.getQuizAmount();

        Button profileButton = new Button("Go to Profile");
        setMargin(profileButton, 10, 10, 10, 5);
        profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));

        Button buttonHome = new Button("Go Home");
        setMargin(buttonHome, 10, 10, 10, 5);
        buttonHome.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        Label progressLabel1 = new Label("Progress Page");
        Label progressLabel2 = new Label("Your score progress:");
        Label progressLabel3 = new Label("Your quiz progress:");
        Label progressLabel4 = new Label("You have done " + completedQuizzes + " out of " + allQuizzes + " quizzes.");

        ProgressBar progressBar1 = new ProgressBar();
        progressBar1.setStyle("-fx-accent: #FF8E72; -fx-control-inner-background: #9b9FB5;");
        progressBar1.setPrefWidth(200);
        progressBar1.setPrefHeight(20);

        ProgressBar progressBar2 = new ProgressBar();
        progressBar2.setStyle("-fx-accent: #FF8E72; -fx-control-inner-background: #9b9FB5;");
        progressBar2.setPrefWidth(200);
        progressBar2.setPrefHeight(20);

        progressBar1.getStyleClass().add("progress-bar");
        progressBar2.getStyleClass().add("progress-bar");

        VBox progressBarBox1 = new VBox();
        progressBarBox1.getStyleClass().add("progressBarBox");
        setMarginVbox(progressBarBox1, 5, 10, 5, 10);

        VBox innerBox1 = new VBox();
        innerBox1.setPadding(new Insets(0));
        innerBox1.getChildren().addAll(progressLabel2, progressBar1);

        progressBarBox1.getChildren().add(innerBox1);

        VBox progressBarBox2 = new VBox();
        progressBarBox2.getStyleClass().add("progressBarBox");
        setMarginVbox(progressBarBox2, 5, 10, 5, 10);

        VBox innerBox2 = new VBox();
        innerBox2.setPadding(new Insets(0));
        innerBox2.getChildren().addAll(progressLabel3, progressBar2);

        progressBarBox2.getChildren().add(innerBox2);

        updateQuizProgress(progressBar1);
        updateScoreProgress(progressBar2);

        VBox mainBox = new VBox();
        mainBox.getChildren().addAll(
                progressLabel1,
                profileButton,
                buttonHome,
                progressBarBox1,
                progressLabel4,
                progressBarBox2
        );

        this.getChildren().add(mainBox);
    }

    @Override
    public void setMargin(Button button, int top, int right, int bottom, int left) {
        VBox.setMargin(button, new Insets(top, right, bottom, left));
    }

    @Override
    public void updateQuizProgress(ProgressBar progressBar) {
        double progressPercentage = (double) completedQuizzes / allQuizzes;
        progressBar.setProgress(progressPercentage);
    }

    @Override
    public void updateScoreProgress(ProgressBar progressBar) {
        double progressPercentage = (double) userScore / maxScore;
        progressBar.setProgress(progressPercentage);
    }

    @Override
    public void setMarginVbox(VBox vBox, int top, int right, int bottom, int left) {
        VBox.setMargin(vBox, new Insets(top, right, bottom, left));
    }
}