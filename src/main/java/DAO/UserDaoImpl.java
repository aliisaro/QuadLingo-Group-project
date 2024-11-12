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
        // Validate the email format first
        if (user.getEmail() == null || !user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return -1; // Return -1 to indicate invalid email format
        }

        // Password validation
        if (user.getPassword() == null) {
            return -1; // Invalid password
        }
        if (!user.getPassword().matches(".*[A-Z].*")) {
            return -1; // Password must include at least 1 uppercase letter
        }
        if (!user.getPassword().matches(".*\\d.*")) {
            return -1; // Password must include at least 1 number
        }
        if (user.getPassword().length() < 8) {
            return -1; // Password must be at least 8 characters
        }

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
        StringBuilder errorMessages = new StringBuilder(); // Object to store error messages

        // First, retrieve the original username and email from the database
        User originalUser = getUserById(user.getUserId()); // You may need to implement this method to fetch the user by ID

        // Validate username if changed
        if (!user.getUsername().equals(originalUser.getUsername()) && doesUsernameExist(user.getUsername())) {
            errorMessages.append("Username already exists.\n");
        }

        // Validate email if changed
        if (!user.getEmail().equals(originalUser.getEmail())) {
            if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                errorMessages.append("Invalid email format.\n");
            } else if (doesEmailExist(user.getEmail())) {
                errorMessages.append("An account with this email already exists.\n");
            }
        }

        // Validate password if changed
        if (user.isPasswordChanged()) {
            String password = user.getPassword();
            if (!password.matches(".*[A-Z].*")) {
                errorMessages.append("Password must include at least 1 uppercase letter.\n");
            }
            if (!password.matches(".*\\d.*")) {
                errorMessages.append("Password must include at least 1 number.\n");
            }
            if (password.length() < 8) {
                errorMessages.append("Password must be at least 8 characters.\n");
            }
        }

        // If there are error messages, log them and prevent the update
        if (errorMessages.length() > 0) {
            System.out.println("Profile update errors: " + errorMessages.toString());
            return false; // Indicate the update was not successful
        }

        // Proceed with the update if all validations pass
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
    public int getQuizzesCompleted(int userId, String language) {
        int quizzesCompleted = 0;
        try (Connection connection = getConnection()) {
            String query = "SELECT COUNT(*) AS QuizzesCompleted FROM ISCOMPLETED JOIN QUIZ ON ISCOMPLETED.QuizID = QUIZ.QuizID WHERE ISCOMPLETED.UserID = ? AND QUIZ.language_code = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setString(2, language);
            System.out.println("Executing query: " + statement);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                quizzesCompleted = resultSet.getInt("QuizzesCompleted");
                System.out.println("QuizzesCompleted retrieved: " + quizzesCompleted + "Language: " + language);
            } else {
                System.out.println("No completed quizzes found for user ID: " + userId + " and language: " + language);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzesCompleted;
    }


    @Override
    public int getFlashcardsMastered(int userId, String language) {
        int flashcardsMastered = 0;
        try (Connection connection = getConnection()) {
            String query = "SELECT COUNT(*) FROM ISMASTERED " +
                    "JOIN FLASHCARD ON ISMASTERED.FlashcardID = FLASHCARD.FlashcardID " +
                    "WHERE ISMASTERED.UserID = ? AND FLASHCARD.language_code = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setString(2, language);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                flashcardsMastered = resultSet.getInt(1);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error
        }
        return flashcardsMastered;
    }


    @Override
    public String getEmail() {
        try (Connection connection = getConnection()) {
            String query = "SELECT Email FROM LINGOUSER WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, currentUserId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                email = resultSet.getString("Email");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }

    // Implement other methods related to User database operations

    @Override
    public int getCurrentUserId() {
        return currentUserId;
    }
}