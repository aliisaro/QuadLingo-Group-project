// ExamplePage1.java
package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is an example page that shows the basic functionality
//of switching between pages
public class ExamplePage1 extends BasePage {

    public ExamplePage1(Stage stage) {

        Button button1 = new Button("Go to Page 2");
        button1.setOnAction(e -> stage.setScene(new ExamplePage2(stage).createScene()));
        this.getChildren().add(button1);

        Button achieButton = new Button("Go to Achievements");
        achieButton.setOnAction(e -> stage.setScene(new AchiePage(stage).createScene()));
        this.getChildren().add(achieButton);

        Button profileButton = new Button("Go to Profile");
        profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));
        this.getChildren().add(profileButton);
    }
}