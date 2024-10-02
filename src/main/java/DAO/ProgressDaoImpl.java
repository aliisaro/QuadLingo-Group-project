package DAO;

import Database.MariaDbConnection;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProgressDaoImpl implements ProgressDao {


    private Connection getConnection() throws SQLException {
        return MariaDbConnection.getConnection(); // Make sure your MariaDbConnection class is correct
    }

    @Override
    public void createOverallProgress(int user, int score) {

        // Implement database operation to create a new progress
    }

    @Override
    public int getProgressQuiz(int user, int quiz) {
        // Implement database operation to get progress by username and quizName
        int progress = 0;

        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "SELECT * FROM ISCOMPLETED WHERE UserID = ? AND QuizID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user);
            statement.setInt(2, quiz);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                progress = resultSet.getInt("Score");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }

        return progress;
    }



    @Override
    public int getUserScore(int user) {
        // Implement database operation to get overall progress
        int totalScore = 0;

        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "SELECT SUM(Score) AS TotalScore FROM ISCOMPLETED WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalScore = resultSet.getInt("TotalScore");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }

        return totalScore;
    }

    @Override
    public int getMaxScore(int user) {
        int maxScore = 0;

        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "SELECT SUM(QuizScore) AS TotalMaxScore FROM QUIZ";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                maxScore = resultSet.getInt("TotalMaxScore");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }

        return maxScore;
    }

    @Override
    public int getAllCompletedQuizzes(int user) {
        // Implement database operation to get overall progress
        int totalScore = 0;

        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "SELECT COUNT(*) AS TotalScore FROM ISCOMPLETED WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalScore = resultSet.getInt("TotalScore");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }

        return totalScore;
    }

    @Override
    public int getQuizAmount() {
        // Implement database operation to get overall progress
        int totalScore = 0;

        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "SELECT COUNT(*) AS TotalScore FROM QUIZ";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalScore = resultSet.getInt("TotalScore");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }

        return totalScore;
    }

    @Override
    public void updateOverallQuizProgress(String username, int score) {
        // Implement database operation to update overall progress
    }

    @Override
    public void updateProgressQuiz(int user, int score) {
        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "UPDATE ISCOMPLETED SET Score = ? WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, score);
            statement.setInt(2, user);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }
        // Implement database operation to update progress
    }

    // Uncomment if this method is needed
    /* @Override
    public void updateProgressFlashcard(int user, int flashcard) {
        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "UPDATE ISMASTERED SET FlashcardID = ? WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, flashcard);
            statement.setInt(2, user);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }
    } */


    // Implement other methods related to Progress database operations
}
