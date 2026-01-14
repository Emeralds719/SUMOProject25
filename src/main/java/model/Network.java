package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service.NetworkService;

public class Network {

    private final NetworkService service;

    private final List<Nodes> nodes = new ArrayList<>();
    private final List<Edges> edges = new ArrayList<>();
    private final Map<String, Nodes> nodeById = new HashMap<>();

    public Network(NetworkService service) {
        this.service = service;
    }

    public List<Nodes> getNodes() {
        return nodes;
    }

    public List<Edges> getEdges() {
        return edges;
    }

    public void loadFromSumo() throws Exception {
        nodes.clear();
        edges.clear();
        nodeById.clear();

        for (String nodeId : service.getAllNodes()) {
            Nodes node = new Nodes(nodeId);
            nodes.add(node);
            nodeById.put(nodeId, node);
        }

        for(String edgeId : service.getEdgeIds()) {
            double length = service.getEdgeLength(edgeId);
            
            String fromId = service.getEdgeFromNode(edgeId);
            String toId = service.getEdgeToNode(edgeId);
            
            Nodes fromNode = nodeById.get(fromId);
            Nodes toNode = nodeById.get(toId);
            Edges edge = new Edges(edgeId, length, fromNode, toNode);
            edges.add(edge);
        }
        
    }


    
}
