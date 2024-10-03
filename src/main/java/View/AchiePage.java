package View;

import DAO.UserDaoImpl;
import Model.Badge;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Controller.UserController;

import java.util.ArrayList;
import java.util.List;

public class AchiePage extends BasePage implements ImageSize, setMarginButton, BadgeLock {
    private UserController userController;
    private List<Badge> badges;
    private VBox unlockedBadgesContainer;
    private VBox lockedBadgesContainer;

    //Displays the Achievements page
    public AchiePage(Stage stage) {
        this.userController = UserController.getInstance(UserDaoImpl.getInstance());
        this.badges = new ArrayList<>();
        badges.add(new Badge("file:src/main/resources/FirstBadge.png", 1, "Complete one quiz"));
        badges.add(new Badge("file:src/main/resources/SecondBadge.png", 5, "Complete five quizzes"));
        badges.add(new Badge("file:src/main/resources/ThirdBadge.png", 10, "Complete ten quizzes"));

        int userId = userController.getCurrentUserId();

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

        addBadgesToContainer(unlockedBadgesContainer, lockedBadgesContainer, userId);

        Label AchieLabel2 = new Label("Earned badges");
        Label AchieLabel3 = new Label("Locked badges");

        this.getChildren().addAll(AchieLabel2, unlockedBadgesContainer, AchieLabel3, lockedBadgesContainer);
    }

    //Adds badges to the container
    private void addBadgesToContainer(VBox unlockedContainer, VBox lockedContainer, int userID) {
        HBox currentUnlockedRow = null;
        HBox currentLockedRow = null;
        int unlockedBadgeCount = 0;
        int lockedBadgeCount = 0;

        for (Badge badge : badges) {
            ImageView imageView = new ImageView(badge.getImage());
            imageView.setPreserveRatio(true);
            setImageSize(imageView, 130, 130);

            Label description = new Label(badge.getDescription());
            VBox badgeContainer = new VBox(5, imageView, description);

            int quizzesCompleted = userController.getQuizzesCompleted(userID);
            int badgeThreshold = badge.getThreshold();

            // Debug statements
            System.out.println("Badge: " + badge.getDescription());
            System.out.println("Quizzes Completed: " + quizzesCompleted);
            System.out.println("Badge Threshold: " + badgeThreshold);

            if (quizzesCompleted >= badgeThreshold) {
                if (unlockedBadgeCount % 2 == 0) {
                    currentUnlockedRow = new HBox(10);
                    VBox.setMargin(currentUnlockedRow, new Insets(10, 10, 10, 5));
                    unlockedContainer.getChildren().add(currentUnlockedRow);
                }
                unlockBadge(imageView);
                currentUnlockedRow.getChildren().add(badgeContainer);
                unlockedBadgeCount++;
            } else {
                if (lockedBadgeCount % 2 == 0) {
                    currentLockedRow = new HBox(10);
                    VBox.setMargin(currentLockedRow, new Insets(10, 10, 10, 5));
                    lockedContainer.getChildren().add(currentLockedRow);
                }
                lockBadge(true, imageView);
                currentLockedRow.getChildren().add(badgeContainer);
                lockedBadgeCount++;
            }
        }
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
    public void unlockBadge(ImageView imageView) {
            ColorAdjust desaturate = new ColorAdjust();
            desaturate.setSaturation(0);
            imageView.setEffect(desaturate);
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