package hellofx;

import backend.TraaSConnection;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.TrafficLightState;
import model.VehicleState;
import service.TraaSTrafficLightService;
import service.TraaSVehicleService;
import service.TrafficLightService;
import service.VehicleService;

import java.util.List;

public class Controller {

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

    private Canvas canvas;
    private GraphicsContext gc;

    private TraaSConnection connection;
    private VehicleService vehicleService;
    private TrafficLightService trafficLightService;

    private AnimationTimer timer;
    private boolean running = false;

    private static final double SCALE = 3.0; // SUMO-Koordinaten → Canvas-Pixel

    @FXML
    public void initialize() {
        // Canvas für deine Map
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        simulationContainer.getChildren().add(canvas);

        // SUMO-Verbindung
        String dllPath = "C:/Program Files (x86)/Eclipse/Sumo/bin/libtracijni.dll";
        String cfgPath = "C:/SumoProject/SumoConfig/demo.sumocfg";
        connection = new TraaSConnection(dllPath, cfgPath);

        // Services
        vehicleService = new TraaSVehicleService(connection);
        trafficLightService = new TraaSTrafficLightService(connection);

        setDisconnectedStatus();
        clearMap();

        // Timer für Simulation + Zeichnen
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate < 50_000_000) { // ~20 FPS
                    return;
                }
                lastUpdate = now;
                stepAndRender();
            }
        };
    }

    // Start-Button
    @FXML
    private void onStartSimulation() {
        if (running) return;
        try {
            connection.connect();
            running = true;
            timer.start();
            setConnectedStatus();
        } catch (Exception e) {
            e.printStackTrace();
            connectionStatusLabel.setText("Status: Fehler bei Verbindung");
            connectionStatusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    // Stop-Button
    @FXML
    private void onStopSimulation() {
        if (!running) return;
        running = false;
        timer.stop();
        connection.disconnect();
        setDisconnectedStatus();
    }

    // Reset-Button
    @FXML
    private void onResetSimulation() {
        onStopSimulation();
        clearMap();
    }

    @FXML
    public void toggleAdvancedSettings() {
        boolean visible = advancedSettingsBox.isVisible();
        advancedSettingsBox.setVisible(!visible);
        advancedSettingsBox.setManaged(!visible);
    }

    // Ein Tick: SUMO weiter + alles neu zeichnen
    private void stepAndRender() {
        try {
            connection.step();

            clearMap();
            drawNetwork();
            drawTrafficLights();
            drawVehicles();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearMap() {
        gc.setFill(Color.web("#27ac5d"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    // einfache, statische Straßenzeichnung (Kreuz)
    private void drawNetwork() {
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(20);

        double w = canvas.getWidth();
        double h = canvas.getHeight();

        // horizontale Straße
        gc.strokeLine(0, h / 2, w, h / 2);
        // vertikale Straße
        gc.strokeLine(w / 2, 0, w / 2, h);
    }

    private void drawVehicles() {
        List<String> ids = vehicleService.getVehicleIds();
        double maxSpeed = 0;
        double sumSpeed = 0;

        gc.setFill(Color.YELLOW);

        for (String id : ids) {
            double[] pos = vehicleService.getVehiclePos(id);
            VehicleState s = new VehicleState(id, pos[0], pos[1]);

            // SUMO-Koordinaten → Canvas
            double x = s.getX() * SCALE;
            double y = canvas.getHeight() - s.getY() * SCALE; // Y-Achse umdrehen

            gc.fillRect(x - 4, y - 4, 8, 8);

            double v = s.getSpeed() * 3.6; // m/s → km/h (falls nötig)
            sumSpeed += v;
            if (v > maxSpeed) maxSpeed = v;
        }

        if (!ids.isEmpty()) {
            double avg = sumSpeed / ids.size();
            maxSpeedLabel.setText(String.format("Max: %.1f km/h", maxSpeed));
            avgSpeedLabel.setText(String.format("Average: %.1f km/h", avg));
        } else {
            maxSpeedLabel.setText("Max: 0 km/h");
            avgSpeedLabel.setText("Average: 0 km/h");
        }
    }

    private void drawTrafficLights() {
        List<String> tlIds = trafficLightService.getTrafficLightIds();
        if (tlIds.isEmpty()) return;

        String id = tlIds.get(0); 
        String state = trafficLightService.getTrafficLightState(id);

        char first = state.charAt(0);
        Color c;
        if (first == 'G' || first == 'g') {
            c = Color.LIMEGREEN;
        } else if (first == 'r' || first == 'R') {
            c = Color.RED;
        } else {
            c = Color.GOLD;
        }

        double x = canvas.getWidth() / 2;
        double y = canvas.getHeight() / 2;

        gc.setFill(c);
        gc.fillOval(x - 10, y - 10, 20, 20);
    }

    private void setConnectedStatus() {
        connectionStatusLabel.setText("Status: Live Sumo Connection");
        connectionStatusLabel.setStyle("-fx-text-fill: green;");
    }

    private void setDisconnectedStatus() {
        connectionStatusLabel.setText("Status: No Live Sumo Connection");
        connectionStatusLabel.setStyle("-fx-text-fill: red;");
    }
}
