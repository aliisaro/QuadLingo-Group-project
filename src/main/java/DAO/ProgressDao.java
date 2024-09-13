package DAO;

public interface ProgressDao {
    void createProgress(String username, String quizName, int score);
    int getProgress(String username, String quizName);
    // Other methods related to Progress database operations
}
