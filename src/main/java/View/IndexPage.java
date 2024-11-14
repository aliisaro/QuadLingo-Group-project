package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    private Button signUpPageButton;
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

        loginPageButton = new Button(bundle.getString("login"));
        loginPageButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 200px;");
        loginPageButton.setOnAction(e -> stage.setScene(new LoginPage(stage).createScene()));

        signUpPageButton = new Button(bundle.getString("signUp"));
        signUpPageButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 200px;");
        signUpPageButton.setOnAction(e -> stage.setScene(new SignUpPage(stage).createScene()));

        // Language selection ComboBox
        languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("English", "French", "Chinese", "Arabic");
        languageComboBox.setValue("English"); // Default selection
        languageComboBox.setOnAction(e -> switchLanguage(languageComboBox.getValue()));

        // Layout for buttons and language selector
        VBox buttonContainer = new VBox(10, loginPageButton, signUpPageButton, languageComboBox);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.setAlignment(Pos.CENTER);

        // Help button with custom PNG image
        Image helpImage = new Image(getClass().getResourceAsStream("/helpButton3.png"));
        ImageView helpImageView = new ImageView(helpImage);

        // Set the desired size for the ImageView
        helpImageView.setFitWidth(50); // Set the desired width
        helpImageView.setFitHeight(50); // Set the desired height

        Button helpButton = new Button();
        helpButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); // Transparent background
        helpButton.setGraphic(helpImageView);
        helpButton.setId("helpButton");
        helpButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("help"));
            alert.setHeaderText(null);

            // Create a Text node to wrap the content text
            Text content = new Text(bundle.getString("helpIndex"));
            content.setWrappingWidth(400); // Set the desired wrapping width

            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        });

        // Add elements to layout
        this.getChildren().addAll(welcomeLabel, descriptionLabel, buttonContainer, helpButton);
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
        loginPageButton.setText(bundle.getString("login")); // Make sure this key is correct
        signUpPageButton.setText(bundle.getString("signUp"));
    }
}
