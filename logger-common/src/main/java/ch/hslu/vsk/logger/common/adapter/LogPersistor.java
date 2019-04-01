package ch.hslu.vsk.logger.common.adapter;

import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;

public interface LogPersistor {

    public void save(LogMessage payload);
}
