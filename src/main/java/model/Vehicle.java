package model;

import service.VehicleService;

public class Vehicle {
    private final String id;
    private final VehicleService vehicleService;

    private double speed;
    private double x;
    private double y;

    public Vehicle(String id, VehicleService vehicleService) {
        this.id = id;
        this.vehicleService = vehicleService;
    }

    public String getId() {
        return id;
    }

    public double getSpeed() {
        return speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void getY(double y) {
        this.y = y;
    }

        public void setSpeed(double speed) throws Exception{
        vehicleService.setVehicleSpeed(id, speed);
        this.speed = speed;
    }

    public void updatePosition() throws Exception {
        this.speed = vehicleService.getVehicleSpeed(id);
        double[] pos = vehicleService.getVehiclePos(id);
        this.x = pos[0];
        this.y = pos[1];
    }

    public VehicleState toVehicleState() {
        VehicleState state = new VehicleState(id);
        state.setSpeed(this.speed);
        state.setX(this.x);
        state.setY(this.y);
        return state;
    }
    
}
