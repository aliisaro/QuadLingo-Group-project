package View;

import Controller.UserController;
import DAO.FlashCardDao;
import DAO.UserDaoImpl;
import Main.SessionManager;
import Model.FlashCard;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

//Page for Flash Cards
public class FlashCardsPage extends BasePage {
    private final FlashCardDao flashCardDao;
    private final UserController userController;
    private List<FlashCard> flashcards;
    private Label termLabel;
    private Label translationLabel;
    private int currentFlashCardIndex = 0;
    private int userID;
    private int currentFlashCardId;
    private boolean isMastered = false;
    private Button markMasteredButton;
    private Button unmarkMasteredButton;

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
        termLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        translationLabel = new Label();
        translationLabel.setWrapText(true);
        translationLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button flipFlashCardButton = new Button("Show Answer");
        flipFlashCardButton.setOnAction(e -> loadFlashCard(true));

        markMasteredButton = new Button("Master this");
        markMasteredButton.setOnAction(e -> toggleMasteredStatus());

        unmarkMasteredButton = new Button("Unmaster this");
        unmarkMasteredButton.setOnAction(e -> toggleMasteredStatus());

        Button nextFlashCardButton = new Button("Next Flashcard");
        nextFlashCardButton.setOnAction(e -> nextFlashCard());

        Button endFlashCardSessionButton = new Button("Go back to library");
        endFlashCardSessionButton.setOnAction(e ->
                endSession()
        );

        // Add the UI components to the layout based on the mastered status
        if (isMastered) {
            this.getChildren().addAll(termLabel, translationLabel, flipFlashCardButton, unmarkMasteredButton, nextFlashCardButton, endFlashCardSessionButton);
        } else {
            this.getChildren().addAll(termLabel, translationLabel, flipFlashCardButton, markMasteredButton, nextFlashCardButton, endFlashCardSessionButton);
        }
    }

    // Load the flashcard term or translation
    private void loadFlashCard(boolean showAnswer) {
        if (!showAnswer) {
            // Display the term of the flashcard
            termLabel.setText(flashcards.get(currentFlashCardIndex).getTerm());
        } else {
            // Display the translation of the flashcard
            translationLabel.setText(flashcards.get(currentFlashCardIndex).getTranslation());
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
            this.getChildren().remove(markMasteredButton);
            if (!this.getChildren().contains(unmarkMasteredButton)) {
                this.getChildren().add(unmarkMasteredButton);
            }
        } else {
            this.getChildren().remove(unmarkMasteredButton);
            if (!this.getChildren().contains(markMasteredButton)) {
                this.getChildren().add(markMasteredButton);
            }
        }

        loadFlashCard(false);
    }

    // Toggle the mastered status of the flashcard
    private void toggleMasteredStatus() {
        if (isMastered) {
            // Unmark as mastered
            isMastered = false;
            currentFlashCardId = flashCardDao.getCurrentFlashCardId(flashcards.get(currentFlashCardIndex).getTerm());
            flashCardDao.unmasterFlashCard(currentFlashCardId, userID);
            this.getChildren().remove(unmarkMasteredButton);
            this.getChildren().add(markMasteredButton);
        } else {
            // Mark as mastered
            isMastered = true;
            currentFlashCardId = flashCardDao.getCurrentFlashCardId(flashcards.get(currentFlashCardIndex).getTerm());
            flashCardDao.masterFlashCard(currentFlashCardId, userID);
            this.getChildren().remove(markMasteredButton);
            this.getChildren().add(unmarkMasteredButton);
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
        translationLabel.setText("");
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
