package Dao;

import Model.Question;
import Model.Quiz;

import java.util.List;

public interface QuizDao {
    List<Quiz> getAllQuizzes(String language);

    List<Question> getQuestionsForQuiz(int quizId);

    boolean checkAnswer(int questionId, String selectedAnswer);

    void recordQuizCompletion(int userId, int quizId, int score);

    boolean deleteUserRecord(int userId);
}