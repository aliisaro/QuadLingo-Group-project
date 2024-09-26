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
