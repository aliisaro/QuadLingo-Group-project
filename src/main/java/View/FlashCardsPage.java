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

        if (topic.equals("Mastered Flashcards")) {
            this.flashcards = flashCardDao.getMasteredFlashCardsByUser(userID);
            if (this.flashcards.isEmpty()) {
                Label noMasteredFlashcardsLabel = new Label("You don't have mastered flashcards yet");
                this.getChildren().add(noMasteredFlashcardsLabel);
                return;
            }
            isMastered =true;
        } else {
            this.flashcards = flashCardDao.getFlashCardsByTopic(topic, userID);
        }

        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }
        initializeUI(stage);
        loadFlashCard(false);
    }

    private void initializeUI(Stage stage) {
        termLabel = new Label();
        termLabel.setWrapText(true);
        termLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        translationLabel = new Label();
        translationLabel.setWrapText(true);
        translationLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button flipFlashCardButton = new Button("Show Answer");
        flipFlashCardButton.setOnAction(e -> loadFlashCard(true));

        markMasteredButton = new Button("Mark as Mastered");
        markMasteredButton.setOnAction(e -> toggleMasteredStatus());

        unmarkMasteredButton = new Button("Unmark as Mastered");
        unmarkMasteredButton.setOnAction(e -> toggleMasteredStatus());

        Button nextFlashCardButton = new Button("Next Flashcard");
        nextFlashCardButton.setOnAction(e -> nextFlashCard());

        Button endFlashCardSessionButton = new Button("End Session");
        endFlashCardSessionButton.setOnAction(e ->
                endSession()
        );

        if (isMastered) {
            this.getChildren().addAll(termLabel, translationLabel, flipFlashCardButton, unmarkMasteredButton, nextFlashCardButton, endFlashCardSessionButton);
        } else {
            this.getChildren().addAll(termLabel, translationLabel, flipFlashCardButton, markMasteredButton, nextFlashCardButton, endFlashCardSessionButton);
        }
    }

    private void loadFlashCard(boolean showAnswer) {
        if (!showAnswer) {
            // Display the term of the flashcard
            termLabel.setText(flashcards.get(currentFlashCardIndex).getTerm());
        } else {
            // Display the translation of the flashcard
            translationLabel.setText(flashcards.get(currentFlashCardIndex).getTranslation());
        }
    }

    private void nextFlashCard() {
        if (isMastered) {
            currentFlashCardId = flashCardDao.getCurrentFlashCardId(flashcards.get(currentFlashCardIndex).getTerm());
            flashCardDao.masterFlashCard(currentFlashCardId, userID);
        }
        isMastered = false;
        currentFlashCardIndex++;

        if (currentFlashCardIndex >= flashcards.size()) {
            currentFlashCardIndex = 0; // Reset to the first flashcard
        }

        loadFlashCard(false);
    }

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
