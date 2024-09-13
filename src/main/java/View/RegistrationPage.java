package View;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class RegistrationPage extends VBox {

    private Stage stage;

    public RegistrationPage(Stage stage) {
        this.stage = stage;
        setLayout();
    }

    private void setLayout() {
        // padding & spacing
        this.setPadding(new Insets(20));
        this.setSpacing(10);

        // elements
        Label titleLabel = new Label("Create an account");
        Label registeredLabel = new Label("Already registered? [INSERT LINK TO LOGIN]");

        Label usernameLabel = new Label("Username");
        TextField usernameField = new TextField();

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        Button registerButton = new Button("Register");

        // register button action logic
        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            // registration functionality here
        });

        this.getChildren().addAll(
            titleLabel,
            usernameLabel,
            usernameField,
            emailLabel,
            emailField,
            passwordLabel,
            passwordField,
            registerButton
        );
    }
}
