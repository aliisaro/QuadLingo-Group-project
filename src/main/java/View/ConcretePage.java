package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConcretePage extends Page {
    public ConcretePage (Stage stage) {

        super(stage);

        Button button1 = new Button("Go to Page 1");
        button1.setOnAction(e -> stage.setScene(new ExamplePage1(stage).createScene()));
        this.getChildren().add(button1);

        Button button2 = new Button("Go to Page 2");
        button2.setOnAction(e -> stage.setScene(new ExamplePage2(stage).createScene()));
        this.getChildren().add(button2);
    }
}