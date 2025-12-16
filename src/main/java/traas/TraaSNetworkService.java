package traas;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sumo.libtraci.Edge;
import org.eclipse.sumo.libtraci.Junction;
import org.eclipse.sumo.libtraci.Lane;
import org.eclipse.sumo.libtraci.StringVector;

import service.TraaSConnection;

public class TraaSNetworkService {
    
    private final TraaSConnection connection;

    public TraaSNetworkService(TraaSConnection connection) {
        this.connection = connection;
    }

    public List<String> getEdgeIds() {
        connectionStatus();
        StringVector ids = org.eclipse.sumo.libtraci.Edge.getIDList();

        List<String> idList = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            idList.add(ids.get(i));
        }
        return idList;
    }

    public List<String> getAllNodes() {
        connectionStatus();
        StringVector ids = Junction.getIDList();

        List<String> idList = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            idList.add(ids.get(i));
        }
        return idList;
    }

    public double getEdgeLength(String id) {
        connectionStatus();
        int laneCount = Edge.getLaneNumber(id);
        
        if(laneCount <= 0) {
            throw new IllegalArgumentException("Edge " + id + " has no lanes.");
        }

        String laneId = id + "_0";
        return Lane.getLength(laneId);
    }

    public void connectionStatus() {
        if (!connection.isConnected()) {
            throw new IllegalStateException("Not connected to SUMO simulation.");
        }
    }
}
