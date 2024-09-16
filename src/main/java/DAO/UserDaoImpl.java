package DAO;

import Database.MariaDbConnection;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Responsible for direct interactions with the database, executes SQL queries and handles results
// Not the same as UserController
public class UserDaoImpl implements UserDao {

    // Retrieves a database connection
    private Connection getConnection() throws SQLException {
        return MariaDbConnection.getConnection(); // Make sure your MariaDbConnection class is correct
    }

    // Inserts a new user into the lingouser table
    @Override
    public void createUser(User user) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO lingouser (Email, Username, UserPassword, QuizzesCompleted) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getQuizzesCompleted());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }


    // Fetches a user by their username from the lingouser table
    @Override
    public User getUser(String username) {
        User user = null;
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM lingouser WHERE Username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dbUsername = resultSet.getString("Username");
                String dbPassword = resultSet.getString("UserPassword");
                String dbEmail = resultSet.getString("Email");
                int dbQuizzesCompleted = resultSet.getInt("QuizzesCompleted");

                // Create User object with quizzesCompleted
                user = new User(dbUsername, dbPassword, dbEmail);
                user.setQuizzesCompleted(dbQuizzesCompleted);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Implement other methods related to User database operations
}