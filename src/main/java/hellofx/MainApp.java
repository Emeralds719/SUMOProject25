package hellofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 700);
        stage.setScene(scene);
        stage.setTitle("Real-Time Traffic Simulation");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
