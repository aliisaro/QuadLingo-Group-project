// QuadLingoQui.java
package View;

import javafx.stage.Stage;
import javafx.application.Application;




//This is Qui class
//It handles the launching of the program and switching between pages
public class QuadLingoQui extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("QuadLingo");
        primaryStage.setScene(new ExamplePage1(primaryStage).createScene());
        //Example page 1 is the first page that is shown, can be changed later


        //Setting the size of the window
        primaryStage.setWidth(400);
        primaryStage.setHeight(640);

        //Centering the window
        primaryStage.centerOnScreen();

        //Showing the window
        primaryStage.show();
    }
}