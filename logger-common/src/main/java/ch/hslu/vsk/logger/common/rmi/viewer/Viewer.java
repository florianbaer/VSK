package ch.hslu.vsk.logger.common.rmi.viewer;

import ch.hslu.vsk.logger.common.rmi.server.PushServer;
import ch.hslu.vsk.stringpersistor.api.PersistedString;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Viewer extends Remote {

    void sendLogMessage(PersistedString logMessage);
}
