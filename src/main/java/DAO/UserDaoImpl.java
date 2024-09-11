package DAO;

import Model.User;


//Responsible for the direct interactions with the database, executes SQL queries and handles results
//Not the same as UserController

public class UserDaoImpl implements UserDao {
    @Override
    public void createUser(User user) {
        // Implement database operation to create a new user
    }

    @Override
    public User getUser(String username) {
        // Implement database operation to get a user by username
        return null;
    }

    // Implement other methods related to User database operations
}