package Controller;

import DAO.FlashcardDao;
import Model.Flashcard;
import java.util.List;

public class FlashcardController {

    private FlashcardDao flashcardDao;

    public FlashcardController(FlashcardDao flashcardDao) {
        this.flashcardDao = flashcardDao;
    }

    public List<Flashcard> getFlashCardsByTopic(String topic, int userId, String languageCode) {
        return flashcardDao.getFlashcardsByTopic(topic, userId, languageCode);
    }

    public List<Flashcard> getTopics(String languageCode) {
        return flashcardDao.getTopics(languageCode);
    }

    public List<Flashcard> getMasteredFlashCards(int userId, String languageCode) {
        return flashcardDao.getMasteredFlashcardsByUser(userId, languageCode);
    }

    public List<Flashcard> getAllFlashcards(String languageCode) {
        return flashcardDao.getAllFlashcards(languageCode);
    }

    public FlashcardDao getFlashcardDao() {
        return flashcardDao;
    }

    public void unmasterAllFlashcards(int userId, String languageCode) {
        flashcardDao.unmasterAllFlashcards(userId, languageCode);
    }
}
