package Dao;

import Database.MariaDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgressDaoImpl implements ProgressDao {

  String scoreLabel = "TotalScore";

  @Override
  public int getUserScore(int user, String language) {
    int totalScore = 0;

    try (Connection connection = MariaDbConnection.getConnection()) {
      String query = "SELECT SUM(Score) AS TotalScore FROM ISCOMPLETED JOIN QUIZ ON ISCOMPLETED.QuizID = QUIZ.QuizID WHERE UserID = ? AND QUIZ.language_code = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, user);
      statement.setString(2, language);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        totalScore = resultSet.getInt(scoreLabel);
      }
      resultSet.close();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return totalScore;
  }


  @Override
  public int getMaxScore(String language) {
    int maxScore = 0;

    try (Connection connection = MariaDbConnection.getConnection()) {
      String query = "SELECT SUM(QuizScore) AS TotalMaxScore FROM QUIZ WHERE language_code = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, language);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        maxScore = resultSet.getInt("TotalMaxScore");
      }
      resultSet.close();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return maxScore;
  }


  @Override
  public int getAllCompletedQuizzes(int user, String language) {
    int totalScore = 0;

    try (Connection connection = MariaDbConnection.getConnection()) {
      String query = "SELECT COUNT(*) AS TotalScore FROM ISCOMPLETED JOIN QUIZ ON ISCOMPLETED.QuizID = QUIZ.QuizID WHERE UserID = ? AND QUIZ.language_code = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, user);
      statement.setString(2, language);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        totalScore = resultSet.getInt(scoreLabel);
      }
      resultSet.close();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return totalScore;
  }


  @Override
  public int getQuizAmount(String language) {
    int totalScore = 0;

    try (Connection connection = MariaDbConnection.getConnection()) {
      String query = "SELECT COUNT(*) AS TotalScore FROM QUIZ WHERE language_code = ?";
      try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, language);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
          totalScore = resultSet.getInt(scoreLabel);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace(); // Log the error
    }

    return totalScore;
  }


  @Override
  public int getMasteredFlashcards(int user, String language) {
    int totalMastered = 0;

    try (Connection connection = MariaDbConnection.getConnection()) {
      String query = "SELECT COUNT(*) AS TotalMastered FROM ISMASTERED " +
              "JOIN FLASHCARD ON ISMASTERED.FlashcardID = FLASHCARD.FlashcardID " +
              "WHERE ISMASTERED.UserID = ? AND FLASHCARD.language_code2 = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, user);
      statement.setString(2, language);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        totalMastered = resultSet.getInt("TotalMastered");
      }
      resultSet.close();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace(); // Log the error
    }

    return totalMastered;
  }

  @Override
  public int getFlashcardAmount(String language) {
    int totalFlashcards = 0;

    try (Connection connection = MariaDbConnection.getConnection()) {
      String query = "SELECT COUNT(*) AS TotalFlashcards FROM FLASHCARD WHERE language_code2 = ?";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, language);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        totalFlashcards = resultSet.getInt("TotalFlashcards");
      }
      resultSet.close();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace(); // Log the error
    }

    return totalFlashcards;
  }

}
