// ExamplePage1.java
package View;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is an example page that shows the basic functionality
//of switching between pages
public class Homepage extends BasePage implements setMarginButton{

    public Homepage(Stage stage) {


        Button achieButton = new Button("Go to Achievements");
        setMargin(achieButton, 10, 10, 10, 5);
        achieButton.setOnAction(e -> stage.setScene(new AchiePage(stage).createScene()));
        this.getChildren().add(achieButton);

        Button profileButton = new Button("Go to Profile");
        setMargin(profileButton, 10, 10, 10, 5);
        profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));
        this.getChildren().add(profileButton);
    }

    @Override
    public void setMargin(Button button, int top, int right, int bottom, int left) {
        VBox.setMargin(button, new Insets(top, right, bottom, left));
    }
}