package core;

import backend.TraaSConnection;
import model.TrafficLight;
import model.TrafficLightState;
import model.Vehicle;
import model.VehicleState;
import service.VehicleService;
import service.TrafficLightService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SimulationController {

    private final TraaSConnection connection;
    private final VehicleService vehicleService;
    private final TrafficLightService trafficLightService;

    private final Map<String, Vehicle> vehicles = new HashMap<>();
    private final Map<String, TrafficLight> trafficLights = new HashMap<>();

    public SimulationController(TraaSConnection connection, 
                                VehicleService vehicleService,
                                TrafficLightService trafficLightService) {
        this.connection = connection;
        this.vehicleService = vehicleService;
        this.trafficLightService = trafficLightService;
    }

    public void start() throws Exception {
        connection.connect();
        initVehicles();
        refreshVehicles();
        initTrafficLights();
        refreshTrafficLights();
    }

    public void stop() {
        connection.disconnect();
        vehicles.clear();
        trafficLights.clear();
    }

    public boolean isConnected() {
        return connection.isConnected();
    }
    
    public void tick() throws Exception {
        connection.step();
        initVehicles();
        refreshVehicles(); 
        initTrafficLights();
        refreshTrafficLights();
    }

    private void initVehicles() {
        List<String> vehicleIds = vehicleService.getVehicleIds();
        Set<String> current = new HashSet<>(vehicleIds);

        for(String id : vehicleIds) {
            vehicles.putIfAbsent(id, new Vehicle(id, vehicleService));
        }

        vehicles.keySet().removeIf(id -> !current.contains(id));
    }

    private void initTrafficLights() {
        List<String> lightIds = trafficLightService.getTrafficLightIds();
        Set<String> current = new HashSet<>(lightIds);

        for(String id : lightIds) {
            trafficLights.putIfAbsent(id, new TrafficLight(id, trafficLightService));
        }

        trafficLights.keySet().removeIf(id -> !current.contains(id));
    }

    public void refreshTrafficLights() throws Exception {
        for(TrafficLight light : trafficLights.values()) {
            light.updateState();
        }
    }

    public void refreshVehicles() throws Exception {
        for(Vehicle vehicle : vehicles.values()) {
            vehicle.updatePosition();
        }
    }

    public Map<String, TrafficLightState> getTrafficLightStates() {
        Map<String, TrafficLightState> states = new HashMap<>();
        for(TrafficLight light : trafficLights.values()) {
            states.put(light.getId(), light.toTrafficLightState());
        }
        return states;
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
