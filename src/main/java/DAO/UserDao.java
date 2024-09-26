package DAO;

import Model.User;

public interface UserDao {
    boolean createUser(User user);
    boolean updateUser(User user);
    User getUser(String username);
    User loginUser(String username, String password);
    boolean doesEmailExist(String email);
    boolean doesUsernameExist(String username);
    int getQuizzesCompleted(String email);
    String getEmail();
}