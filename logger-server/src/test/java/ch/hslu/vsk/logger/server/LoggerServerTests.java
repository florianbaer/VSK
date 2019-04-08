/*
 * Copyright 2019 Roland Gisler, HSLU Informatik, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.vsk.logger.server;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import ch.hslu.vsk.logger.common.adapter.StringPersistorAdapter;
import ch.hslu.vsk.logger.common.messagepassing.LogServerCommunicationHandler;
import ch.hslu.vsk.stringpersistor.impl.FileStringPersistor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Testfälle für RemoveMeTest.
 */
final class LoggerServerTests {

    @Test
    public void setupPersistorAdapterTest() throws IOException {
        var properties = mock(ServerProperties.class);
        var threadPool = mock(ExecutorService.class);
        LoggerServer server = new LoggerServer(properties, threadPool);
        File file = new File("File.tmp");
        assertThat(file.createNewFile()).isTrue();
        file.deleteOnExit();
        StringPersistorAdapter persistor = server.setupPersistorAdapter(file);
        assertThat(persistor.getStringPersistor()).isInstanceOf(FileStringPersistor.class);
    }

    @Test
    public void loggerServerHandleTest() throws IOException {
        var properties = mock(ServerProperties.class);
        var threadPool = mock(ExecutorService.class);
        var socket = mock(Socket.class);

        LoggerServer server = new LoggerServer(properties, threadPool);
        server.handleMessage(socket);
        verify(threadPool, times(1)).execute(isA(LogServerCommunicationHandler.class));
    }
}
