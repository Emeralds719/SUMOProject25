package backend;

public interface Connection {
    void connect() throws Exception;
    void disconnect();
    boolean isConnected();
    void step() throws Exception;
}
