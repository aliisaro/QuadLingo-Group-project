package View;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public abstract class BasePage extends VBox {

    public Scene createScene() {
        Scene scene = new Scene(this, 400, 640);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        return scene;
    }
}