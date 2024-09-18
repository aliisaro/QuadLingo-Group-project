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
        userDao.createUser(newUser); // Call DAO to save the user in the DB
        return newUser;
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
