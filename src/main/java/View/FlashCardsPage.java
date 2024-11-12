package View;

import Config.LanguageConfig;
import Controller.UserController;
import DAO.FlashcardDao;
import DAO.UserDaoImpl;
import Main.SessionManager;
import Model.Flashcard;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.RotateTransition;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.List;
import java.util.ResourceBundle;

//Page for Flash Cards
public class FlashCardsPage extends BasePage {
    private final FlashcardDao flashCardDao;
    private final UserController userController;
    private List<Flashcard> flashcards;
    private Label termLabel;
    private int currentFlashCardIndex = 0;
    private int userID;
    private int currentFlashCardId;
    private boolean isMastered = false;
    private boolean isFlipped = false;
    private Button markMasteredButton;
    private Button flipFlashCardButton;
    private VBox flashcardContainer;
    private final ResourceBundle bundle;
    private String languageCode;

    public FlashCardsPage(FlashcardDao flashCardDao, String topic, Stage stage) {
        this.flashCardDao = flashCardDao;
        this.userController = new UserController(new UserDaoImpl());
        this.userID = userController.getCurrentUserId();
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale());

        // Retrieve the current language code
        this.languageCode = LanguageConfig.getInstance().getCurrentLocale().getLanguage();

        // Get the flashcards based on the topic
        if (topic.equals("Mastered Flashcards")) {
            this.flashcards = flashCardDao.getMasteredFlashcardsByUser(userID, languageCode);
            if (this.flashcards.isEmpty()) {
                Label noMasteredFlashcardsLabel = new Label(bundle.getString("noMastered")); // You don't have mastered flashcards yet
                Button endFlashCardSessionButton = new Button(bundle.getString("backToFlashLibraryButton")); // Go back to library
                endFlashCardSessionButton.setOnAction(e ->
                        this.getScene().setRoot(new FlashCardLibrary((Stage) this.getScene().getWindow()))
                );
                this.getChildren().addAll(noMasteredFlashcardsLabel, endFlashCardSessionButton);
                return;
            }
            isMastered =true;
        } else {
            this.flashcards = flashCardDao.getFlashcardsByTopic(topic, userID, LanguageConfig.getInstance().getCurrentLocale().getLanguage());
            if (this.flashcards.isEmpty()) {
                Label noFlashcardsLabel = new Label(bundle.getString("masterAllTopic")); // You have mastered all flashcards in this topic. Great job!
                Button endFlashCardSessionButton = new Button(bundle.getString("backToFlashLibraryButton")); // Go back to library
                endFlashCardSessionButton.setOnAction(e ->
                        this.getScene().setRoot(new FlashCardLibrary((Stage) this.getScene().getWindow()))
                );
                this.getChildren().addAll(noFlashcardsLabel, endFlashCardSessionButton);
                return;
            }
        }

