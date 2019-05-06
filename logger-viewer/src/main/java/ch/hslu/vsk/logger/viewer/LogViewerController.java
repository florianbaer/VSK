package ch.hslu.vsk.logger.viewer;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * Controller for the Log Viewer.
 */
public class LogViewerController {
    private LogViewerModel model;

    // Fields of View
    @FXML private TableView<TableEntryOfLogMessages> logTable;

    @FXML private TableColumn<TableEntryOfLogMessages, String> serverTimestamp;

    @FXML private TableColumn<TableEntryOfLogMessages, String> logTimestamp;

    @FXML private TableColumn<TableEntryOfLogMessages, String> identifier;

    @FXML private TableColumn<TableEntryOfLogMessages, String> logLevel;

    @FXML private TableColumn<TableEntryOfLogMessages, String> loggingClass;

    @FXML private TableColumn<TableEntryOfLogMessages, String> logMessage;

    /**
     * Used to initialize the model.
     * @param model to initialize.
     */
    public void initModel(final LogViewerModel model) {
        if (this.model != null) {
            throw new IllegalArgumentException("Model was already initialized");
        }
        this.model = model;

        // the cell value factory is used to populate the actual entries of each colum
        this.serverTimestamp.setCellValueFactory(new PropertyValueFactory<>("serverTimestamp"));
        this.logTimestamp.setCellValueFactory(new PropertyValueFactory<>("logTimestamp"));
        this.identifier.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        this.logLevel.setCellValueFactory(new PropertyValueFactory<>("logLevel"));
        this.loggingClass.setCellValueFactory((new PropertyValueFactory<>("loggingClass")));
        this.logMessage.setCellValueFactory(new PropertyValueFactory<>("logMessage"));
    }

    /**
     * Update the table.
     */
    public void updateTable() {
        ObservableList<TableEntryOfLogMessages> observableList = this.model.getListOfLogMessages();
        this.logTable.setItems(observableList);
        Platform.runLater(() -> logTable.scrollTo(observableList.size() - 1));
    }
}
