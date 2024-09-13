package DAO;

import Model.FlashCard;

public class FCImplement implements FlashCardDao{
    @Override
    public void createFlashCard(FlashCard flashCard) {
        // Implement database operation to create a new flashcard
    }

    @Override
    public FlashCard getFlashCard(String question) {
        // Implement database operation to get a flashcard by question
        return null;
    }

    // Implement other methods related to FlashCard database operations
}
