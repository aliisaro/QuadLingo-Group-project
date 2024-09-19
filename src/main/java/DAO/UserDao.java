package DAO;

import Model.User;

public interface UserDao {
    boolean createUser(User user);
    User getUser(String username);
    // Other methods related to User database operations

    int getQuizzesCompleted(String email);

    String getEmail();


}
