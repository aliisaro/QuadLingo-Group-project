package DAO;

import Model.User;

public interface UserDao {
    boolean createUser(User user);
    User getUser(String username);
}
