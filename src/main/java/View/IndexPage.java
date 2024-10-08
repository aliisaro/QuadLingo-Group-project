package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class IndexPage extends BasePage{

    public IndexPage(Stage stage) {
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        // Apply padding and spacing to the VBox
        this.setPadding(new Insets(10));

        // Set the alignment of the entire page to center
        this.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome to QuadLingo!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label descriptionLabel = new Label("A Finnish language learning app");
        descriptionLabel.setStyle("-fx-font-size: 16px;");

        // go to the login page
        Button loginPageButton = new Button("Go to Login page");
        loginPageButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 200px;");
        loginPageButton.setOnAction(e -> stage.setScene(new LoginPage(stage).createScene()));

        // go to the register page
        Button registerPageButton = new Button("Go to Sign up page");
        registerPageButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px;-fx-pref-width: 200px;");
        registerPageButton.setOnAction(e -> stage.setScene(new RegistrationPage(stage).createScene()));

        // Create a container (HBox) for buttons
        VBox buttonContainer = new VBox(10);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.getChildren().addAll(loginPageButton, registerPageButton);
        buttonContainer.setAlignment(Pos.CENTER);

        //Add elements to layout
        this.getChildren().addAll(welcomeLabel, descriptionLabel, buttonContainer);
    }
}
