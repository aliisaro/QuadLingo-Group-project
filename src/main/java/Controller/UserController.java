package Controller;

import DAO.UserDaoImpl;
import Model.Progress;
import Model.User;

public class UserController {
    private UserDaoImpl userDao;

    private UserDaoImpl quizzesDao;

    private static UserController instance;

    public UserController(UserDaoImpl userDao) {
        this.userDao = userDao;
        quizzesDao = new UserDaoImpl();
    }

    public static UserController getInstance(UserDaoImpl userDao) {
        if (instance == null) {
            instance = new UserController(userDao);
        }
        return instance;
    }

    public User createUser(String username, String password, String email) {
        User newUser = new User(username, password, email);
        boolean success = userDao.createUser(newUser); // Check if the user was successfully created
        return success ? newUser : null; // Return the user object if successful, else null
    }

    public User loginUser(String username, String password) {
        User user = userDao.getUser(username);
        if (user != null && password.equals(user.getPassword())) {
            return user; // Successful login
        }
        return null; // Login failed
    }

    public boolean doesEmailExist(String email) {
        return userDao.doesEmailExist(email);
    }

    public boolean doesUsernameExist(String username) {
        return userDao.doesUsernameExist(username);
    }

    public int getQuizzesCompleted(String email) {
        return quizzesDao.getQuizzesCompleted(email);
    }

    public String getEmailDao() {
        return userDao.getEmail();
    }

    public Progress getUserProgress(User user) {
        return null; // Placeholder
    }
}