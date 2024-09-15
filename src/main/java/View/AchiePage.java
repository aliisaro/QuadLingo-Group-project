package View;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AchiePage extends BasePage implements ImageSize, setMarginButton, badgeLock {


    public AchiePage(Stage stage) {
        Image image1 = new Image("file:src/main/resources/BadgePaceHolder.png");
        Image image2 = new Image("file:src/main/resources/BadgePaceHolder.png");
        ImageView imageView1 = new ImageView(image1);
        ImageView imageView2 = new ImageView(image2);

        //Adjust the size of the image
        imageView1.setPreserveRatio(true);
        setImageSize(imageView1, 100, 100);
        VBox.setMargin(imageView1, new Insets(10, 10, 10, 5));
        imageView2.setPreserveRatio(true);
        VBox.setMargin(imageView2, new Insets(10, 10, 10, 5));
        setImageSize(imageView2, 100, 100);
        lockBadge(true, imageView2);

        Button button1 = new Button("Go to Page 2");
        setMargin(button1, 10, 10, 10, 5);
        button1.setOnAction(e -> stage.setScene(new ExamplePage2(stage).createScene()));

        Button profileButton = new Button("Go to Profile");
        setMargin(profileButton, 10, 10, 10, 5);
        profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));

        Button buttonHome = new Button("Go Home");
        setMargin(buttonHome, 10, 10, 10, 5);
        buttonHome.setOnAction(e -> stage.setScene(new ExamplePage1(stage).createScene()));

        Label AchieLabel1 = new Label("Achievements Page");
        Label AchieLabel2 = new Label("Earned badges");
        Label AchieLabel3 = new Label("Locked badges");

        this.getChildren().add(AchieLabel1);
        this.getChildren().add(button1);
        this.getChildren().add(profileButton);
        this.getChildren().add(buttonHome);
        this.getChildren().add(AchieLabel2);
        this.getChildren().add(imageView1);
        this.getChildren().add(AchieLabel3);
        this.getChildren().add(imageView2);
    }

    @Override
    public void setImageSize(ImageView imageView, int width, int height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    @Override
    public void setMargin(Button button, int top, int right, int bottom, int left) {
        VBox.setMargin(button, new Insets(top, right, bottom, left));
    }

    @Override
    public void unlockBadge(boolean value, ImageView imageView) {
        if (value) {
            ColorAdjust desaturate = new ColorAdjust();
            desaturate.setSaturation(0);
            imageView.setEffect(desaturate);
        }
}
    @Override
    public void lockBadge(boolean value, ImageView imageView) {
        if (value) {
            ColorAdjust desaturate = new ColorAdjust();
            desaturate.setSaturation(-0.5);
            imageView.setEffect(desaturate);
        }
    }
}