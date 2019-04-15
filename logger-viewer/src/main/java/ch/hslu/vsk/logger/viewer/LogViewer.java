package ch.hslu.vsk.logger.viewer;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.rmi.viewer.Viewer;

public class LogViewer implements Viewer {
    @Override
    public void sendLogMessage(final LogMessage logMessage) {
        System.out.println("LogMessage was received in the viewer" + logMessage);
    }
}
