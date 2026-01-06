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

public class TraaSNetworkService implements NetworkService {
    
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
        if(laneCount <= 0) return 0.0;
        return Lane.getLength(id + "_0");
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
        List<double[]> points = new ArrayList<>();

        try {
            String laneId = id + "_0";
            
            TraCIPositionVector wrapper = Lane.getShape(laneId);
            
            TraCPositionVector vector = wrapper.getValue();
            
            for (int i = 0; i < vector.size(); i++) {
                TraCIPosition pos = vector.get(i);
                points.add(new double[]{pos.getX(), pos.getY()});
            }
        } catch (Exception e) {
            System.err.println("Could not get shape for edge " + id);
        }
        return points;
    }

    @Override
    public double[] getJunctionPosition(String id) {
        if (!connection.isConnected()){
            return new double[]{0, 0};
        }
        
        try{
            var pos = Junction.getPosition(id);
            return new double[]{pos.getX(), pos.getY()};
        } catch (Exception e){
            return new double[]{0, 0};
        }
    }

}