package service;
import model.VehicleState;
import java.util.List;

public interface VehicleService {
    public List<String> getVehicleIds();
    public double getVehicleSpeed(String id);
    public void setVehicleSpeed(String id, double speed);
    public double[] getVehiclePos(String id);
    public void addVehicle(String id, String routeId);
}
