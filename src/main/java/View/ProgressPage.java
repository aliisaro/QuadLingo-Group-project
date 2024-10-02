package View;

import DAO.ProgressDaoImpl;
import DAO.UserDaoImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;

import Controller.UserController;

public class ProgressPage extends BasePage implements setMarginButton, UpdateProgress {

    //These are placeholders, will be updated later according to the number of quizzes and flashcards
    private final int maxScore;
    private final int userScore;
    private final int completedQuizzes;
    private final int allQuizzes;


    public ProgressPage (Stage stage) {
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

        Label ProgressLabel1 = new Label("Progress Page");
        Label ProgressLabel2 = new Label("Your score progress:");
        Label ProgressLabel3 = new Label("Your quiz progress:");
        ProgressBar progressBar1 = new ProgressBar();
        ProgressBar progressBar2 = new ProgressBar();
        progressBar1.setStyle("-fx-accent: #FF8E72; -fx-control-inner-background: #9b9FB5;");
        progressBar1.setPrefWidth(200);
        progressBar1.setPrefHeight(20);
        progressBar2.setStyle("-fx-accent: #FF8E72; -fx-control-inner-background: #9b9FB5;");
        progressBar2.setPrefWidth(200);
        progressBar2.setPrefHeight(20);

        VBox progressBarBox1 = new VBox();
        progressBarBox1.setAlignment(Pos.CENTER);
        progressBarBox1.setPadding(new Insets(0));
        progressBarBox1.setSpacing(0);
        progressBarBox1.getChildren().add(progressBar1);

        VBox progressBarBox2 = new VBox();
        progressBarBox2.setAlignment(Pos.CENTER);
        progressBarBox2.setPadding(new Insets(0));
        progressBarBox2.setSpacing(0);
        progressBarBox2.getChildren().add(progressBar2);


        updateQuizProgress(progressBar1);
        updateScoreProgress(progressBar2);

        this.getChildren().add(ProgressLabel1);
        this.getChildren().add(profileButton);
        this.getChildren().add(buttonHome);
        this.getChildren().add(ProgressLabel2);
        this.getChildren().add(progressBarBox1);
        this.getChildren().add(ProgressLabel3);
        this.getChildren().add(progressBarBox2);

    }

        @Override
        public void setMargin (Button button,int top, int right, int bottom, int left){
            VBox.setMargin(button, new Insets(top, right, bottom, left));
        }

        @Override
        public void updateQuizProgress(ProgressBar progressBar){

            // Calculate the progress percentage
            double progressPercentage = (double) completedQuizzes / allQuizzes;

            // Update the progress bar
            progressBar.setProgress(progressPercentage);
        }

        @Override
        public void updateScoreProgress(ProgressBar progressBar) {
            // Update the number of completed items

            // Calculate the progress percentage
            double progressPercentage = (double) userScore / maxScore;

            // Update the progress bar
            progressBar.setProgress(progressPercentage);
        }


}
