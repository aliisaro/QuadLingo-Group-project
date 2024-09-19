// QuadLingoGUI.java
package View;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;


// This is GUI class
// It handles the launching of the program and switching between pages

public class QuadLingoGUI extends Application{

    public final int WIDTH = 400;
    public final int HEIGHT = 640;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("QuadLingo");
        primaryStage.setScene(new HomePage(primaryStage).createScene());


        // Setting the size of the window
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);

        // Centering the window
        primaryStage.centerOnScreen();

        // Showing the window
        primaryStage.show();
    }
}