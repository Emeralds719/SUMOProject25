package model;

public class TrafficLightState {
    private final String id;
    private String state;

    public TrafficLightState(String id, String state) {
        this.id = id;
        this.state = state;
    }

    public String getId() { return id; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}
