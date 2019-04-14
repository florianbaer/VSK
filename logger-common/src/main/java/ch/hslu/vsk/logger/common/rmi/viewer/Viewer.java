package ch.hslu.vsk.logger.common.rmi.viewer;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Viewer extends Remote {

    void sendLogMessage(LogMessage logMessage) throws RemoteException;
}
