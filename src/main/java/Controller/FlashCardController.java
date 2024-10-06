package Controller;

import DAO.FlashCardDao;
import Model.FlashCard;

import java.util.List;

public class FlashCardController {

    private FlashCardDao flashCardDao;

    public FlashCardController(FlashCardDao flashCardDao) {
        this.flashCardDao = flashCardDao;
    }

    public List<FlashCard> getFlashCardsByTopic(String topic) {
        return flashCardDao.getFlashCardsByTopic(topic);
    }

    public List<FlashCard> getTopics() {
        return flashCardDao.getTopics();
    }

    public List<FlashCard> getMasteredFlashCards(int userId) {
        return flashCardDao.getMasteredFlashCards(userId);
    }

    public List<FlashCard> getAllFlashCards() {
        return flashCardDao.getAllFlashCards();
    }
}
