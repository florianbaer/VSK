package ch.hslu.vsk.logger.common.rmi.server;

import ch.hslu.vsk.stringpersistor.api.PersistedString;

import java.rmi.RemoteException;

public class LogPushServer implements PushServer {

    public LogPushServer() throws RemoteException {
        super();
    }

    @Override
    public void register(PushServer server) throws RemoteException {
        System.out.println("remote call worked");
    }

    @Override
    public void unregister(PushServer server) throws RemoteException {
        System.out.println("remote call worded");
    }
}
