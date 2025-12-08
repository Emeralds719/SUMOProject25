package hellofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import javafx.scene.control.Button;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.StackPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        
        Parent root = FXMLLoader.load(getClass().getResource("hellofx.fxml"));

        primaryStage.setTitle("Traffic Simulation Map");
        primaryStage.setScene(new Scene(root, 800, 620));
        primaryStage.show(); // }
    }


    public static void main(String[] args) {
        launch(args);
    }
}