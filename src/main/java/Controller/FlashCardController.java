package Controller;

import DAO.FlashCardDao;
import Model.FlashCard;

import java.util.List;

public class FlashCardController {

    private FlashCardDao flashCardDao;

    public FlashCardController(FlashCardDao flashCardDao) {
        this.flashCardDao = flashCardDao;
    }

    public List<FlashCard> getFlashCardsByTopic(String topic, int userId) {
        return flashCardDao.getFlashCardsByTopic(topic, userId);
    }

    public List<FlashCard> getTopics() {
        return flashCardDao.getTopics();
    }

    public List<FlashCard> getMasteredFlashCards(int userId) {
        return flashCardDao.getMasteredFlashCardsByUser(userId);
    }

    public List<FlashCard> getAllFlashCards() {
        return flashCardDao.getAllFlashCards();
    }

    public FlashCardDao getFlashCardDao() {
        return flashCardDao;
    }

    public void unmasterAllFlashcards(int userId) {
        flashCardDao.unmasterAllFlashCards(userId);
    }
}
