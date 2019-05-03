package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.DTO.LogMessageDTO;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.rmi.server.RegistrationServer;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RemotePushServer implements RegistrationServer {

    private List<Viewer> viewers = new ArrayList<>();
    private static Registry registry;

    private static RegistrationServer instance;
    private static RegistrationServer pushServer;

    public RemotePushServer(){
        super();
    }

    public static RegistrationServer getInstance() {

        if (instance == null) {
            try {
                System.setProperty("java.rmi.server.hostname","localhost");
                pushServer = new RemotePushServer();
                registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
                instance = (RegistrationServer) UnicastRemoteObject.exportObject(pushServer, 0);
                registry.bind("logpushserver", instance);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return instance;
    }

    @Override
    public void register(final Viewer server) throws RemoteException {
        System.out.println("register viewer...");
        this.viewers.add(server);
    }



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

    @Override
    public void stop() throws RemoteException
    {
        try{
            // Unregister ourself
            registry.unbind("logpushserver");

            // Unexport; this will also remove us from the RMI runtime
            UnicastRemoteObject.unexportObject(pushServer, true);

            System.out.println("Stopping LogPushServer exiting.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
