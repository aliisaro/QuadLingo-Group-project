package Controller;

import DAO.FlashcardDao;
import Model.Flashcard;
import java.util.List;

public class FlashcardController {

    private FlashcardDao flashcardDao;

    public FlashcardController(FlashcardDao flashcardDao) {
        this.flashcardDao = flashcardDao;
    }

    public List<Flashcard> getFlashCardsByTopic(String topic, int userId) {
        return flashcardDao.getFlashcardsByTopic(topic, userId);
    }

    public List<Flashcard> getTopics() {
        return flashcardDao.getTopics();
    }

    public List<Flashcard> getMasteredFlashCards(int userId) {
        return flashcardDao.getMasteredFlashcardsByUser(userId);
    }

    public List<Flashcard> getAllFlashcards() {
        return flashcardDao.getAllFlashcards();
    }

    public FlashcardDao getFlashcardDao() {
        return flashcardDao;
    }

    public void unmasterAllFlashcards(int userId) {
        flashcardDao.unmasterAllFlashcards(userId);
    }
}
