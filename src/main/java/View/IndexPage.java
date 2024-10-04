package View;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class IndexPage extends BasePage{

    public IndexPage(Stage stage) {

        Label welcomeLabel = new Label("Welcome to QuadLingo!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-padding: 10px;");

        // go to the login page
        Button loginPageButton = new Button("Go to Login page");
        loginPageButton.setOnAction(e -> stage.setScene(new LoginPage(stage).createScene()));

        // go to the register page
        Button registerPageButton = new Button("Go to Sign up page");
        registerPageButton.setOnAction(e -> stage.setScene(new RegistrationPage(stage).createScene()));

        //Add elements to layout
        this.getChildren().addAll(welcomeLabel, loginPageButton, registerPageButton);
    }
}
