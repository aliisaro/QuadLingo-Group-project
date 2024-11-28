package view;

import Config.LanguageConfig;
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
import java.util.ResourceBundle;

public class AchiePage extends BasePage implements setMarginButton {
    private final UserController userController;
    private final List<Badge> badges;
    private VBox unlockedBadgesContainer;
    private VBox lockedBadgesContainer;
    private ResourceBundle bundle;
    private final String languageCode;

    //Displays the Achievements page
    public AchiePage(Stage stage) {
        this.userController = UserController.getInstance(UserDaoImpl.getInstance());
        this.badges = new ArrayList<>();
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

        // Retrieve the current language code
        this.languageCode = LanguageConfig.getInstance().getCurrentLocale().getLanguage();

        //Paths to badge images
        badges.add(new Badge("file:docs/badges/FirstBadge.png", 1, bundle.getString("quizRequirement1"),"quiz"));
        badges.add(new Badge("file:docs/badges/SecondBadge.png", 5, bundle.getString("quizRequirement5"), "quiz"));
        badges.add(new Badge("file:docs/badges/Thirdbadge.png", 10, bundle.getString("quizRequirement10"), "quiz"));
        badges.add(new Badge("file:docs/badges/FlashcardBadge1.png", 5, bundle.getString("flashcardRequirement5"), "flashcard"));
        badges.add(new Badge("file:docs/badges/FlashcardBadge2.png", 10, bundle.getString("flashcardRequirement10"), "flashcard"));

        int userId = userController.getCurrentUserId();

        Label AchieLabel1 = new Label(bundle.getString("achievementsTitle"));
        AchieLabel1.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        //Button to go to the profile page
        Button profileButton = new Button(bundle.getString("profileButton"));
        setMargin(profileButton, 10, 10, 10, 5);
        profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));

        //Button to go back to the homepage
        Button buttonHome = new Button(bundle.getString("homeButton"));
        setMargin(buttonHome, 10, 10, 10, 5);
        buttonHome.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        this.getChildren().addAll(AchieLabel1, profileButton, buttonHome);

        //Containers for unlocked and locked badges
        unlockedBadgesContainer = new VBox();
        lockedBadgesContainer = new VBox();

        addBadgesToContainer(unlockedBadgesContainer, lockedBadgesContainer, userId);

        Label AchieLabel2 = new Label(bundle.getString("unlockedBadges"));
        AchieLabel2.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        Label AchieLabel3 = new Label(bundle.getString("lockedBadges"));
        AchieLabel3.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        unlockedBadgesContainer.setPadding(new Insets(10, 10, 10, 5));
        unlockedBadgesContainer.setStyle("-fx-background-color:rgba(255,175,135,0.62); -fx-border-color: #473d43; -fx-border-width: 2px; -fx-border-radius: 5px;");

        lockedBadgesContainer.setPadding(new Insets(10, 10, 10, 5));
        lockedBadgesContainer.setStyle("-fx-background-color:rgba(230,178,149,0.62); -fx-border-color: #473d43; -fx-border-width: 2px; -fx-border-radius: 5px;");

        this.getChildren().addAll(AchieLabel2, unlockedBadgesContainer, AchieLabel3, lockedBadgesContainer);
    }

    //Adds badges to the container
    private void addBadgesToContainer(VBox unlockedContainer, VBox lockedContainer, int userID) {
        HBox currentUnlockedRow = null;
        HBox currentLockedRow = null;
        int unlockedBadgeCount = 0;
        int lockedBadgeCount = 0;

        //Iterates through the badges
        for (Badge badge : badges) {
            ImageView imageView = new ImageView(badge.getImage());
            imageView.setPreserveRatio(true);
            setImageSize(imageView, 140, 140);

            Label description = new Label(badge.getDescription());
            VBox badgeContainer = new VBox(5, imageView, description);

            int quizzesCompleted = userController.getQuizzesCompleted(userID, languageCode);
            int flashcardsMastered = userController.getFlashcardsMastered(userID, languageCode);
            int badgeThreshold = badge.getThreshold();
            String checker = badge.getChecker();

            boolean isUnlocked = false;

            // Check if the badge is for quizzes or flashcards
            if ("quiz".equals(checker) && quizzesCompleted >= badgeThreshold) {
                isUnlocked = true;
            } else if ("flashcard".equals(checker) && flashcardsMastered >= badgeThreshold) {
                isUnlocked = true;
            }

            // Adds the badge to the appropriate container
            if (isUnlocked) {
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

    //Sets the size of the image
    public void setImageSize(ImageView imageView, int width, int height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    @Override
    //Sets the margin of the button
    public void setMargin(Button button, int top, int right, int bottom, int left) {
        VBox.setMargin(button, new Insets(top, right, bottom, left));
    }

    //Unlocks the badge
    public void unlockBadge(ImageView imageView) {
        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(0);
        imageView.setEffect(desaturate);
    }

    //Locks the badge
    public void lockBadge(boolean value, ImageView imageView) {
        if (value) {
            ColorAdjust desaturate = new ColorAdjust();
            desaturate.setSaturation(-0.5);
            imageView.setEffect(desaturate);
        }
    }
}