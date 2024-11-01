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
    public int getMasteredFlashcards(int user) {
        int totalMastered = 0;

        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "SELECT COUNT(*) AS TotalMastered FROM ISMASTERED WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user);
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
    public int getFlashcardAmount() {
        int totalFlashcards = 0;

        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "SELECT COUNT(*) AS TotalFlashcards FROM FLASHCARD";
            PreparedStatement statement = connection.prepareStatement(query);
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
