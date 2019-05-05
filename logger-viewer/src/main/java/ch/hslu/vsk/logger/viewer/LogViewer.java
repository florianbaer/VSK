package ch.hslu.vsk.logger.viewer;

import ch.hslu.vsk.logger.common.rmi.server.RegistrationServer;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class that initilaizes the LogViwer GUI.
 */
public class LogViewer extends Application {
    private Remote handler;
    private RegistrationServer registration;
    private LogViewerModel model;

    /**
     * Entry point of the javafx application.
     * @param stage to display
     * @throws Exception thrown during initialization of javafx application
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final BorderPane root = new BorderPane();

        // Load resources
        final FXMLLoader tableLoader = new FXMLLoader(this.getClass().getResource("/LogViewer.fxml"));
        root.setCenter(tableLoader.load());

        final LogViewerController controller = tableLoader.getController();
        model = new LogViewerModel();
        controller.initModel(model);

        controller.updateTable();

        // Add a Scene to the Stage
        stage.setTitle("LoggerViewer-VSK g06");
        stage.setScene(new Scene(root, 1024, 512));
        stage.show();

        this.registerViewer();
    }


    /**
     * Register this viewer using RMI.
     * @throws RemoteException that can happen
     * @throws NotBoundException that can happen
     */
    private void registerViewer() {
        try {
            String host = "localhost";
             System.setProperty("java.rmi.server.codebase", "http://localhost:8080/");
             System.setProperty("java.security.policy", "server_rules.policy");
             if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
             }
            final Registry reg = LocateRegistry.getRegistry(host, Registry.REGISTRY_PORT);
            this.registration = (RegistrationServer) reg.lookup("logpushserver");
            this.handler = UnicastRemoteObject.exportObject(model, 0);
            registration.register((Viewer) this.handler);
        } catch (Exception e) {
            System.out.println("Failed to register LogViewer: " + e.getMessage());
        }

    }
}
