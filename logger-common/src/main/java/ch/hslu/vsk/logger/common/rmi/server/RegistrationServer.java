package ch.hslu.vsk.logger.common.rmi.server;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegistrationServer extends Remote {
    void register(Viewer server) throws RemoteException;
    void unregister(Viewer server) throws RemoteException;
    void notifyViewers(LogMessage message) throws RemoteException;
}
