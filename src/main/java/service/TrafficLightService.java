package service;
import java.util.List;

public interface TrafficLightService {

    public List<String> getTrafficLightIds();
    public String getTrafficLightState(String id);
    public void setTrafficLightState(String id, String state);
    public int getPhase(String id);
    public void setPhase(String id, int phaseId);
    public String getProgram(String id);
}
