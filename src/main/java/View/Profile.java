package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Page for user profile
public class Profile extends VBox {

    public Profile(Stage stage) {
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
        backButton.setOnAction(e -> stage.setScene(new ExamplePage1(stage).createScene()));

        // Layout setup
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
        this.setSpacing(10);
        this.getChildren().addAll(nameBox, emailBox, saveButton, backButton);
    }

    private void handleSaveAction(String name, String email) {
        // Logic to handle saving the profile information
        System.out.println("Profile saved:");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
    }

    public Scene createScene() {
        Scene scene = new Scene(this, 400, 640);

        // Adding the CSS file
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        return scene;
    }
}
