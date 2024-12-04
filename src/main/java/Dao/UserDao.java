package Dao;

import Model.User;

public interface UserDao {
  int createUser(User user);

  boolean updateUser(User user);

  User getUserById(int userId); // Fetch user by ID

  User loginUser(String email, String password);

  boolean doesEmailExist(String email);

  boolean doesUsernameExist(String username);

  boolean deleteUserByEmail(String email);

  int getQuizzesCompleted(int userId, String language);

  int getFlashcardsMastered(int userId, String language);

  String getEmail();

  int getCurrentUserId();

  Boolean getUserByEmail(String email);
}