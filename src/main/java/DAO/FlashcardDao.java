package DAO;

import Model.Flashcard;

import java.util.List;

public interface FlashcardDao {
    List<Flashcard> getFlashcardsByTopic(String topic, int userId);
    List<Flashcard> getTopics();
    List<Flashcard> getAllFlashcards();
    void masterFlashcard(int flashCardId, int userId);
    void unmasterFlashcard(int flashCardId, int userId);
    List<Flashcard> getMasteredFlashcardsByUser(int userId);
    int getCurrentFlashcardId(String term);
    boolean isFlashcardMastered(int flashCardId, int userId);
    void unmasterAllFlashcards(int userId);
}
