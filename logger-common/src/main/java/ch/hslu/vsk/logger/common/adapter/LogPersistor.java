package ch.hslu.vsk.logger.common.adapter;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;

/**
 * Server side implementation of the Adapter Pattern used to persist Log-Messages.
 */
public interface LogPersistor {

    /**
     * Saves the payload of the given message.
     * @param payload Message of which the payload should be saved.
     */
    void save(LogMessage payload);
}
