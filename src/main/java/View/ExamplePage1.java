// ExamplePage1.java
package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is an example page that shows the basic functionality
//of switching between pages
public class ExamplePage1 extends VBox {

    public ExamplePage1(Stage stage) {

        Button button1 = new Button("Go to Page 2");
        button1.setOnAction(e -> stage.setScene(new ExamplePage2(stage).createScene()));
        this.getChildren().add(button1);
    }

    public Scene createScene() {
        Scene scene = new Scene(this, 400, 640);

        //Adding the css file, it can't be in the main qui class
        //This is because the ccs is applied to the scene, not the stage
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        return scene;
    }
}