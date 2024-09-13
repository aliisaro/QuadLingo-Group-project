package DAO;

import Model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


//Responsible for the direct interactions with the database, executes SQL queries and handles results
//Not the same as UserController

public class UserDaoImpl implements UserDao {

    private static final String URL = "jdbc:mysql://localhost:3306/Quadlingo";
    private static final String USER = "lingo";
    private static final String PASSWORD = "5206xx";

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public void createUser(User user) {
        // Implement database operation to create a new user
    }

    @Override
    public User getUser(String username) {
        User user = null;
        try (Connection connection = getConnection()) {
            String query = "SELECT username, password, email FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dbUsername = resultSet.getString("username");
                String dbPassword = resultSet.getString("password");
                String dbEmail = resultSet.getString("email");
                user = new User(dbUsername, dbPassword, dbEmail);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // Implement other methods related to User database operations
}