package ch.hslu.vsk.logger.common.rmi.viewer;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Instant;

public interface Viewer extends Remote {

    void sendLogMessage(Instant instant, LogMessage logMessage) throws RemoteException;
}
