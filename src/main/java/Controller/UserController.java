package Controller;

import DAO.UserDaoImpl;
import Model.Progress;
import Model.User;

public class UserController {
    private UserDaoImpl userDao;

    public UserController(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public User createUser(String username, String password, String email) {
        User newUser = new User(username, password, email);
        boolean success = userDao.createUser(newUser); // Check if the user was successfully created
        if (success) {
            return newUser; // Return the user object if successful
        } else {
            return null; // Return null if registration failed
        }
    }


    public User loginUser(String username, String password) {
        User user = userDao.getUser(username);
        if (user != null) {
            System.out.println("User found: " + user.getUsername());
            if (password.equals(user.getPassword())) {
                return user;
            } else {
                System.out.println("Invalid password.");
            }
        } else {
            System.out.println("User not found.");
        }
        return null;
    }

    public Progress getUserProgress(User user) {
        return null; // Placeholder
    }
}
