package DAO;

public interface ProgressDao {
    void createOverallProgress(int user, int score);
    int getProgressQuiz(int user, int quiz);

    int getUserScore(int user);

    int getAllCompletedQuizzes(int user);
    void updateOverallQuizProgress(String user, int score);

    int getQuizAmount();
    void updateProgressQuiz(int user, int score);
    // Other methods related to Progress database operations

    int getMaxScore(int user);

    /* void updateProgressFlashcard(int user, int score); */
}
