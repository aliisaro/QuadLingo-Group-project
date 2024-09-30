package DAO;

import Database.MariaDbConnection;
import Model.User;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

// Responsible for direct interactions with the database, executes SQL queries and handles results
// Not the same as UserController
public class UserDaoImpl implements UserDao {

    public static UserDaoImpl instance;

    private static int currentUserId;
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
    public int createUser(User user) {
        String query = "INSERT INTO LINGOUSER (Username, UserPassword, Email) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getUsername());

            // Hash the password before storing
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            statement.setString(2, hashedPassword);
            statement.setString(3, user.getEmail());

            int rowsAffected = statement.executeUpdate();

            // Get the generated user ID
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indicate failure
    }

    // Logs in a user by their username and password
    @Override
    public User loginUser(String username, String password) {
        User user = null;
        String query = "SELECT * FROM LINGOUSER WHERE Username = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String dbPassword = resultSet.getString("UserPassword");

                    // Check if the password matches the hashed password in the database
                    if (BCrypt.checkpw(password, dbPassword)) {
                        int userId = resultSet.getInt("UserID");
                        currentUserId = userId;
                        String dbEmail = resultSet.getString("Email");
                        int dbQuizzesCompleted = resultSet.getInt("QuizzesCompleted");

                        user = new User(userId, username, dbPassword, dbEmail);
                        user.setQuizzesCompleted(dbQuizzesCompleted);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user; // Return user or null if not found or password mismatched
    }

    // Fetches a user by their ID from the LINGOUSER table
    @Override
    public User getUserById(int id) {
        User user = null;
        try (Connection connection = getConnection()) {
            String query = "SELECT * FROM LINGOUSER WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dbUsername = resultSet.getString("Username");
                String dbPassword = resultSet.getString("UserPassword");
                String dbEmail = resultSet.getString("Email");
                int dbQuizzesCompleted = resultSet.getInt("QuizzesCompleted");

                user = new User(id, dbUsername, dbPassword, dbEmail);
                user.setQuizzesCompleted(dbQuizzesCompleted);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Updates a user's info in the LINGOUSER table (if password is changed, it will be hashed)
    @Override
    public boolean updateUser(User user) {
        boolean isUpdated = false;
        String query = "UPDATE LINGOUSER SET Username = ?, UserPassword = COALESCE(?, UserPassword), Email = ?, QuizzesCompleted = ? WHERE UserID = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());

            // Check if the user has changed their password
            if (user.isPasswordChanged()) {
                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
                statement.setString(2, hashedPassword); // Set the new password
            } else {
                statement.setString(2, null); // Set to null, so the existing password remains unchanged
            }

            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getQuizzesCompleted());
            statement.setInt(5, user.getUserId());

            int rowsAffected = statement.executeUpdate();
            isUpdated = (rowsAffected > 0); // Check if any row was updated

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;
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


    // Deletes a user by their email from the LINGOUSER table
    @Override
    public boolean deleteUserByEmail(String email) {
        String query = "DELETE FROM LINGOUSER WHERE Email = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Consider using a logging framework instead of printStackTrace
            e.printStackTrace();
            return false;
        }
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

    @Override
    public int getCurrentUserId() {
        return currentUserId;
    }
}