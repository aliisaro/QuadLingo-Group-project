package DAO;

public interface ProgressDao {
    void createOverallProgress(int user, int score);
    int getProgressQuiz(int user, int quiz);

    int getUserScore(int user);

    int getAllCompletedQuizzes(int user);
    void updateOverallQuizProgress(String user, int score);

    int getQuizAmount();
    void updateProgressQuiz(int user, int score);

    int getMaxScore(int user);

    int getMasteredFlashcards(int user);

    int getFlashcardAmount();
}
