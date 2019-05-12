package ch.hslu.vsk.logger.common.adapter;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;

import java.time.Instant;

/**
 * Server side implementation of the Adapter Pattern used to persist Log-Messages.
 */
public interface LogPersistor {

    /**
     * Saves the payload of the given message.
     * @param payload Message of which the payload should be saved.
     * @param instant (time).
     */
    void save(Instant instant, LogMessage payload);

    /**
     * Gets the StringPersistor.
     * @return The instance of the stringpersitostor.
     */
    StringPersistor getStringPersistor();
}
