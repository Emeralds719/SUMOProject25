package core;

import backend.TraaSConnection;
import model.VehicleState;
import service.TraaSVehicleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SimulationController {

    private final TraaSConnection connection;
    private final TraaSVehicleService vehicleService;

    private final Map<String, VehicleState> vehicles = new HashMap<>();

    public SimulationController(TraaSConnection connection, TraaSVehicleService vehicleService) {
        this.connection = connection;
        this.vehicleService = vehicleService;
    }

    public void start() throws Exception {
        connection.connect();
        initVehicles();
    }

    public void stop() {
        connection.disconnect();
        vehicles.clear();
    }

    public boolean isConnected() {
        return connection.isConnected();
    }
    
    public void tick() throws Exception {
        connection.step();
        initVehicles(); 
    }

    private void initVehicles() {
        List<String> ids = vehicleService.getVehicleIds();
        vehicles.clear();

        for (String id : ids) {
            VehicleState v = new VehicleState(id);
            double speed = vehicleService.getVehicleSpeed(id);
            double[] pos = vehicleService.getVehiclePos(id);

            v.setSpeed(speed);
            v.setX(pos[0]);
            v.setY(pos[1]);

            vehicles.put(id, v);
        }
    }

    public Map<String, VehicleState> getVehicles() {
        return vehicles;
    }
}
