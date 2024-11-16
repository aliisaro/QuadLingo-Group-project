package Controller;

import DAO.UserDaoImpl;
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

    // Create a new user
    public User createUser(String username, String password, String email) {
        User newUser = new User(username, password, email);

        int userId = userDao.createUser(newUser);

        if (userId > 0) {
            newUser.setUserId(userId);
            return newUser;
        }
        return null;
    }

    // Login a user
    public User loginUser(String email, String password) {
        return userDao.loginUser(email, password);
    }

    // Get a user by their ID
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    // Update a user's information
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    // Check if an email exists
    public boolean doesEmailExist(String email) {
        return userDao.doesEmailExist(email);
    }

    // Delete a user by email
    public boolean deleteUserByEmail(String email) {return userDao.deleteUserByEmail(email); }

    public int getQuizzesCompleted(int userId, String language) {
        return userDao.getQuizzesCompleted(userId, language );
    }

    public int getFlashcardsMastered(int userId, String language) {
        return userDao.getFlashcardsMastered(userId, language);
    }

    public int getCurrentUserId() {
        return userDao.getCurrentUserId();
    }

}