// ExamplePage2.java
package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is example page 2
//From example page 1, you can go to this page
public class ExamplePage2 extends VBox {

    public ExamplePage2(Stage stage) {

        Button button1 = new Button("Go back to Page 1");
        button1.setOnAction(e -> stage.setScene(new HomePage(stage).createScene()));
        this.getChildren().add(button1);

        Button button0 = new Button("Go to Concrete Page");
        button1.setOnAction(e -> stage.setScene(new ConcretePage(stage).createScene()));
        this.getChildren().add(button0);
    }

    public Scene createScene() {
        Scene scene = new Scene(this, 400, 640);

        //Adding the css file, it can't be in the main gui class
        //This is because the ccs is applied to the scene, not the stage
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        return scene;
    }
}