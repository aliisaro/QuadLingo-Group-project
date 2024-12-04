package Controller;

import Dao.QuizDao;
import Model.Quiz;

import java.util.List;

public class QuizController {
  private QuizDao quizDao;

  public QuizController(QuizDao quizDao) {
    this.quizDao = quizDao;
  }

  public List<Quiz> getAllQuizzes(String language) {
    return quizDao.getAllQuizzes(language);
  }

  public QuizDao getQuizDao() {
    return this.quizDao;
  }

}
