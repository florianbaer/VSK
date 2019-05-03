package ch.hslu.vsk.logger.common.rmi.server;

import ch.hslu.vsk.logger.common.DTO.LogMessageDTO;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegistrationServer extends Remote {
    void register(Viewer server) throws RemoteException;
    void stop() throws RemoteException;
    void notifyViewers(LogMessageDTO message) throws RemoteException;
}
