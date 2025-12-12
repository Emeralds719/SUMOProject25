package hellofx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;

public class Controller {

    @FXML
    private Label label;

    @FXML
    private VBox simulationContainer;

    @FXML
    private Label maxSpeedLabel;

    @FXML
    private Label avgSpeedLabel;

    @FXML
    private Label connectionStatusLabel;

    @FXML
    private VBox advancedSettingsBox;

    @FXML
    private Button settingsButton;   

    public void initialize() {

        Canvas canvas = new Canvas(380, 580);
        simulationContainer.getChildren().add(canvas);

        
        connectionStatusLabel.setText("Status: No Live Sumo Connection");
        connectionStatusLabel.setStyle("-fx-text-fill: red;");

        connectionStatusLabel.setText("Status: Live Sumo Connection");
        connectionStatusLabel.setStyle("-fx-text-fill: green;");
        }

        public void toggleAdvancedSettings() {
               boolean isVisible = advancedSettingsBox.isVisible();
               advancedSettingsBox.setVisible(!isVisible);
               advancedSettingsBox.setManaged(!isVisible); }
}
