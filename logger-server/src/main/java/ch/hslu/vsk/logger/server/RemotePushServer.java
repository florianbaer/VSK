package ch.hslu.vsk.logger.server;

import ch.hslu.vsk.logger.common.rmi.server.PushServer;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RemotePushServer implements Runnable, PushServer {

    private List<Viewer> viewers = new ArrayList<>();

    public RemotePushServer(){
        super();
    }

    @Override
    public void run() {

        try {
            System.setProperty("java.rmi.server.hostname","localhost");
            PushServer pushServer = new RemotePushServer();
            Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
            PushServer stub = (PushServer) UnicastRemoteObject.exportObject(pushServer, 0);
            registry.bind("logpushserver", stub);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void register(final Viewer server) throws RemoteException {
        System.out.println("register viewer...");
        this.viewers.add(server);
    }

    @Override
    public void unregister(Viewer server) throws RemoteException {
        System.out.println("unregister viewer...");
        this.viewers.remove(server);
    }

    @Override
    public List<Viewer> getAllViewers() {
        return this.viewers;
    }
}
