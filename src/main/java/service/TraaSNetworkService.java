package service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sumo.libtraci.Edge;
import org.eclipse.sumo.libtraci.Junction;
import org.eclipse.sumo.libtraci.Lane;
import org.eclipse.sumo.libtraci.StringVector;
import org.eclipse.sumo.libtraci.TraCIPosition;
import org.eclipse.sumo.libtraci.TraCIPositionVector;
import org.eclipse.sumo.libtraci.TraCPositionVector;

import backend.TraaSConnection;

public class TraaSNetworkService implements NetworkService{
    
    private final TraaSConnection connection;

    public TraaSNetworkService(TraaSConnection connection) {
        this.connection = connection;
    }

    @Override
    public List<String> getEdgeIds() {
        connectionStatus();
        StringVector ids = Edge.getIDList();

        List<String> idList = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            idList.add(ids.get(i));
        }
        return idList;
    }

    @Override
    public List<String> getAllNodes() {
        connectionStatus();
        StringVector ids = Junction.getIDList();

        List<String> idList = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            idList.add(ids.get(i));
        }
        return idList;
    }

    @Override
    public double getEdgeLength(String id) {
        connectionStatus();
        int laneCount = Edge.getLaneNumber(id);
        
        if(laneCount <= 0) {
            throw new IllegalArgumentException("Edge " + id + " has no lanes.");
        }

        String laneId = id + "_0";
        return Lane.getLength(laneId);
    }

    @Override
    public void connectionStatus() {
        if (!connection.isConnected()) {
            throw new IllegalStateException("Not connected to SUMO simulation.");
        }
    }

    @Override
    public List<double[]> getEdgeShape(String id) {
        connectionStatus();
        
        // 1. Call SUMO to get the edge shape
        TraCPositionVector shapeVector = Edge.getShape(id);
        
        List<double[]> points = new ArrayList<>();

        // 2. Iterate over the vector
        // Note: shapeVector.size() usually returns long in SWIG-generated code
        long count = shapeVector.size(); 
        for (int i = 0; i < count; i++) {
            TraCIPosition pos = shapeVector.get(i);
            points.add(new double[] { pos.getX(), pos.getY() });
        }
        
        return points;
    }
}

