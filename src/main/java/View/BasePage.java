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


        // Adjust scroll sensitivity
        scrollPane.setVvalue(0.0); // Set initial vertical scroll position
        scrollPane.setHvalue(0.7); // Set initial horizontal scroll position (0.0 to 1.0)

        // Set the style of the scroll pane
        scrollPane.setStyle(
                "-fx-background: #D6E3F8;" +
                        "-fx-background-color: #a5adbf;" +
                        "-fx-border-color: #a5adbf;" +
                        "-fx-border-width: 4px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-vbar-policy: always;" +
                        "-fx-control-inner-background: #FFFFFF;" +
                        "-fx-control-inner-background-alt: #F0F0F0;" +
                        "-fx-control-inner-background-inactive: #E0E0E0;"
        );

        Scene scene = new Scene(scrollPane,400, 660);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        return scene;
    }
}