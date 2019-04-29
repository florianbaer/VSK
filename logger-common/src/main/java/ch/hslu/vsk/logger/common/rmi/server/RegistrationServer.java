package ch.hslu.vsk.logger.common.rmi.server;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Instant;

public interface RegistrationServer extends Remote {
    void register(Viewer server) throws RemoteException;
    void unregister(Viewer server) throws RemoteException;
    void notifyViewers(Instant instant, LogMessage message) throws RemoteException;
}
