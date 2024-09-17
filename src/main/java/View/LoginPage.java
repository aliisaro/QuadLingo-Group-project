package View;

import Controller.UserController;
import DAO.UserDaoImpl; // Import UserDaoImpl
import Main.SessionManager;
import Model.User;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginPage extends BasePage {
    private UserController userController;

    public LoginPage(Stage stage) {
        // Initialize UserDaoImpl and UserController
        userController = new UserController(new UserDaoImpl());

        // Set layout to the stage
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        // Create and configure the login page UI components
        Label titleLabel = new Label("Login");

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            User user = userController.loginUser(username, password);

            if (user != null) {
                // Set the current user in session (login user)
                SessionManager.getInstance().setCurrentUser(user);

                System.out.println("Login successful: " + user.getUsername());

                // Navigate to the homepage
                stage.setScene(new Homepage(stage).createScene());
            } else {
                errorLabel.setText("Login failed. Please check your credentials.");
            }
        });

        // Go back to the index page
        Button indexPageButton = new Button("Go back to Index page");
        indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

        // Add elements to layout
        getChildren().addAll(
                titleLabel,
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
                loginButton,
                errorLabel,
                indexPageButton);
    }
}

