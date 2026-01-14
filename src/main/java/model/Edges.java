package model;

import java.util.List;

public class Edges {
    
    private final String id;
    private final double length;
    private final Nodes fromNode;
    private final Nodes toNode;

    public Edges(String id, double length, Nodes fromNode, Nodes toNode) {
        this.id = id;
        this.length = length;
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    public String getId() {
        return id;
    }

    public double getLength() {
        return length;
    }

    public Nodes getFromNode() {
        return fromNode;
    }

    public Nodes getToNode() {
        return toNode;
    }

}
