package Controller;

import Dao.FlashcardDao;
import Model.Flashcard;
import java.util.List;

public class FlashcardController {

    private FlashcardDao flashcardDao;

    public FlashcardController(FlashcardDao flashcardDao) {
        this.flashcardDao = flashcardDao;
    }

    public List<Flashcard> getTopics(String languageCode) {
        return flashcardDao.getTopics(languageCode);
    }

    public FlashcardDao getFlashcardDao() {
        return flashcardDao;
    }

    public void unmasterAllFlashcards(int userId, String languageCode) {
        flashcardDao.unmasterAllFlashcards(userId, languageCode);
    }
}
