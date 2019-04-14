package ch.hslu.vsk.logger.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiRegistryBootstrapper implements Runnable {

        @Override
        public void run() {
            System.setProperty("java.rmi.server.codebase", "http://localhost:8080/");

            try {
                final Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
                synchronized (registry) {
                    registry.wait();
                }
            } catch (final RemoteException e) {
                e.printStackTrace();
            } catch (final InterruptedException e){
                System.out.println("Closing registry...");
            }
        }

    }
