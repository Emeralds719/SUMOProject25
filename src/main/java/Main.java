import org.eclipse.sumo.libtraci.Simulation;
import org.eclipse.sumo.libtraci.Vehicle;
import org.eclipse.sumo.libtraci.StringVector;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        // Load the JNI DLL
        System.load("C:/Program Files (x86)/Eclipse/Sumo/bin/libtracijni.dll");
        System.out.println("Loaded libtracijni.dll OK");

        // Config file (relative to cwd = SumoProject)
        String cfg = "net/hello.sumocfg";

        File cfgFile = new File(cfg);
        System.out.println("Using config: " + cfgFile.getAbsolutePath());
        if (!cfgFile.exists()) {
            System.err.println("ERROR: Config file not found!");
            return;
        }

        // Command - call sumo-gui directly (no spaces issue)
        String[] cmd = { "sumo-gui", "-c", cfg, "--start" };
        System.out.println(java.util.Arrays.toString(cmd));

        // Build SWIG StringVector
        StringVector sv = new StringVector();
        for (String s : cmd) sv.add(s);

        try {
            Simulation.start(sv);
            for (int step = 0; step < 100; step++) {
                Simulation.step();
                StringVector ids = Vehicle.getIDList();
                for (int i = 0; i < ids.size(); i++) {
                    String id = ids.get(i);
                    System.out.println("Vehicle " + id + " speed: " + Vehicle.getSpeed(id));
                }
            }
        } finally {
            Simulation.close();
            System.out.println("Simulation closed.");
        }
    }
}