        // If user is not logged in, redirect to index page
        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        initializeUI(stage);
        loadFlashCard(false);
    }

    // Initialize the UI components
    private void initializeUI(Stage stage) {
        termLabel = new Label();
        termLabel.setWrapText(true);
        termLabel.getStyleClass().add("label-term");

        flashcardContainer = new VBox(10, termLabel);
        flashcardContainer.setPadding(new Insets(10));
        flashcardContainer.getStyleClass().add("flashcard-container");

        flipFlashCardButton = new Button(bundle.getString("showAnswer")); // Show Answer
        flipFlashCardButton.setOnAction(e -> {
            if (!isFlipped) {
                applyFlipTransition(flashcardContainer, true);
                isFlipped = true;
            } else {
                applyFlipTransition(flashcardContainer, false);
                isFlipped = false;
            }
        });

        markMasteredButton = new Button(bundle.getString("masterThis")); // Master this
        markMasteredButton.setOnAction(e -> toggleMasteredStatus());

        Button nextFlashCardButton = new Button(bundle.getString("nextFlashcard")); // Next Flashcard
        nextFlashCardButton.setOnAction(e -> nextFlashCard());

        Button endFlashCardSessionButton = new Button(bundle.getString("backToFlashLibraryButton")); // Go back to library
        endFlashCardSessionButton.setOnAction(e ->
                endSession()
        );

        VBox buttonContainer = new VBox(10, flipFlashCardButton, markMasteredButton, nextFlashCardButton, endFlashCardSessionButton);
        buttonContainer.setPadding(new Insets(20));
        buttonContainer.getStyleClass().add("button-container");

        // Add the UI components to the layout based on the mastered status
        if (isMastered) {
            markMasteredButton.setText(bundle.getString("unmasterThis")); // Unmaster this
            this.getChildren().addAll(flashcardContainer, buttonContainer);
        } else {
            markMasteredButton.setText(bundle.getString("masterThis")); // Master this
            this.getChildren().addAll(flashcardContainer, buttonContainer);
        }
    }

    // Load the flashcard term or translation
    private void loadFlashCard(boolean showAnswer) {
        if (!showAnswer || isFlipped) {
            // Display the term of the flashcard
            termLabel.setText(flashcards.get(currentFlashCardIndex).getTerm());
            flipFlashCardButton.setText(bundle.getString("showTranslation")); // Show Translation
        } else {
            // Display the translation of the flashcard
            termLabel.setText(flashcards.get(currentFlashCardIndex).getTranslation());
            flipFlashCardButton.setText(bundle.getString("showTerm")); // Hide Translation
        }
    }

    // Load the next flashcard
    private void nextFlashCard() {

        // Move to the next flashcard
        currentFlashCardIndex++;

        // If the current flashcard index is greater than the number of flashcards, reset to the first flashcard
        if (currentFlashCardIndex >= flashcards.size()) {
            currentFlashCardIndex = 0; // Reset to the first flashcard
        }

        // Check if the next flashcard is mastered
        currentFlashCardId = flashCardDao.getCurrentFlashcardId(flashcards.get(currentFlashCardIndex).getTerm());
        isMastered = flashCardDao.isFlashcardMastered(currentFlashCardId, userID);

        // Update the button state based on the mastered status
        if (isMastered) {
            markMasteredButton.setText(bundle.getString("unmasterThis")); // Unmaster this
        } else {
            markMasteredButton.setText(bundle.getString("masterThis")); // Master this
        }

        loadFlashCard(false);
    }

    private void applyFlipTransition(VBox container, boolean show) {
        RotateTransition flipOut = new RotateTransition(Duration.millis(150), container);
        flipOut.setAxis(Rotate.Y_AXIS);
        flipOut.setFromAngle(0);
        flipOut.setToAngle(90);

        ScaleTransition flipIn = new ScaleTransition(Duration.millis(150), container);
        flipIn.setFromX(0);
        flipIn.setToX(1);


            if (show) {
                loadFlashCard(true);
            } else {
                loadFlashCard(false);
            }

            flipIn.play();
        flipIn.setOnFinished(e -> {
            // Reset the text orientation
            container.setScaleX(1);
        });
    }

    // Toggle the mastered status of the flashcard
    private void toggleMasteredStatus() {
        currentFlashCardId = flashCardDao.getCurrentFlashcardId(flashcards.get(currentFlashCardIndex).getTerm());

        if (isMastered) {
            // Unmark as mastered
            isMastered = false;
            flashCardDao.unmasterFlashcard(currentFlashCardId, userID);
            markMasteredButton.setText(bundle.getString("masterThis")); // Master this
        } else {
            // Mark as mastered
            isMastered = true;
            flashCardDao.masterFlashcard(currentFlashCardId, userID);
            markMasteredButton.setText(bundle.getString("unmasterThis")); // Unmaster this
        }

        // Refresh the list of flashcards if the topic is "Mastered Flashcards"
        if (flashcards.get(currentFlashCardIndex).getTopic().equals("Mastered Flashcards")) {
            flashcards = flashCardDao.getMasteredFlashcardsByUser(userID, languageCode);
            if (flashcards.isEmpty()) {
                Label noMasteredFlashcardsLabel = new Label(bundle.getString("noMastered")); // You don't have mastered flashcards yet
                this.getChildren().clear();
                this.getChildren().add(noMasteredFlashcardsLabel);
            } else {
                loadFlashCard(false);
            }
        }
    }

    // End the flashcard session
    private void endSession(){
        // Clear the translation label
        // Reset the current flashcard index
        currentFlashCardIndex = 0;

        // Redirect to the FlashCardLibrary page
        this.getScene().setRoot(new FlashCardLibrary((Stage) this.getScene().getWindow()));
    }
}
