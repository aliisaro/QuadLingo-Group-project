package DAO;

import Database.MariaDbConnection;
import Model.Progress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProgressDaoImpl implements ProgressDao {


    private Connection getConnection() throws SQLException {
        return MariaDbConnection.getConnection(); // Make sure your MariaDbConnection class is correct
    }

    @Override
    public void createOverallProgress(String username, int score) {

        // Implement database operation to create a new progress
    }

    @Override
    public int getProgress(String username, String quizName) {
        // Implement database operation to get progress by username and quizName
        int progress = 0;

        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "SELECT * FROM ISCOMPLETED WHERE UserID = ? AND QuizID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, quizName);
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
    public void updateOverallProgress(String username, String quizName, int score) {
        // Implement database operation to update overall progress
    }

    @Override
    public void updateProgress(String username, int score) {
        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "UPDATE ISCOMPLETED SET Score = ? WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, score);
            statement.setString(2, username);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }
        // Implement database operation to update progress
    }

    // Implement other methods related to Progress database operations
}
