package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Page extends VBox {

    public final int WIDTH = 400;
    public final int HEIGHT = 640;

    public Page(Stage stage) {
    }

    public Scene createScene() {
        Scene scene = new Scene(this, 400, 640);

        //Adding the css file, it can't be in the main qui class
        //This is because the ccs is applied to the scene, not the stage
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        return scene;
    }
}


