package Controller;

import Model.Progress;
import Model.User;

//Responsible for handling user requests and coordinating the flow of data between user model
//and the user interface (View). Uses the UserDaoImpl to perform the necessary database operations
//Not the same as UserDaoImpl

//Example provided by Copilot: User tries to log in, the UserController would take the username
//and password, pass them to the UserDaoImpl to check against the database and then based on
//the result, it would either return the User object or an error message.

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
