package ch.hslu.vsk.logger.common.rmi.server;

import ch.hslu.vsk.logger.common.DTO.LogMessageDTO;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfacer for the registration of viewers using rmi.
 */
public interface RegistrationServer extends Remote {

    /**
     * Method to register a viewer.
     * @param server to register
     * @throws RemoteException that can be thrown
     */
    void register(Viewer server) throws RemoteException;

    /**
     * Stops the rmi registry.
     * @throws RemoteException that can be thrown
     */
    void stop() throws RemoteException;

    /**
     * Triggers the notification of the registered viewers.
     * @param message to push
     * @throws RemoteException that can be thrown
     */
    void notifyViewers(LogMessageDTO message) throws RemoteException;
}
