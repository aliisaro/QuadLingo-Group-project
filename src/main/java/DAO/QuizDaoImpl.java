package DAO;

import Model.Quiz;
import Model.Question;
import Database.MariaDbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Database.MariaDbConnection.getConnection;

public class QuizDaoImpl implements QuizDao {
    private Connection connection;

    public QuizDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT QuizID, QuizTitle, QuizScore FROM QUIZ";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int quizId = rs.getInt("QuizID");
                String quizTitle = rs.getString("QuizTitle");
                int quizScore = rs.getInt("QuizScore");
                quizzes.add(new Quiz(quizId, quizTitle, quizScore));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizzes;
    }

    @Override
    public List<Question> getQuestionsForQuiz(int quizId) {
        List<Question> questions = new ArrayList<>();
        String questionQuery = "SELECT QuestionID, Question FROM QUESTION WHERE QuizID = ?";
        String correctAnswerQuery = "SELECT A.Answer FROM CORRECTANSWER CA JOIN ANSWER A ON CA.AnswerID = A.AnswerID WHERE CA.QuestionID = ?";
        String wrongAnswerQuery = "SELECT A.Answer FROM ANSWER A WHERE A.AnswerID NOT IN (SELECT AnswerID FROM CORRECTANSWER WHERE QuestionID = ?) ORDER BY RAND() LIMIT 3"; // Fetch multiple wrong answers

        try (PreparedStatement questionStmt = connection.prepareStatement(questionQuery)) {
            questionStmt.setInt(1, quizId);
            try (ResultSet rs = questionStmt.executeQuery()) {
                while (rs.next()) {
                    int questionId = rs.getInt("QuestionID");
                    String questionText = rs.getString("Question");

                    // Fetch Correct Answer
                    String correctAnswer = "";
                    try (PreparedStatement correctStmt = connection.prepareStatement(correctAnswerQuery)) {
                        correctStmt.setInt(1, questionId);
                        try (ResultSet correctRs = correctStmt.executeQuery()) {
                            if (correctRs.next()) {
                                correctAnswer = correctRs.getString("Answer");
                            } else {
                                System.out.println("No correct answer found for Question ID: " + questionId);
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Error fetching correct answer for Question ID: " + questionId);
                        e.printStackTrace();
                    }


                    // Fetch Wrong Answers
                    List<String> wrongAnswers = new ArrayList<>();
                    try (PreparedStatement wrongStmt = connection.prepareStatement(wrongAnswerQuery)) {
                        wrongStmt.setInt(1, questionId);
                        try (ResultSet wrongRs = wrongStmt.executeQuery()) {
                            while (wrongRs.next()) {
                                wrongAnswers.add(wrongRs.getString("Answer"));
                            }
                            if (wrongAnswers.isEmpty()) {
                                System.out.println("No wrong answers found for Question ID: " + questionId);
                            }
                        }
                    }

                    // Combine and Shuffle Answers
                    List<String> answerOptions = new ArrayList<>();
                    if (!correctAnswer.isEmpty()) {
                        answerOptions.add(correctAnswer);
                    }
                    answerOptions.addAll(wrongAnswers); // Add all wrong answers

                    // Ensure there are answer options
                    if (answerOptions.isEmpty()) {
                        answerOptions.add("No answers available"); // Placeholder for no answers
                    }

                    // Shuffle to randomize answer order
                    Collections.shuffle(answerOptions);

                    // Create Question object
                    Question question = new Question(questionId, questionText, answerOptions, correctAnswer);
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                    String correctAnswer = rs.getString("Answer");
                    System.out.println("Checking Answer for Question ID " + questionId + ": " + selectedAnswer + " | Correct Answer: " + correctAnswer);
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
                    int existingScore = rs.getInt("Score");
                    String updateSQL = "UPDATE ISCOMPLETED SET Score = ? WHERE UserID = ? AND QuizID = ?";

                    try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {
                        updateStmt.setInt(1, score); // Update to new score
                        updateStmt.setInt(2, userId);
                        updateStmt.setInt(3, quizId);
                        updateStmt.executeUpdate();

                        System.out.println("Quiz score updated: UserID = " + userId + ", QuizID = " + quizId + ", New Score = " + score);
                    }
                } else {
                    // User has not completed the quiz, insert a new record
                    String insertSQL = "INSERT INTO ISCOMPLETED (UserID, QuizID, Score) VALUES (?, ?, ?)";

                    try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
                        insertStmt.setInt(1, userId);
                        insertStmt.setInt(2, quizId);
                        insertStmt.setInt(3, score);
                        insertStmt.executeUpdate();

                        System.out.println("Quiz completion recorded: UserID = " + userId + ", QuizID = " + quizId + ", Score = " + score);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error recording quiz completion: " + e.getMessage());
        }
    }


    @Override
    public void incrementCompletedQuizzes(int userId) {
        String sql = "UPDATE LINGOUSER SET QuizzesCompleted = QuizzesCompleted + 1 WHERE UserID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions as needed
        }
    }

    @Override
    public boolean hasUserCompletedQuiz(int userId, int quizId) {
        // Implement the logic to check if the user has completed the quiz
        String sql = "SELECT COUNT(*) FROM ISCOMPLETED WHERE UserID = ? AND QuizID = ?";
        try (Connection connection = getConnection(); // Get a connection
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, quizId);

            // Execute the query
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Return true if the user has completed the quiz
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Default to false if an error occurs or no records are found
    }
}
