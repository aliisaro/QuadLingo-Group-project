package DAO;

import Model.Question;

public interface QuestionDao {
    void createQuestion(Question question);
    Question getQuestion(String question);
    // Other methods related to Question database operations
}
