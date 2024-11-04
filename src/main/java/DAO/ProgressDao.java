package DAO;

public interface ProgressDao {

    int getUserScore(int user);

    int getAllCompletedQuizzes(int user);

    int getQuizAmount();

    int getMaxScore(int user);

    int getMasteredFlashcards(int user);

    int getFlashcardAmount();
}
