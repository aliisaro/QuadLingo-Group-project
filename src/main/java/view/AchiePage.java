package view;

import config.LanguageConfig;
import dao.UserDaoImpl;
import model.Badge;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controller.UserController;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AchiePage extends BasePage implements SetMarginButton {
  private static final String FLASHCARD = "flashcard";
  private final UserController userController;
  private final List<Badge> badges;
  private VBox unlockedBadgesContainer;
  private VBox lockedBadgesContainer;
  private ResourceBundle bundle;
  private final String languageCode;

  public AchiePage(Stage stage) {
    this.userController = UserController.getInstance(UserDaoImpl.getInstance());
    this.badges = new ArrayList<>();
    this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

    this.languageCode = LanguageConfig.getInstance().getCurrentLocale().getLanguage();

    // Paths to badge images
    badges.add(new Badge("file:docs/badges/FirstBadge.png", 1, bundle.getString("quizRequirement1"), "quiz"));
    badges.add(new Badge("file:docs/badges/SecondBadge.png", 5, bundle.getString("quizRequirement5"), "quiz"));
    badges.add(new Badge("file:docs/badges/Thirdbadge.png", 10, bundle.getString("quizRequirement10"), "quiz"));
    badges.add(new Badge("file:docs/badges/FlashcardBadge1.png", 5, bundle.getString("flashcardRequirement5"), FLASHCARD));
    badges.add(new Badge("file:docs/badges/FlashcardBadge2.png", 10, bundle.getString("flashcardRequirement10"), FLASHCARD));

    int userId = userController.getCurrentUserId();

    Label achieLabel1 = new Label(bundle.getString("achievementsTitle"));
    achieLabel1.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

    Button profileButton = new Button(bundle.getString("profileButton"));
    setMargin(profileButton, 10, 10, 10, 5);
    profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));

    Button buttonHome = new Button(bundle.getString("homeButton"));
    setMargin(buttonHome, 10, 10, 10, 5);
    buttonHome.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

    this.getChildren().addAll(achieLabel1, profileButton, buttonHome);

    unlockedBadgesContainer = new VBox();
    lockedBadgesContainer = new VBox();

    addBadgesToContainer(unlockedBadgesContainer, lockedBadgesContainer, userId);

    Label achieLabel2 = new Label(bundle.getString("unlockedBadges"));
    achieLabel2.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

    Label achieLabel3 = new Label(bundle.getString("lockedBadges"));
    achieLabel3.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

    unlockedBadgesContainer.setPadding(new Insets(10, 10, 10, 5));
    unlockedBadgesContainer.setStyle("-fx-background-color:rgba(255,175,135,0.62); -fx-border-color: #473d43; -fx-border-width: 2px; -fx-border-radius: 5px;");

    lockedBadgesContainer.setPadding(new Insets(10, 10, 10, 5));
    lockedBadgesContainer.setStyle("-fx-background-color:rgba(230,178,149,0.62); -fx-border-color: #473d43; -fx-border-width: 2px; -fx-border-radius: 5px;");

    this.getChildren().addAll(achieLabel2, unlockedBadgesContainer, achieLabel3, lockedBadgesContainer);
  }

  private void addBadgesToContainer(VBox unlockedContainer, VBox lockedContainer, int userId) {
    HBox currentUnlockedRow = null;
    HBox currentLockedRow = null;
    int unlockedBadgeCount = 0;
    int lockedBadgeCount = 0;

    for (Badge badge : badges) {
      ImageView imageView = new ImageView(badge.getImage());
      imageView.setPreserveRatio(true);
      setImageSize(imageView, 140, 140);

      Label description = new Label(badge.getDescription());
      VBox badgeContainer = new VBox(5, imageView, description);

      boolean isUnlocked = isBadgeUnlocked(badge, userId);

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

  private boolean isBadgeUnlocked(Badge badge, int userId) {
    int quizzesCompleted = userController.getQuizzesCompleted(userId, languageCode);
    int flashcardsMastered = userController.getFlashcardsMastered(userId, languageCode);
    int badgeThreshold = badge.getThreshold();
    String checker = badge.getChecker();

    return ("quiz".equals(checker) && quizzesCompleted >= badgeThreshold) ||
            (FLASHCARD.equals(checker) && flashcardsMastered >= badgeThreshold);
  }

  public void setImageSize(ImageView imageView, int width, int height) {
    imageView.setFitWidth(width);
    imageView.setFitHeight(height);
  }

  @Override
  public void setMargin(Button button, int top, int right, int bottom, int left) {
    VBox.setMargin(button, new Insets(top, right, bottom, left));
  }

  public void unlockBadge(ImageView imageView) {
    ColorAdjust desaturate = new ColorAdjust();
    desaturate.setSaturation(0);
    imageView.setEffect(desaturate);
  }

  public void lockBadge(boolean value, ImageView imageView) {
    if (value) {
      ColorAdjust desaturate = new ColorAdjust();
      desaturate.setSaturation(-0.5);
      imageView.setEffect(desaturate);
    }
  }
}
