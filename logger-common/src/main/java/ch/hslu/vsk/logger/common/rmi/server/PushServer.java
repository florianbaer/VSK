package ch.hslu.vsk.logger.common.rmi.server;

import ch.hslu.vsk.stringpersistor.api.PersistedString;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PushServer extends Remote {
    void register(PushServer server) throws RemoteException;
    void unregister(PushServer server) throws RemoteException;
}
