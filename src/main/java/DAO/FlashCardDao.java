package DAO;

import Model.FlashCard;

import java.util.List;

public interface FlashCardDao {
    List<FlashCard> getFlashCardsByTopic(String topic);
    List<FlashCard> getTopics();
    List<FlashCard> getAllFlashCards();
    void masterFlashCard(int flashCardId, int userId);
    void unmasterFlashCard(int flashCardId, int userId);
    List<FlashCard> getMasteredFlashCards(int userId);
    void checkMasteredStatus(int flashCardId, int userId);
    int getCurrentFlashCardId(String term);
}
