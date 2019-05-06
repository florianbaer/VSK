package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.DTO.LogMessageDTO;
import ch.hslu.vsk.logger.common.rmi.server.RegistrationServer;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements a service to push notifications the the viewers.
 */
public class RemotePushService implements RegistrationServer {

    private List<Viewer> viewers = new ArrayList<>();
    private static Registry registry;

    private static RegistrationServer instance;
    private static RegistrationServer pushServer;

    /**
     * Private constructor.
     */
    private RemotePushService() {
        super();
    }

    /**
     * Gets the instance of the remote push service.
     * @return The instance.
     */
    public static RegistrationServer getInstance() {

        if (instance == null) {
            try {
                 System.setProperty("java.security.policy", "server_rules.policy");
                 if (System.getSecurityManager() == null) {
                    System.setSecurityManager(new SecurityManager());
                 }
                pushServer = new RemotePushService();
                registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
                instance = (RegistrationServer) UnicastRemoteObject.exportObject(pushServer, 0);
                registry.bind("logpushserver", instance);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * Registers a viewer.
     * @param viewer a viewer to register.
     */
    @Override
    public void register(final Viewer viewer) {
        System.out.println("register viewer...");
        this.viewers.add(viewer);
    }

    /**
     * Notifies all the viewers.
     * @param message The LogMessage to send to the viewers.
     */
    @Override
    public void notifyViewers(final LogMessageDTO message) {
        synchronized (this) {
            this.viewers.stream().forEach(x -> {
                try {
                    x.sendLogMessage(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Stops the RMI registry.
     */
    @Override
    public void stop() {
        try {
            // Unregister ourself
            registry.unbind("logpushserver");

            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(pushServer, true);

            System.out.println("Stopping LogPushServer exiting.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
