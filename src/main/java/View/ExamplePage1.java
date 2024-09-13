// ExamplePage1.java
package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is an example page that shows the basic functionality
//of switching between pages
public class ExamplePage1 extends Page {

    public ExamplePage1(Stage stage) {

        super(stage);

        Button button2 = new Button("Go to Page 2");
        button2.setOnAction(e -> stage.setScene(new ExamplePage2(stage).createScene()));
        this.getChildren().add(button2);

        Button button0 = new Button("´Go to ConcretePage");
        button0.setOnAction(e -> stage.setScene(new ConcretePage(stage).createScene()));
        this.getChildren().add(button0);
    }
}