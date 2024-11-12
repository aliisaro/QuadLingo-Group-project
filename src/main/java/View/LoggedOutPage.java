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
    private Button indexPageButton;

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

        indexPageButton = new Button(bundle.getString("indexPageButton"));
        indexPageButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 200px;");
        indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

        // Layout for buttons and language selector
        VBox buttonContainer = new VBox(10, indexPageButton);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        buttonContainer.setAlignment(Pos.CENTER);

        // Add elements to layout
        this.getChildren().addAll(loggedOutLabel, buttonContainer);
    }


    private void updateTexts() {
        loggedOutLabel.setText(bundle.getString("welcomeMessage"));
        // descriptionLabel.setText(bundle.getString("descriptionMessage"));
        indexPageButton.setText(bundle.getString("indexPageButton")); // Make sure this key is correct
    }
}
