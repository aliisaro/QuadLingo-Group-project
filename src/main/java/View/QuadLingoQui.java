// QuadLingoQui.java
package View;

import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.application.Application;




//This is Qui class
//It handles the launching of the program and switching between pages
public class QuadLingoQui extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Setting the title of the window
        primaryStage.setTitle("QuadLingo");

        //Go to index page where there is login and register buttons
        primaryStage.setScene(new IndexPage(primaryStage).createScene());

        //Setting the size of the window
        primaryStage.setWidth(400);
        primaryStage.setHeight(660);

        //Centering the window
        primaryStage.centerOnScreen();

        //Showing the window
        primaryStage.show();

        // Force layout update to ensure scrollbars and other dynamic content are properly displayed
        Platform.runLater(() -> {
            primaryStage.sizeToScene(); // Adjust the stage size based on the content
        });
    }
}