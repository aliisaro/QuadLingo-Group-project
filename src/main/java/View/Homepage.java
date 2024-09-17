// ExamplePage1.java
package View;

import Main.SessionManager;
import Model.User;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is an example page that shows the basic functionality
//of switching between pages
public class Homepage extends BasePage implements setMarginButton{

    public Homepage(Stage stage) {
        // Get the current logged-in user from the session
        User currentUser = SessionManager.getInstance().getCurrentUser();

        // If user is not logged in, redirect to index page
        if (!SessionManager.getInstance().isLoggedIn()) {
            stage.setScene(new IndexPage(stage).createScene());
            return;
        }

        // Welcome message and logout button
        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsername());
        Button logoutButton = new Button("Logout");

        // Logout logic: clears session and redirects to IndexPage
        logoutButton.setOnAction(e -> {
            SessionManager.getInstance().logout();
            stage.setScene(new IndexPage(stage).createScene());
        });

        // Add elements to layout
        this.getChildren().addAll(welcomeLabel, logoutButton);


        Button achieButton = new Button("Go to Achievements");
        setMargin(achieButton, 10, 10, 10, 5);
        achieButton.setOnAction(e -> stage.setScene(new AchiePage(stage).createScene()));
        this.getChildren().add(achieButton);

        Button profileButton = new Button("Go to Profile");
        setMargin(profileButton, 10, 10, 10, 5);
        profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));
        this.getChildren().add(profileButton);
    }

    @Override
    public void setMargin(Button button, int top, int right, int bottom, int left) {
        VBox.setMargin(button, new Insets(top, right, bottom, left));
    }
}