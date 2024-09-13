package DAO;

import Model.FlashCard;

public interface FlashCardDao {
void createFlashCard(FlashCard flashCard);
    FlashCard getFlashCard(String question);
    // Other methods related to FlashCard database operations
}
