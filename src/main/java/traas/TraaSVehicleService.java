package traas;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sumo.libtraci.StringVector;
import org.eclipse.sumo.libtraci.TraCIPosition;
import org.eclipse.sumo.libtraci.Vehicle;

import service.TraaSConnection;

public class TraaSVehicleService {
    private final TraaSConnection connection;

    public TraaSVehicleService(TraaSConnection connection) {
        this.connection = connection;
    }

    public List<String> getVehicleIds() {
        connectionStatus();
        StringVector ids = Vehicle.getIDList();
        
        List<String> idList = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            idList.add(ids.get(i));
        }
        return idList;
    }

    public double getVehicleSpeed(String id) {
        connectionStatus();
        return Vehicle.getSpeed(id);
    }

    public void setVehicleSpeed(String id, double speed) {
        connectionStatus();
        Vehicle.setSpeed(id, speed);
    }

    public TraCIPosition getVehiclePos(String id) {
        connectionStatus();
        return Vehicle.getPosition(id);
    }


    public void addVehicle(String id, String routeId) {
        connectionStatus();
        Vehicle.add(id, routeId);
    }

    private void connectionStatus() {
        if(!connection.isConnected()) {
            throw new IllegalStateException("Not connected to SUMO simulation.");
        }
    }
}
