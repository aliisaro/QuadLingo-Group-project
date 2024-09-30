package DAO;

public interface ProgressDao {
    void createOverallProgress(int user, int score);
    int getProgressQuiz(int user, int quiz);

    int getOverallProgress(int user);
    void updateOverallQuizProgress(String user, int score);

    void updateProgressQuiz(int user, int score);
    // Other methods related to Progress database operations

    /* void updateProgressFlashcard(int user, int score); */
}
