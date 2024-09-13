package DAO;

import Model.Answer;

public interface AnswerDao {
    void createAnswer(Answer answer);
    Answer getAnswer(String answer);
    // Other methods related to Answer database operations
}
