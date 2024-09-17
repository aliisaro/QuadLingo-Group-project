package Main;

import Model.User;

// Singleton class to manage the current session (whether user is logged in or not)
public class SessionManager {
    private static SessionManager instance; // Instance of the SessionManager
    private User currentUser; // Object to store the current user

    // Constructor
    private SessionManager() {}

    // Get the instance of the SessionManager
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    // Set the current user when they log in
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Get the current logged-in user
    public User getCurrentUser() {
        return currentUser;
    }

    // Clear the current session when the user logs out
    public void logout() {
        currentUser = null;
    }

    // Check if a user is logged in
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
