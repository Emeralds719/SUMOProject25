package model;

public class VehicleState {

    private String id;
    private double x;
    private double y;
    private double speed;
    private String edgeId;

    public VehicleState(String id) {
        this.id = id;
        this.x = 0;
        this.y = 0;
        this.speed = 0;
        this.edgeId = "";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    public String getEdgeId() { return edgeId; }
    public void setEdgeId(String edgeId) { this.edgeId = edgeId; }
}
