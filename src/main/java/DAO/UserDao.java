package DAO;

import Model.User;

public interface UserDao {
    int createUser(User user);
    boolean updateUser(User user);
    User getUserById(int userId); // Fetch user by ID
    User loginUser(String username, String password);
    boolean doesEmailExist(String email);
    boolean doesUsernameExist(String username);
    boolean deleteUserByEmail(String email);
    int getQuizzesCompleted(int userId);
    int getFlashcardsMastered(int userId);
    String getEmail();
    int getCurrentUserId();
}