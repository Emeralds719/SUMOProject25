package model;

import service.TrafficLightService;

public class TrafficLight {
    
    private final String id;
    private final TrafficLightService service;

    public String state;

    public TrafficLight(String id, TrafficLightService service) {
        this.id = id;
        this.service = service;
        this.state = "";
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public void updateState() throws Exception {
        this.state = service.getTrafficLightState(id);
    }

    public void setState(String state) throws Exception {
        service.setTrafficLightState(id, state);
        this.state = state;
    }

    public TrafficLightState toTrafficLightState() {
        TrafficLightState lightState = new TrafficLightState(id,  state);
        lightState.setState(this.state);
        return lightState;
    }
}
