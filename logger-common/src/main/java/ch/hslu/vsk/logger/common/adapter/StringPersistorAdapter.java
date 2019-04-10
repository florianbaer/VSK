package ch.hslu.vsk.logger.common.adapter;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.stringpersistor.api.*;

import java.time.Instant;

/**
 * Implements the {@link LogPersistor} using the Adapter pattern (GoF 139).
 */
public class StringPersistorAdapter implements LogPersistor {

    private final StringPersistor stringPersistor;

    /**
     * Creates a new instance of the {@link StringPersistorAdapter}.
     * @param stringPersistor the stringpersistor to inject.
     */
    public StringPersistorAdapter(final StringPersistor stringPersistor) {
        this.stringPersistor = stringPersistor;
    }

    /**
     * Gets the string persistor.
     * @return the {@link StringPersistor}.
     */
    public StringPersistor getStringPersistor() {
        return this.stringPersistor;
    }

    /**
     * Saves the a passed {@link LogMessage}.
     * @param log The logmessage to save.
     */
    public void save(final LogMessage log) {
        this.stringPersistor.save(Instant.now(), log.getMessageText());
    }

}
