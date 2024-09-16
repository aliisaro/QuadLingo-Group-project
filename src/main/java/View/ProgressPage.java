package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;

public class ProgressPage extends BasePage implements setMarginButton, UpdateProgress {

    //These are placeholders, will be updated later according to the number of quizzes and flashcards
    private int totalItems = 30;
    private int completedItems;

    public ProgressPage (Stage stage) {
        Button profileButton = new Button("Go to Profile");
        setMargin(profileButton, 10, 10, 10, 5);
        profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));

        Button buttonHome = new Button("Go Home");
        setMargin(buttonHome, 10, 10, 10, 5);
        buttonHome.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        Label ProgressLabel1 = new Label("Progress Page");
        Label ProgressLabel2 = new Label("Progress bar");
        ProgressBar progressBar = new ProgressBar();
        progressBar.setStyle("-fx-accent: #FF8E72; -fx-control-inner-background: #9b9FB5;");
        progressBar.setPrefWidth(200);
        progressBar.setPrefHeight(20);

        VBox progressBarBox = new VBox();
        progressBarBox.setAlignment(Pos.CENTER);
        progressBarBox.setPadding(new Insets(0));
        progressBarBox.setSpacing(0);
        progressBarBox.getChildren().add(progressBar);


        updateProgress(6, progressBar);


        this.getChildren().add(ProgressLabel1);
        this.getChildren().add(profileButton);
        this.getChildren().add(buttonHome);
        this.getChildren().add(ProgressLabel2);
        this.getChildren().add(progressBarBox);

    }

        @Override
        public void setMargin (Button button,int top, int right, int bottom, int left){
            VBox.setMargin(button, new Insets(top, right, bottom, left));
        }

        @Override
        public void updateProgress (int progress, ProgressBar progressBar){
            // Update the number of completed items
            completedItems += progress;

            // Calculate the progress percentage
            double progressPercentage = (double) completedItems / totalItems;

            // Update the progress bar
            progressBar.setProgress(progressPercentage);
        }


}
