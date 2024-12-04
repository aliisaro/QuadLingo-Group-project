package view;

import config.LanguageConfig;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class LoggedOutPage extends BasePage {
  private ResourceBundle bundle;

  public LoggedOutPage(Stage stage) {
    this.bundle = ResourceBundle.getBundle("bundle", LanguageConfig.getInstance().getCurrentLocale()); // Default to current locale
    setLayout(stage);
  }

  private void setLayout(Stage stage) {
    this.setPadding(new Insets(10));
    this.setAlignment(Pos.CENTER);

    // Create and configure the logged-out message label
    Label loggedOutLabel = new Label(bundle.getString("loggedOutMessage"));
    loggedOutLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

    // Create and configure the index page button
    Button indexPageButton = new Button(bundle.getString("indexPageButton"));
    indexPageButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-pref-width: 200px;");
    indexPageButton.setOnAction(e -> stage.setScene(new IndexPage(stage).createScene()));

    // Layout for buttons
    VBox buttonContainer = new VBox(10, indexPageButton);
    buttonContainer.setPadding(new Insets(10, 0, 0, 0));
    buttonContainer.setAlignment(Pos.CENTER);

    // Add elements to layout
    this.getChildren().addAll(loggedOutLabel, buttonContainer);
  }
}
