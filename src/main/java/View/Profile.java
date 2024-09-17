package View;

import Main.SessionManager;
import Model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// Page for user profile
public class Profile extends BasePage {

    public Profile(Stage stage) {
        // Get the current logged-in user from the session
        User currentUser = SessionManager.getInstance().getCurrentUser();

        // If user is not logged in, redirect to index page
        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        // Logout button: clears session and redirects to IndexPage
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new IndexPage(stage).createScene());
        });


        // Name Field
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();
        HBox nameBox = new HBox(10, nameLabel, nameTextField);
        nameBox.setAlignment(Pos.CENTER);

        // Email Field
        Label emailLabel = new Label("Email:");
        TextField emailTextField = new TextField();
        HBox emailBox = new HBox(10, emailLabel, emailTextField);
        emailBox.setAlignment(Pos.CENTER);

        // Save Button to handle saving the profile information
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> handleSaveAction(nameTextField.getText(), emailTextField.getText()));

        // Back Button to return to ExamplePage1
        Button backButton = new Button("Back to Main");
        backButton.setOnAction(e -> stage.setScene(new Homepage(stage).createScene()));

        Button buttonProgress = new Button("Go to Progress");
        buttonProgress.setOnAction(e -> stage.setScene(new ProgressPage(stage).createScene()));

        // Layout setup
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.getChildren().addAll(nameBox, emailBox, saveButton, backButton, buttonProgress, logoutButton );
    }

    private void handleSaveAction(String name, String email) {
        // Logic to handle saving the profile information
        System.out.println("Profile saved:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }

}
