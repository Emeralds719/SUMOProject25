package backend;

public interface Connection {
    void connect() throws Exception;
    void disconnect();
    boolean isConnected();
    void stepSimulation() throws Exception;

}
