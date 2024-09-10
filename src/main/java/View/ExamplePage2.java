// ExamplePage2.java
package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is example page 2
//From example page 1, you can go to this page
public class ExamplePage2 extends VBox {

    public ExamplePage2(Stage stage) {

        Button button2 = new Button("Go back to Page 1");
        button2.setOnAction(e -> stage.setScene(new ExamplePage1(stage).createScene()));
        this.getChildren().add(button2);
    }

    public Scene createScene() {
        Scene scene = new Scene(this, 400, 640);

        //Adding the css file, it can't be in the main qui class
        //This is because the ccs is applied to the scene, not the stage
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        return scene;
    }
}