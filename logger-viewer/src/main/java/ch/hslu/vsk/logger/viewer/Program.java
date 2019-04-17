package ch.hslu.vsk.logger.viewer;

import ch.hslu.vsk.logger.common.rmi.server.RegistrationServer;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The Class which starts the program.
 */
public final class Program {

    private Remote handler;
    private RegistrationServer registration;

    /**
     * The private constructor for the program.
     */
    public Program() {
    }

    /**
     * The main method.
     *
     * @param args The main method arguments.
     */
    public static void main(final String[] args) {
        Program program = new Program();
        program.run();
    }

    private void run() {
        try {
            Viewer viewer = new LogViewer();
            this.registerViewer(viewer);
            //Thread.sleep(20000);
            //this.unregisterViewer(viewer);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void registerViewer(final Viewer model) throws Exception {
        String host = "localhost";

        //System.setProperty("java.rmi.server.codebase", "http://localhost:8080/");

        final Registry reg = LocateRegistry.getRegistry(host, Registry.REGISTRY_PORT);
        this.registration = (RegistrationServer) reg.lookup("logpushserver");
        this.handler = UnicastRemoteObject.exportObject(model, 0);
        registration.register((Viewer) this.handler);
    }

    private void unregisterViewer(final Viewer model) throws Exception {
        registration.unregister(model);
    }
}


