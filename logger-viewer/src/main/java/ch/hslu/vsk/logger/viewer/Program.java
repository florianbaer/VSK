package ch.hslu.vsk.logger.viewer;

import ch.hslu.vsk.logger.common.rmi.server.LogPushServer;
import ch.hslu.vsk.logger.common.rmi.server.PushServer;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * The Class which starts the program.
 */
public final class Program {

    /**
     * The private constructor for the program.
     */
    private Program() {
    }

    /**
     * The main method.
     * @param args The main method arguments.
     */
    public static void main(final String[] args)throws MalformedURLException, RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry("localhost", 3455);
        var server = (PushServer) registry.lookup("logpushserver");

        server.register(null);


    }
}
