package Controller;

import DAO.UserDaoImpl;
import Model.Progress;
import Model.User;
import org.mindrot.jbcrypt.BCrypt;

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
    public User loginUser(String username, String password) {
        return userDao.loginUser(username, password);
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

    // Check if a username exists
    public boolean doesUsernameExist(String username) {
        return userDao.doesUsernameExist(username);
    }

    // Delete a user by email
    public boolean deleteUserByEmail(String email) {return userDao.deleteUserByEmail(email); }

    public int getQuizzesCompleted(int userId, String language) {
        System.out.println("Retrieving quizzes completed quizzes for user: " + userId + ". Language " + language);
        int quizzesCompleted = userDao.getQuizzesCompleted(userId, language );
        System.out.println("Quizzes completed: " + quizzesCompleted + ". Language " + language);
        return quizzesCompleted;
    }

    public int getFlashcardsMastered(int userId, String language) {
        System.out.println("Retrieving flashcards mastered for user: " + userId);
        int flashcardsMastered = userDao.getFlashcardsMastered(userId, language);
        System.out.println("Flashcards mastered: " + flashcardsMastered);
        return flashcardsMastered;
    }

    public String getEmailDao() {
        return userDao.getEmail();
    }

    public Progress getUserProgress(User user) {
        return null; // Placeholder
    }

    public int getCurrentUserId() {
        return userDao.getCurrentUserId();
    }

}