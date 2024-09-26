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

    public static UserDaoImpl instance;

    public String email;

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    // Retrieves a database connection
    private Connection getConnection() throws SQLException {
        return MariaDbConnection.getConnection(); // Make sure your MariaDbConnection class is correct
    }

    // Inserts a new user into the LINGOUSER table
    @Override
    public boolean createUser(User user) {
        boolean isRegistered = false;
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO LINGOUSER (Username, UserPassword, Email, QuizzesCompleted) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getQuizzesCompleted());

            int rowsAffected = statement.executeUpdate(); // Returns number of affected rows
            if (rowsAffected > 0) {
                isRegistered = true;
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }
        return isRegistered;
    }


    // Fetches a user by their username from the LINGOUSER table
    @Override
    public User getUser(String username) {
        User user = null;
        try (Connection connection = MariaDbConnection.getConnection()) {
            String query = "SELECT * FROM LINGOUSER WHERE Username = ?";
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


    // Updates a user's info in the LINGOUSER table
    @Override
    public boolean updateUser(User user) {
        boolean isUpdated = false;
        try (Connection connection = getConnection()) {
            // Update the user information based on their email
            String query = "UPDATE LINGOUSER SET Username = ?, UserPassword = ? WHERE Email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail()); // Use email to identify user

            int rowsAffected = statement.executeUpdate();
            isUpdated = (rowsAffected > 0); // Check if any row was updated

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    // Logs in a user by their username and password
    @Override
    public User loginUser(String username, String password) {
        User user = getUser(username); // Reuse getUser method
        if (user != null && password.equals(user.getPassword())) {
            return user; // Successful login
        }
        return null; // Login failed
    }


    // Checks if a username exists in the LINGOUSER table
    @Override
    public boolean doesUsernameExist(String username) {
        boolean exists = false;
        try (Connection connection = getConnection()) {
            String query = "SELECT 1 FROM LINGOUSER WHERE Username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                exists = true; // Username exists
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    // Checks if an email exists in the LINGOUSER table
    @Override
    public boolean doesEmailExist(String email) {
        boolean exists = false;
        try (Connection connection = getConnection()) {
            String query = "SELECT 1 FROM LINGOUSER WHERE Email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                exists = true; // Email exists
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }


    @Override
    public int getQuizzesCompleted(String email) {
        int quizzesCompleted = 0;
        try (Connection connection = getConnection()) {
            String query = "SELECT QuizzesCompleted FROM LINGOUSER WHERE Email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                quizzesCompleted = resultSet.getInt("QuizzesCompleted");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzesCompleted;
    }

    @Override
    public String getEmail() {
        return email;
    }

    // Implement other methods related to User database operations
}