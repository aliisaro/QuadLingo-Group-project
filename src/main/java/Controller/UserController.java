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

        int userId = userDao.createUser(newUser); // This should return the generated user ID

        if (userId > 0) { // Check if user was created successfully
            newUser.setUserId(userId); // Set the generated ID to the user object
            return newUser; // Return the newly created user object
        }

        return null; // Return null if creation failed
    }

    public User loginUser(String username, String password) {
        // Use the UserDaoImpl to login with username and password
        return userDao.loginUser(username, password);
    }

    public boolean updateUser(User user) {
        return userDao.updateUser(user);
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