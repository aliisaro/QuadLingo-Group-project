package View;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public abstract class BasePage extends VBox {

    public Scene createScene() {
        // Add enough content to make the scroll bar appear
        for (int i = 0; i < 5; i++) {
            this.getChildren().add(new javafx.scene.control.Label(""));
        }

        this.getChildren().add(new javafx.scene.control.Label("If the page has enough content, you can scroll it!"));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: #D6E3F8; -fx-background-color: #584B53;");

        // Adjust scroll sensitivity
        scrollPane.setVvalue(0.7); // Set initial vertical scroll position (0.0 to 1.0)
        scrollPane.setHvalue(0.7); // Set initial horizontal scroll position (0.0 to 1.0)


        Scene scene = new Scene(scrollPane,640, 640);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        return scene;
    }
}