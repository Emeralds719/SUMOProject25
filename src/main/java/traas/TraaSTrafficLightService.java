package traas;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sumo.libtraci.StringVector;
import org.eclipse.sumo.libtraci.TrafficLight;

import service.TraaSConnection;

public class TraaSTrafficLightService {
    
    private final TraaSConnection connection;

    public TraaSTrafficLightService(TraaSConnection connection) {
        this.connection = connection;
    }

    public List<String> getTrafficLightIds() {
        connectionStatus();
        StringVector ids = TrafficLight.getIDList();

        List<String> idList = new ArrayList<>();
        connectionStatus();
        return idList;
    }

    public String getTrafficLightState(String id) {
        connectionStatus();
        return TrafficLight.getRedYellowGreenState(id);
    }

    public void setTrafficLightState(String id, String state) {
        connectionStatus();
        TrafficLight.setRedYellowGreenState(id, state);
    }
    
    public String getProgram(String id) {
        connectionStatus();
        return TrafficLight.getProgram(id);
    }
    
    public void connectionStatus() {
        if (!connection.isConnected()) {
            throw new IllegalStateException("Not connected to SUMO simulation.");
        }
    }
}
