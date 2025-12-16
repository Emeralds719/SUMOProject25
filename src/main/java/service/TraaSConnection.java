package service;

import org.eclipse.sumo.libtraci.Simulation;
import org.eclipse.sumo.libtraci.StringVector;

public class TraaSConnection {
 
    private boolean connection = false;

    public TraaSConnection() {
    }

    public void connect(String configPath, boolean gui) {
        if(connection) return;

        System.loadLibrary("libtracijni");

        String binary = gui ? "sumo-gui" : "sumo";
        String[] cmd = { binary, "-c", configPath, "--start" };

        StringVector sv = new StringVector();
        for (String s : cmd) {
            sv.add(s);
        }

        Simulation.start(sv);
        connection = true;
    }

    public void disconnect() {
        if(!connection) return;
        Simulation.close();
        connection = false;
    }

    public boolean isConnected() {
        return connection;
    }

    public void step() {
        if(!connection)
            throw new IllegalStateException("Not connected to SUMO simulation.");
        Simulation.step();
    }
}