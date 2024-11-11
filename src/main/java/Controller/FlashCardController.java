package Controller;

import DAO.FlashcardDao;
import Model.FlashCard;

import java.util.List;

public class FlashCardController {

    private FlashcardDao flashCardDao;

    public FlashCardController(FlashcardDao flashCardDao) {
        this.flashCardDao = flashCardDao;
    }

    public List<FlashCard> getFlashCardsByTopic(String topic, int userId) {
        return flashCardDao.getFlashcardsByTopic(topic, userId);
    }

    public List<FlashCard> getTopics() {
        return flashCardDao.getTopics();
    }

    public List<FlashCard> getMasteredFlashCards(int userId) {
        return flashCardDao.getMasteredFlashcardsByUser(userId);
    }

    public List<FlashCard> getAllFlashCards() {
        return flashCardDao.getAllFlashcards();
    }

    public FlashcardDao getFlashCardDao() {
        return flashCardDao;
    }

    public void unmasterAllFlashcards(int userId) {
        flashCardDao.unmasterAllFlashcards(userId);
    }
}
