package core;

import backend.TraaSConnection;
import model.Vehicle;
import model.VehicleState;
import service.TraaSVehicleService;
import service.VehicleService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SimulationController {

    private final TraaSConnection connection;
    private final VehicleService vehicleService;

    private final Map<String, Vehicle> vehicles = new HashMap<>();

    public SimulationController(TraaSConnection connection, VehicleService vehicleService) {
        this.connection = connection;
        this.vehicleService = vehicleService;
    }

    public void start() throws Exception {
        connection.connect();
        initVehicles();
        refreshVehicles();

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
        refreshVehicles(); 
    }

    private void initVehicles() {
        List<String> vehicleIds = vehicleService.getVehicleIds();
        Set<String> current = new HashSet<>(vehicleIds);

        for(String id : vehicleIds) {
            vehicles.putIfAbsent(id, new Vehicle(id, vehicleService));
        }

        vehicles.keySet().removeIf(id -> !current.contains(id));
    }

    public void refreshVehicles() throws Exception {
        for(Vehicle vehicle : vehicles.values()) {
            vehicle.updatePosition();
        }
    }

    public Map<String, VehicleState> getVehicleStates() {
        Map<String, VehicleState> states = new HashMap<>();
        for(Vehicle vehicle : vehicles.values()) {
            states.put(vehicle.getId(), vehicle.toVehicleState());
        }
        return states;
    }

    public Vehicle getVehicleById(String id) {
        return vehicles.get(id);
    }

}
