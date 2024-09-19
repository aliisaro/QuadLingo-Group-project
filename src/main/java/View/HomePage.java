// ExamplePage1.java
package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is an example page that shows the basic functionality
//of switching between pages
public class HomePage {

    private Stage stage;

    public HomePage(Stage stage) {
        this.stage = stage;  // Pass in the primary stage from QuadLingoGUI
    }

    public Scene createScene() {
        VBox layout = new VBox();
        // Add UI components to the layout here
        // layout.getChildren().add(/* some UI elements */);

        Scene scene = new Scene(layout, 400, 640);
        return scene;
    }
}