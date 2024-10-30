package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.util.Locale;
import java.util.ResourceBundle;
import Config.LanguageConfig;

public class IndexPage extends BasePage {
    private ResourceBundle bundle;
    private Label welcomeLabel;
    private Label descriptionLabel;
    private Button loginPageButton;
    private Button registerPageButton;
    private ComboBox<String> languageComboBox;

    public IndexPage(Stage stage) {
        this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale()); // Default to current locale
        setLayout(stage);
    }

    private void setLayout(Stage stage) {
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);

        // Labels and Buttons
        welcomeLabel = new Label(bundle.getString("welcomeMessage"));
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        descriptionLabel = new Label(bundle.getString("descriptionMessage"));
        descriptionLabel.setStyle("-fx-font-size: 16px;");

        loginPageButton = new Button(bundle.getString("gotToLoginButton"));
        loginPageButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 200px;");
        loginPageButton.setOnAction(e -> stage.setScene(new LoginPage(stage).createScene()));

        registerPageButton = new Button(bundle.getString("registerButton"));
        registerPageButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 200px;");
        registerPageButton.setOnAction(e -> stage.setScene(new RegistrationPage(stage).createScene()));

        // Language selection ComboBox
        languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("English", "French", "Chinese", "Arabic");
        languageComboBox.setValue("English"); // Default selection
        languageComboBox.setOnAction(e -> switchLanguage(languageComboBox.getValue()));

        // Layout for buttons and language selector
        VBox buttonContainer = new VBox(10, loginPageButton, registerPageButton, languageComboBox);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.setAlignment(Pos.CENTER);

        // Add elements to layout
        this.getChildren().addAll(welcomeLabel, descriptionLabel, buttonContainer);
    }

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
    }

    private void updateTexts() {
        welcomeLabel.setText(bundle.getString("welcomeMessage"));
        descriptionLabel.setText(bundle.getString("descriptionMessage"));
        loginPageButton.setText(bundle.getString("gotToLoginButton")); // Make sure this key is correct
        registerPageButton.setText(bundle.getString("registerButton"));
    }
}
