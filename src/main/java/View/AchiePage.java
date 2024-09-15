package View;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AchiePage extends BasePage {

    Image image = new Image("file:src/main/resources/BadgePaceHolder.png");
    ImageView imageView = new ImageView(image);

    public AchiePage(Stage stage) {
        Button button1 = new Button("Go to Page 2");
        button1.setOnAction(e -> stage.setScene(new ExamplePage2(stage).createScene()));

        Button profileButton = new Button("Go to Profile");
        profileButton.setOnAction(e -> stage.setScene(new Profile(stage).createScene()));

        Button buttonHome = new Button("Home");
        buttonHome.setOnAction(e -> stage.setScene(new ExamplePage1(stage).createScene()));


        this.getChildren().add(button1);
        this.getChildren().add(profileButton);
        this.getChildren().add(imageView);
    }
}