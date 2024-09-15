// ExamplePage2.java
package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is example page 2
//From example page 1, you can go to this page
public class ExamplePage2 extends BasePage {

    public ExamplePage2(Stage stage) {

        Button button2 = new Button("Go back to Page 1");
        button2.setOnAction(e -> stage.setScene(new ExamplePage1(stage).createScene()));
        this.getChildren().add(button2);
    }

}