package DAO;

public interface ProgressDao {
    void createOverallProgress(String username, int score);
    int getProgress(String username, String quizName);
    void updateOverallProgress(String username, String quizName, int score);

    void updateProgress(String username, int score);
    // Other methods related to Progress database operations
}
