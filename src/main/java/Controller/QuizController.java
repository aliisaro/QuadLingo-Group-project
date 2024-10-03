package Controller;

import DAO.QuizDao;
import Model.Quiz;
import Model.Question;
import java.util.List;

public class QuizController {
    private QuizDao quizDao;

    public QuizController(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    public List<Quiz> getAllQuizzes() {
        return quizDao.getAllQuizzes();
    }

    public List<Question> getQuestionsForQuiz(int quizId) {
        return quizDao.getQuestionsForQuiz(quizId);
    }

    public boolean checkAnswer(int questionId, String selectedAnswer) {
        return quizDao.checkAnswer(questionId, selectedAnswer);
    }

    public QuizDao getQuizDao() {
        return this.quizDao;
    }

    // Add other controller methods as needed
}
