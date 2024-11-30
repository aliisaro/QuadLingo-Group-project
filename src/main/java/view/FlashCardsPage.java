package view;

import Config.LanguageConfig;
import Controller.UserController;
import DAO.FlashcardDao;
import DAO.UserDaoImpl;
import Main.SessionManager;
import Model.Flashcard;
import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.ResourceBundle;

// Page for Flash Cards
public class FlashCardsPage extends BasePage {
    private static final String MASTER_THIS = "masterThis";
    private static final String UNMASTER_THIS = "unmasterThis";
    private static final String BACK_TO_FLASH_LIBRARY_BUTTON = "backToFlashLibraryButton";

    private final FlashcardDao flashCardDao;
    private final UserController userController;
    private final ResourceBundle bundle;
    private final String languageCode;

    private List<Flashcard> flashcards;
    private Label termLabel;
    private int currentFlashCardIndex = 0;
    private int userID;
    private int currentFlashCardId;
    private boolean isMastered = false;
    private boolean isFlipped = false;
    private Button markMasteredButton;
    private Button flipFlashCardButton;

    public FlashCardsPage(FlashcardDao flashCardDao, String topic, Stage stage) {
        this.flashCardDao = flashCardDao;
        this.userController = new UserController(new UserDaoImpl());
        this.userID = userController.getCurrentUserId();
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());
        this.languageCode = LanguageConfig.getInstance().getCurrentLocale().getLanguage();

        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        // Retrieve flashcards based on the topic
        if (topic.equals("Mastered Flashcards")) {
            loadMasteredFlashcards();
        } else {
            loadFlashcardsByTopic(topic);
        }

        if (flashcards == null || flashcards.isEmpty()) return;

        initializeUI();
        loadFlashCard(false);
    }

    private void loadMasteredFlashcards() {
        this.flashcards = flashCardDao.getMasteredFlashcardsByUser(userID, languageCode);
        if (flashcards.isEmpty()) {
            showNoFlashcardsMessage(bundle.getString("noMastered"));
        } else {
            isMastered = true; // Ensure mastered state is set correctly for this topic
        }
    }

    private void loadFlashcardsByTopic(String topic) {
        this.flashcards = flashCardDao.getFlashcardsByTopic(topic, userID, languageCode);
        if (flashcards.isEmpty()) {
            showNoFlashcardsMessage(bundle.getString("masterAllTopic"));
        }
    }

    private void initializeUI() {
        termLabel = new Label();
        termLabel.setWrapText(true);
        termLabel.getStyleClass().add("label-term");

        VBox flashcardContainer = new VBox(10, termLabel);
        flashcardContainer.setPadding(new Insets(10));
        flashcardContainer.getStyleClass().add("flashcard-container");

        flipFlashCardButton = new Button(bundle.getString("showAnswer"));
        flipFlashCardButton.setOnAction(e -> {
            isFlipped = !isFlipped;
            applyFlipTransition(flashcardContainer, isFlipped);
        });

        markMasteredButton = new Button(bundle.getString(isMastered ? UNMASTER_THIS : MASTER_THIS));
        markMasteredButton.setOnAction(e -> toggleMasteredStatus());

        Button nextFlashCardButton = new Button(bundle.getString("nextFlashcard"));
        nextFlashCardButton.setOnAction(e -> nextFlashCard());

        Button endFlashCardSessionButton = new Button(bundle.getString(BACK_TO_FLASH_LIBRARY_BUTTON));
        endFlashCardSessionButton.setOnAction(e -> endSession());

        VBox buttonContainer = new VBox(10, flipFlashCardButton, markMasteredButton, nextFlashCardButton, endFlashCardSessionButton);
        buttonContainer.setPadding(new Insets(20));
        buttonContainer.getStyleClass().add("button-container");

        this.getChildren().addAll(flashcardContainer, buttonContainer);
    }

    private void showNoFlashcardsMessage(String message) {
        Label noFlashcardsLabel = new Label(message);
        Button backButton = new Button(bundle.getString(BACK_TO_FLASH_LIBRARY_BUTTON));
        backButton.setOnAction(e -> this.getScene().setRoot(new FlashCardLibrary((Stage) this.getScene().getWindow())));
        this.getChildren().addAll(noFlashcardsLabel, backButton);
    }

    private void loadFlashCard(boolean showAnswer) {
        if (flashcards == null || flashcards.isEmpty()) return;

        Flashcard currentFlashcard = flashcards.get(currentFlashCardIndex);
        termLabel.setText(showAnswer ? currentFlashcard.getTranslation() : currentFlashcard.getTerm());
        flipFlashCardButton.setText(bundle.getString(showAnswer ? "showTerm" : "showTranslation"));

        // Always update the mastered button state after loading a flashcard
        currentFlashCardId = flashCardDao.getCurrentFlashcardId(currentFlashcard.getTerm());
        isMastered = flashCardDao.isFlashcardMastered(currentFlashCardId, userID);
        markMasteredButton.setText(bundle.getString(isMastered ? UNMASTER_THIS : MASTER_THIS));
    }

    private void nextFlashCard() {
        currentFlashCardIndex = (currentFlashCardIndex + 1) % flashcards.size();
        loadFlashCard(false);
    }

    private void applyFlipTransition(VBox container, boolean show) {
        // Rotate out animation
        RotateTransition flipOut = new RotateTransition(Duration.millis(150), container);
        flipOut.setAxis(Rotate.Y_AXIS);
        flipOut.setFromAngle(0);
        flipOut.setToAngle(90);
        flipOut.setOnFinished(e -> {
            // Update the flashcard text (term or translation) after the flip-out finishes
            loadFlashCard(show);

            // Rotate in animation
            RotateTransition flipIn = new RotateTransition(Duration.millis(150), container);
            flipIn.setAxis(Rotate.Y_AXIS);
            flipIn.setFromAngle(90);
            flipIn.setToAngle(0);
            flipIn.play();
        });

        flipOut.play();
    }

    private void toggleMasteredStatus() {
        currentFlashCardId = flashCardDao.getCurrentFlashcardId(flashcards.get(currentFlashCardIndex).getTerm());
        if (isMastered) {
            flashCardDao.unmasterFlashcard(currentFlashCardId, userID);
        } else {
            flashCardDao.masterFlashcard(currentFlashCardId, userID);
        }
        isMastered = !isMastered;
        markMasteredButton.setText(bundle.getString(isMastered ? UNMASTER_THIS : MASTER_THIS));
    }

    private void endSession() {
        currentFlashCardIndex = 0;
        this.getScene().setRoot(new FlashCardLibrary((Stage) this.getScene().getWindow()));
    }
}
