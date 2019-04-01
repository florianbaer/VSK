package ch.hslu.vsk.logger.common.adapter;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;

/**
 * Server side implementation of the Adapter Pattern used to persist Log-Messages
 */
public interface LogPersistor {

    public void save(LogMessage payload);
}
