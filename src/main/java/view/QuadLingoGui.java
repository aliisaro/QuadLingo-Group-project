package view;

import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.application.Application;

// This is GUI class
// It handles the launching of the program and switching between pages
public class QuadLingoGui extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Setting the title of the window
        primaryStage.setTitle("QuadLingo");

        // Go to index page where there is login and register buttons
        primaryStage.setScene(new IndexPage(primaryStage).createScene());

        // Setting the size of the window
        primaryStage.setWidth(400);
        primaryStage.setHeight(660);

        // Centering the window
        primaryStage.centerOnScreen();

        // Showing the window
        primaryStage.show();

        // Force layout update to ensure scrollbars and other dynamic content are properly displayed
        Platform.runLater(primaryStage::sizeToScene); // Use method reference
    }
}
