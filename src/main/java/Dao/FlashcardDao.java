package Dao;

import Model.Flashcard;

import java.util.List;

public interface FlashcardDao {
    List<Flashcard> getFlashcardsByTopic(String topic, int userId, String languageCode);
    List<Flashcard> getTopics(String languageCode);
    void masterFlashcard(int flashCardId, int userId);
    void unmasterFlashcard(int flashCardId, int userId);
    List<Flashcard> getMasteredFlashcardsByUser(int userId, String languageCode);
    int getCurrentFlashcardId(String term);
    boolean isFlashcardMastered(int flashCardId, int userId);
    void unmasterAllFlashcards(int userId, String languageCode);
    boolean hasUserMasteredSpecificFlashcard(int flashCardId, int userId);
}
