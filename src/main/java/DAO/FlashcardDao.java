package DAO;

import Model.Flashcard;

import java.util.List;

public interface FlashcardDao {
    List<Flashcard> getFlashcardsByTopic(String topic, int userId, String languageCode);
    List<Flashcard> getTopics(String languageCode);
    List<Flashcard> getAllFlashcards(String languageCode);
    void masterFlashcard(int flashCardId, int userId);
    void unmasterFlashcard(int flashCardId, int userId);
    List<Flashcard> getMasteredFlashcardsByUser(int userId);
    int getCurrentFlashcardId(String term);
    boolean isFlashcardMastered(int flashCardId, int userId);
    void unmasterAllFlashcards(int userId);
}
