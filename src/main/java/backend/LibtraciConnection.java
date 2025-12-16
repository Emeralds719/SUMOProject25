package backend;

import org.eclipse.sumo.libtraci.Simulation;
import org.eclipse.sumo.libtraci.StringVector;

public class LibtraciConnection implements Connection{

    private final String dllPath;
    private final String sumoConfigPath;

    private boolean connected = false;
    private static boolean jniLoaded = false;

    public LibtraciConnection(String dllPath, String sumoConfigPath) {
        this.dllPath = dllPath;
        this.sumoConfigPath = sumoConfigPath;
    }

    private void loadJni() {
        if (!jniLoaded) {
            System.load(dllPath);
            jniLoaded = true;
        }
    }

    @Override
    public void connect() throws Exception {
        if (connected) return;

        loadJni();

        String[] cmd = {
            "sumo","-c", sumoConfigPath,"--start"
        };

        StringVector sv = new StringVector();
        for (String s : cmd) {
            sv.add(s);
        }

        Simulation.start(sv);
        connected = true;
    }

    @Override
    public void disconnect() {
        if (!connected) return;
        Simulation.close();
        connected = false;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void stepSimulation() throws Exception {
        if (!connected) return;
        Simulation.step();
    }
}
