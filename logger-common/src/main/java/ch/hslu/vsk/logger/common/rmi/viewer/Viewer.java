package ch.hslu.vsk.logger.common.rmi.viewer;

import ch.hslu.vsk.logger.common.DTO.LogMessageDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for registered viewers.
 */
public interface Viewer extends Remote {

    /**
     * Send LogMessageDTO.
     * @param logMessage to send
     * @throws RemoteException that can be thrown
     */
    void sendLogMessage(LogMessageDTO logMessage) throws RemoteException;
}
