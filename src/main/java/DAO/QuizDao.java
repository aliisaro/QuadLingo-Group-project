package DAO;

import Model.Quiz;

public interface QuizDao {
    void createQuiz(Quiz quiz);
    Quiz getQuiz(String quizName);
    // Other methods related to Quiz database operations
}
