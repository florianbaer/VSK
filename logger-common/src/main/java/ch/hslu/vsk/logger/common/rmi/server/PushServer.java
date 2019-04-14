package ch.hslu.vsk.logger.common.rmi.server;

import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PushServer extends Remote {
    void register(Viewer server) throws RemoteException;
    void unregister(Viewer server) throws RemoteException;
    List<Viewer> getAllViewers() throws RemoteException;
}
