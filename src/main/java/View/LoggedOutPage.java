package View;

import Config.LanguageConfig;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoggedOutPage extends BasePage {
    private ResourceBundle bundle;
    private Label loggedOutLabel;

    // private Label descriptionLabel;
    private Button loginPageButton;
    private Button signUpPageButton;

    public LoggedOutPage(Stage stage) {
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale()); // Default to current locale
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);

        // Labels and Buttons
        loggedOutLabel = new Label(bundle.getString("loggedOutMessage"));
        loggedOutLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // descriptionLabel = new Label(bundle.getString("descriptionMessage"));
        // descriptionLabel.setStyle("-fx-font-size: 16px;");

        loginPageButton = new Button(bundle.getString("login"));
        loginPageButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 200px;");
        loginPageButton.setOnAction(e -> stage.setScene(new LoginPage(stage).createScene()));

        signUpPageButton = new Button(bundle.getString("signUp"));
        signUpPageButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 200px;");
        signUpPageButton.setOnAction(e -> stage.setScene(new SignUpPage(stage).createScene()));

        // Layout for buttons and language selector
        VBox buttonContainer = new VBox(10, loginPageButton, signUpPageButton);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.setAlignment(Pos.CENTER);

        // Add elements to layout
        this.getChildren().addAll(loggedOutLabel, buttonContainer);
    }

    /*
    private void switchLanguage(String language) {
        Locale locale;
        switch (language) {
            case "French":
                locale = new Locale("fr");
                break;
            case "Chinese":
                locale = new Locale("ch"); // Simplified Chinese
                break;
            case "Arabic":
                locale = new Locale("ar");
                break;
            default:
                locale = Locale.ENGLISH;
                break;
        }

        // Update the global locale
        LanguageConfig.getInstance().setCurrentLocale(locale);

        // Load the ResourceBundle with the selected locale
        bundle = ResourceBundle.getBundle("bundle", locale);
        updateTexts(); // Update UI texts
    }*/

    private void updateTexts() {
        loggedOutLabel.setText(bundle.getString("welcomeMessage"));
        // descriptionLabel.setText(bundle.getString("descriptionMessage"));
        loginPageButton.setText(bundle.getString("login")); // Make sure this key is correct
        signUpPageButton.setText(bundle.getString("signUp"));
    }
}
