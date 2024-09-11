package Controller;

import Model.Progress;
import Model.User;

public class UserController {

    //Example code
    public User createUser(String username, String password, String email) {
        // Create a new User object and save it to the database
        // Return null for now to avoid errors;
        return null;
    }

    public User loginUser(String username, String password) {
        // Check the username and password against the database
        // If they match, return the User object
        // If they don't match, return an error
        return null;

    }

    public Progress getUserProgress(User user) {
        // Retrieve the user's Progress from the database and return it
        return null;
    }

}
