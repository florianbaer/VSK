package ch.hslu.vsk.logger.viewer;

import ch.hslu.vsk.logger.common.rmi.server.RegistrationService;
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
import java.util.Scanner;

/**
 * Class that initilaizes the LogViwer GUI.
 */
public class LogViewer extends Application {
    private Remote handler;
    private RegistrationService registration;
    private LogViewerModel model;
    private String rmiHost;
    private String rmiHostLocal;

    /**
     * Entry point of the javafx application.
     * @param stage to display
     * @throws Exception thrown during initialization of javafx application
     */
    @Override
    public void start(final Stage stage) throws Exception {
        System.out.println("Please enter the ip of the server that the rmi-messages should be received from:");
        Scanner scanner = new Scanner(System.in);
        this.rmiHost = scanner.nextLine();
        System.out.println("You entered: " + rmiHost);
        System.out.println("Please enter the ip of the machine the viewer runs on:");
        this.rmiHostLocal = scanner.nextLine();
        System.out.println("You entered: " + rmiHostLocal);

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
     * unregister Viewer because error is elsewise thrown at server
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        // unregister viewer
        if (this.handler!= null && this.registration != null) {
           registration.unregister((Viewer) this.handler);
        }
    }


    /**
     * Register this viewer using RMI.
     * @throws RemoteException that can happen
     * @throws NotBoundException that can happen
     */
    private void registerViewer() {
        try {
             System.setProperty("java.rmi.server.codebase", "http://localhost:8080/");
             System.setProperty("java.security.policy", "reg_rules.policy");
             System.setProperty("java.rmi.server.hostname", rmiHostLocal);
             if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
             }
            final Registry reg = LocateRegistry.getRegistry(rmiHost, Registry.REGISTRY_PORT);
            this.registration = (RegistrationService) reg.lookup("logpushserver");
            this.handler = UnicastRemoteObject.exportObject(model, 0);
            registration.register((Viewer) this.handler);
        } catch (Exception e) {
            System.out.println("Failed to register LogViewer: " + e.getMessage());
        }

    }
}
