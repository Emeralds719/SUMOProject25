package service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sumo.libtraci.StringVector;
import org.eclipse.sumo.libtraci.TrafficLight;

import backend.TraaSConnection;

public class TraaSTrafficLightService implements TrafficLightService{
    
    private final TraaSConnection connection;

    public TraaSTrafficLightService(TraaSConnection connection) {
        this.connection = connection;
    }

    @Override
    public List<String> getTrafficLightIds() {
        connectionStatus();
        StringVector ids = TrafficLight.getIDList();

        List<String> idList = new ArrayList<>(ids.size());
        for (int i = 0; i < ids.size(); i++) {
            idList.add(ids.get(i));
        }
        return idList;
    }

    @Override
    public String getTrafficLightState(String id) {
        connectionStatus();
        return TrafficLight.getRedYellowGreenState(id);
    }

    @Override
    public void setTrafficLightState(String id, String state) {
        connectionStatus();
        TrafficLight.setRedYellowGreenState(id, state);
    }
    
    @Override
    public String getProgram(String id) {
        connectionStatus();
        return TrafficLight.getProgram(id);
    }

    @Override
    public int getPhase(String id) {
        connectionStatus();
        return TrafficLight.getPhase(id);
    }

    @Override
    public void setPhase(String id, int phase) {
        connectionStatus();
        TrafficLight.setPhase(id, phase);
    }

    private void connectionStatus() {
        if (!connection.isConnected()) {
            throw new IllegalStateException("Not connected to SUMO simulation.");
        }
    }
}
