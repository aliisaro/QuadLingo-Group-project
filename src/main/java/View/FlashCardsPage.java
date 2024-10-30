package View;

import Controller.UserController;
import DAO.FlashCardDao;
import DAO.UserDaoImpl;
import Main.SessionManager;
import Model.FlashCard;
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

//Page for Flash Cards
public class FlashCardsPage extends BasePage {
    private final FlashCardDao flashCardDao;
    private final UserController userController;
    private List<FlashCard> flashcards;
    private Label termLabel;
    private int currentFlashCardIndex = 0;
    private int userID;
    private int currentFlashCardId;
    private boolean isMastered = false;
    private boolean isFlipped = false;
    private Button markMasteredButton;
    private Button flipFlashCardButton;
    private VBox flashcardContainer;

    public FlashCardsPage(FlashCardDao flashCardDao, String topic, Stage stage) {
        this.flashCardDao = flashCardDao;
        this.userController = new UserController(new UserDaoImpl());
        this.userID = userController.getCurrentUserId();

        // Get the flashcards based on the topic
        if (topic.equals("Mastered Flashcards")) {
            this.flashcards = flashCardDao.getMasteredFlashCardsByUser(userID);
            if (this.flashcards.isEmpty()) {
                Label noMasteredFlashcardsLabel = new Label("You don't have mastered flashcards yet");
                Button endFlashCardSessionButton = new Button("Go back to library");
                endFlashCardSessionButton.setOnAction(e ->
                        this.getScene().setRoot(new FlashCardLibrary((Stage) this.getScene().getWindow()))
                );
                this.getChildren().addAll(noMasteredFlashcardsLabel, endFlashCardSessionButton);
                return;
            }
            isMastered =true;
        } else {
            this.flashcards = flashCardDao.getFlashCardsByTopic(topic, userID);
            if (this.flashcards.isEmpty()) {
                Label noFlashcardsLabel = new Label("You have mastered all flashcards in this topic. Great job!");
                Button endFlashCardSessionButton = new Button("Go back to library");
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

        flipFlashCardButton = new Button("Show Answer");
        flipFlashCardButton.setOnAction(e -> {
            if (!isFlipped) {
                applyFlipTransition(flashcardContainer, true);
                isFlipped = true;
            } else {
                applyFlipTransition(flashcardContainer, false);
                isFlipped = false;
            }
        });

        markMasteredButton = new Button("Master this");
        markMasteredButton.setOnAction(e -> toggleMasteredStatus());

        Button nextFlashCardButton = new Button("Next Flashcard");
        nextFlashCardButton.setOnAction(e -> nextFlashCard());

        Button endFlashCardSessionButton = new Button("Go back to library");
        endFlashCardSessionButton.setOnAction(e ->
                endSession()
        );

        VBox buttonContainer = new VBox(10, flipFlashCardButton, markMasteredButton, nextFlashCardButton, endFlashCardSessionButton);
        buttonContainer.setPadding(new Insets(20));
        buttonContainer.getStyleClass().add("button-container");

        // Add the UI components to the layout based on the mastered status
        if (isMastered) {
            markMasteredButton.setText("Unmaster this");
            this.getChildren().addAll(flashcardContainer, buttonContainer);
        } else {
            markMasteredButton.setText("Master this");
            this.getChildren().addAll(flashcardContainer, buttonContainer);
        }
    }

    // Load the flashcard term or translation
    private void loadFlashCard(boolean showAnswer) {
        if (!showAnswer || isFlipped) {
            // Display the term of the flashcard
            termLabel.setText(flashcards.get(currentFlashCardIndex).getTerm());
            flipFlashCardButton.setText("Show Translation");
        } else {
            // Display the translation of the flashcard
            termLabel.setText(flashcards.get(currentFlashCardIndex).getTranslation());
            flipFlashCardButton.setText("Hide Translation");
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
        currentFlashCardId = flashCardDao.getCurrentFlashCardId(flashcards.get(currentFlashCardIndex).getTerm());
        isMastered = flashCardDao.isFlashCardMastered(currentFlashCardId, userID);

        // Update the button state based on the mastered status
        if (isMastered) {
            markMasteredButton.setText("Unmaster this");
        } else {
            markMasteredButton.setText("Master this");
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
        if (isMastered) {
            // Unmark as mastered
            isMastered = false;
            currentFlashCardId = flashCardDao.getCurrentFlashCardId(flashcards.get(currentFlashCardIndex).getTerm());
            flashCardDao.unmasterFlashCard(currentFlashCardId, userID);
            markMasteredButton.setText("Master this");
        } else {
            // Mark as mastered
            isMastered = true;
            currentFlashCardId = flashCardDao.getCurrentFlashCardId(flashcards.get(currentFlashCardIndex).getTerm());
            flashCardDao.masterFlashCard(currentFlashCardId, userID);
            markMasteredButton.setText("Unmaster this");
        }

        // Refresh the list of flashcards
        if (flashcards.get(currentFlashCardIndex).getTopic().equals("Mastered Flashcards")) {
            flashcards = flashCardDao.getMasteredFlashCardsByUser(userID);
            if (flashcards.isEmpty()) {
                Label noMasteredFlashcardsLabel = new Label("You don't have mastered flashcards yet");
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

        if (isMastered) {
            currentFlashCardId = flashCardDao.getCurrentFlashCardId(flashcards.get(currentFlashCardIndex).getTerm());
            flashCardDao.masterFlashCard(currentFlashCardId, userID);
        }

        // Redirect to the FlashCardLibrary page
        this.getScene().setRoot(new FlashCardLibrary((Stage) this.getScene().getWindow()));
    }
}
