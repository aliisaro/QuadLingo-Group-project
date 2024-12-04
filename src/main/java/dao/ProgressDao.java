package dao;

public interface ProgressDao {

  int getUserScore(int user, String language);

  int getAllCompletedQuizzes(int user, String language);

  int getQuizAmount(String language);

  int getMaxScore(String language);

  int getMasteredFlashcards(int user, String language);

  int getFlashcardAmount(String language);
}
