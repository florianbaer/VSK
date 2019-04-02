package ch.hslu.vsk.logger.common.adapter;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.stringpersistor.api.*;

import java.time.Instant;

/**
 * Implements the {@link LogPersistor} using the Adapter pattern (GoF 139).
 */
public class StringPersistorAdapter implements LogPersistor {

    private final StringPersistor stringPersistor;

    public StringPersistorAdapter(final StringPersistor stringPersistor) {
        this.stringPersistor = stringPersistor;
    }

    public StringPersistor getStringPersistor(){
        return this.stringPersistor;
    }


    public void save(final LogMessage log) {
        this.stringPersistor.save(Instant.now(), log.getMessageText());
    }

}
