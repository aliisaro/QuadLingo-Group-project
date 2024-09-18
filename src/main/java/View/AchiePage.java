package View;

import DAO.UserDaoImpl;
import Model.Badge;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Controller.UserController;

import java.util.ArrayList;
import java.util.List;

public class AchiePage extends BasePage implements ImageSize, setMarginButton, badgeLock {
    private UserController userController;
    private List<Badge> badges;
    private VBox unlockedBadgesContainer;
    private VBox lockedBadgesContainer;

    public AchiePage(Stage stage) {
        this.userController = UserController.getInstance(UserDaoImpl.getInstance());
        this.badges = new ArrayList<>();
        badges.add(new Badge("file:src/main/resources/FirstFlashBadge.png", 1));
        badges.add(new Badge("file:src/main/resources/SecondFlashBadge.png", 5));

        String userEmail = userController.getEmailDao();

        Label AchieLabel1 = new Label("Achievements Page");
        Button profileButton = new Button("Go to Profile");
        setMargin(profileButton, 10, 10, 10, 5);
        profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));

        Button buttonHome = new Button("Go Home");
        setMargin(buttonHome, 10, 10, 10, 5);
        buttonHome.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        this.getChildren().addAll(AchieLabel1, profileButton, buttonHome);

        unlockedBadgesContainer = new VBox();
        lockedBadgesContainer = new VBox();

        for (Badge badge : badges) {
            ImageView imageView = new ImageView(badge.getImage());
            imageView.setPreserveRatio(true);
            setImageSize(imageView, 100, 100);
            VBox.setMargin(imageView, new Insets(10, 10, 10, 5));

            int quizzesCompleted = userController.getQuizzesCompleted(userEmail);
            if (quizzesCompleted >= badge.getThreshold()) {
                unlockBadge(true, imageView);
                unlockedBadgesContainer.getChildren().add(imageView);
            } else {
                lockBadge(true, imageView);
                lockedBadgesContainer.getChildren().add(imageView);
            }
        }

        Label AchieLabel2 = new Label("Earned badges");
        Label AchieLabel3 = new Label("Locked badges");

        this.getChildren().addAll(AchieLabel2, unlockedBadgesContainer, AchieLabel3, lockedBadgesContainer);
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