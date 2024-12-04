package dao;

import model.Quiz;
import model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuizDaoImpl implements QuizDao {
  private final Connection connection;
  private String answerLabel = "Answer";
  private static final Logger logger = Logger.getLogger(QuizDaoImpl.class.getName());

  public QuizDaoImpl(Connection connection) {
    this.connection = connection;
  }

  @Override
  public List<Quiz> getAllQuizzes(String language) {
    List<Quiz> quizzes = new ArrayList<>();
    String query = "SELECT * " + " FROM QUIZ WHERE language_code = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, language);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        int quizId = rs.getInt("QuizID");
        String quizTitle = rs.getString("QuizTitle");
        int quizScore = rs.getInt("QuizScore");
        String languageCode = rs.getString("language_code");
        quizzes.add(new Quiz(quizId, quizTitle, quizScore, languageCode));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return quizzes;
  }

  private String fetchCorrectAnswer(int questionId) {
    String correctAnswer = "";
    String correctAnswerQuery = "SELECT A.Answer FROM CORRECTANSWER CA JOIN ANSWER A ON CA.AnswerID = A.AnswerID WHERE CA.QuestionID = ?";

    try (PreparedStatement correctStmt = connection.prepareStatement(correctAnswerQuery)) {
      correctStmt.setInt(1, questionId);
      try (ResultSet correctRs = correctStmt.executeQuery()) {
        if (correctRs.next()) {
          correctAnswer = correctRs.getString(answerLabel);
        } else {
          logger.log(Level.INFO, "No correct answer found for Question ID: {0}", questionId);
        }
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error fetching correct answer for Question ID: {0}", questionId);
    }

    return correctAnswer;
  }

  @Override
  public List<Question> getQuestionsForQuiz(int quizId) {
    List<Question> questions = new ArrayList<>();
    String questionQuery = "SELECT QuestionID, Question FROM QUESTION WHERE QuizID = ?";
    String wrongAnswerQuery = "SELECT A.Answer FROM ANSWER A WHERE A.AnswerID NOT IN (SELECT AnswerID FROM CORRECTANSWER WHERE QuestionID = ?) ORDER BY RAND() LIMIT 3";

    try (PreparedStatement questionStmt = connection.prepareStatement(questionQuery)) {
      questionStmt.setInt(1, quizId);
      try (ResultSet rs = questionStmt.executeQuery()) {
        while (rs.next()) {
          int questionId = rs.getInt("QuestionID");
          String questionText = rs.getString("Question");

          // Fetch Correct Answer
          String correctAnswer = fetchCorrectAnswer(questionId);

          // Fetch Wrong Answers
          List<String> wrongAnswers = new ArrayList<>();
          try (PreparedStatement wrongStmt = connection.prepareStatement(wrongAnswerQuery)) {
            wrongStmt.setInt(1, questionId);
            try (ResultSet wrongRs = wrongStmt.executeQuery()) {
              while (wrongRs.next()) {
                wrongAnswers.add(wrongRs.getString(answerLabel));
              }
              if (wrongAnswers.isEmpty()) {
                logger.log(Level.INFO, "No wrong answers found for Question ID: {0}", questionId);
              }
            }
          }

          // Combine and Shuffle Answers
          List<String> answerOptions = new ArrayList<>();
          if (!correctAnswer.isEmpty()) {
            answerOptions.add(correctAnswer);
          }
          answerOptions.addAll(wrongAnswers);

          if (answerOptions.isEmpty()) {
            answerOptions.add("No answers available");
          }

          Collections.shuffle(answerOptions);

          Question question = new Question(questionId, questionText, answerOptions, correctAnswer);
          questions.add(question);
        }
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error fetching questions for Quiz ID: {0}", quizId);
    }

    return questions;
  }

  @Override
  public boolean checkAnswer(int questionId, String selectedAnswer) {
    String query = "SELECT A.Answer FROM CORRECTANSWER CA " +
            "JOIN ANSWER A ON CA.AnswerID = A.AnswerID " +
            "WHERE CA.QuestionID = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, questionId);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          String correctAnswer = rs.getString(answerLabel);
          logger.log(Level.INFO, "Checking Answer for Question ID {0}: {1} | Correct Answer: {2}", new Object[]{questionId, selectedAnswer, correctAnswer});
          return correctAnswer.equalsIgnoreCase(selectedAnswer.trim());
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  // method to record quiz completion
  @Override
  public void recordQuizCompletion(int userId, int quizId, int score) {
    // Check if the user has already completed the quiz
    String checkSQL = "SELECT Score FROM ISCOMPLETED WHERE UserID = ? AND QuizID = ?";

    try (PreparedStatement checkStmt = connection.prepareStatement(checkSQL)) {
      checkStmt.setInt(1, userId);
      checkStmt.setInt(2, quizId);

      try (ResultSet rs = checkStmt.executeQuery()) {
        if (rs.next()) {
          // User has completed the quiz, update the score
          String updateSQL = "UPDATE ISCOMPLETED SET Score = ? WHERE UserID = ? AND QuizID = ?";

          try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {
            updateStmt.setInt(1, score); // Update to new score
            updateStmt.setInt(2, userId);
            updateStmt.setInt(3, quizId);
            updateStmt.executeUpdate();

            logger.log(Level.INFO, "Quiz score updated: UserID = {0}, QuizID = {1}, New Score = {2}", new Object[]{userId, quizId, score});
          }
        } else {
          // User has not completed the quiz, insert a new record
          String insertSQL = "INSERT INTO ISCOMPLETED (UserID, QuizID, Score) VALUES (?, ?, ?)";

          try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, quizId);
            insertStmt.setInt(3, score);
            insertStmt.executeUpdate();

            logger.log(Level.INFO, "Quiz completion recorded: UserID = {0}, QuizID = {1}, Score = {2}", new Object[]{userId, quizId, score});
          }
        }
      }
    } catch (SQLException e) {
      logger.log(Level.SEVERE, "Error recording quiz completion: {0}", e.getMessage());
    }
  }

  @Override
  public boolean deleteUserRecord(int userId) {
    String query = "DELETE FROM ISCOMPLETED WHERE UserID = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, userId);
      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}