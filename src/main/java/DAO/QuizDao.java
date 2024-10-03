package DAO;

import Model.Question;
import Model.Quiz;
import java.util.List;

public interface QuizDao {
    List<Quiz> getAllQuizzes();
    List<Question> getQuestionsForQuiz(int quizId);
    boolean checkAnswer(int questionId, String selectedAnswer);
    void recordQuizCompletion(int userId, int quizId, int score);
    void incrementCompletedQuizzes(int userId);
    boolean hasUserCompletedQuiz(int userId, int quizId);
}
