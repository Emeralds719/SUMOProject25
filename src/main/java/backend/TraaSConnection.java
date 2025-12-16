package backend;

import org.eclipse.sumo.libtraci.Simulation;
import org.eclipse.sumo.libtraci.StringVector;

public class TraaSConnection implements Connection{
 
    private final String dllPath;
    private final String sumoConfigPath;
    
    private boolean connection = false;
    private static boolean jniLoaded = false;

    public TraaSConnection(String dllPath, String sumoConfigPath) {
        this.dllPath = dllPath;
        this.sumoConfigPath = sumoConfigPath;
    }

    private void loadJni() {
        if (!jniLoaded) {
            System.load(dllPath);
            jniLoaded = true;
        }
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

    @Override
    public void connect() throws Exception {
        if (connection) return;

        loadJni();

        String[] cmd = {
            "sumo","-c", sumoConfigPath,"--start"
        };

        StringVector sv = new StringVector();
        for (String s : cmd) {
            sv.add(s);
        }

        Simulation.start(sv);
        connection = true;
    }
}