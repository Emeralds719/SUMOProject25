package service;

import java.util.List;

public interface NetworkService {
    public List<String> getEdgeIds();
    public List<String> getAllNodes();
    public double getEdgeLength(String id);
    String getEdgeFromNode(String id);
    String getEdgeToNode(String id);
    List<double[]> getEdgeShape(String id);
    double[] getJunctionPosition(String id);
}
