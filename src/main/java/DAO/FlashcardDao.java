package DAO;

import Model.FlashCard;

import java.util.List;

public interface FlashcardDao {
    List<FlashCard> getFlashcardsByTopic(String topic, int userId);
    List<FlashCard> getTopics();
    List<FlashCard> getAllFlashcards();
    void masterFlashcard(int flashCardId, int userId);
    void unmasterFlashcard(int flashCardId, int userId);
    List<FlashCard> getMasteredFlashcardsByUser(int userId);
    int getCurrentFlashcardId(String term);
    boolean isFlashcardMastered(int flashCardId, int userId);
    void unmasterAllFlashcards(int userId);
}
