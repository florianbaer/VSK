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

import ch.hslu.vsk.logger.common.adapter.StringPersistorAdapter;
import ch.hslu.vsk.logger.common.messagepassing.LogCommunicationHandler;
import ch.hslu.vsk.logger.common.messagepassing.LogServerCommunicationHandler;
import ch.hslu.vsk.logger.common.rmi.server.PushServer;
import ch.hslu.vsk.stringpersistor.impl.FileStringPersistor;
import ch.hslu.vsk.stringpersistor.impl.PersistedStringCsvConverter;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;

/**
 * Logger-Server which handles the incoming request from the logger component.
 */
public final class LoggerServer implements Runnable {

    private final ServerProperties serverProperties;
    private ExecutorService threadPool = null;
    private PushServer pushServer;
    private StringPersistorAdapter persistorAdapter = null;

    /**
     * The loggerserver which handles the requests.
     * @param serverProperties The properties to be used in the server.
     * @param threadPool The executor service to be used.
     */
    public LoggerServer(final ServerProperties serverProperties, final ExecutorService threadPool,
                        final PushServer server) {
        this.serverProperties = serverProperties;
        this.threadPool = threadPool;
        this.pushServer = server;
    }


    @Override
    public void run() {
        try {
            int loggerPort = this.serverProperties.getServerPort();
            File loggerFile = this.serverProperties.getLoggerFile();

            System.out.println("Server listening on port: " + loggerPort);
            try (ServerSocket listen = new ServerSocket(loggerPort)) {

                persistorAdapter = this.setupPersistorAdapter(loggerFile);

                Socket client;


                while (!(Thread.currentThread().isInterrupted())) {
                    listen.setSoTimeout(5000);
                    try {
                        client = listen.accept();
                        this.handleMessage(client);
                    } catch (SocketTimeoutException ex) {
                        // ignore, because there is no other solution
                    }
                }

                this.threadPool.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println("Error while initialization of the server...");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * The method to handle the incoming messages.
     * @param client The socket client which has the connections.
     * @throws IOException unhandled io exception.
     */
    public void handleMessage(final Socket client) throws IOException {
        LogServerCommunicationHandler handler = new LogServerCommunicationHandler(
                client.getInputStream(), client.getOutputStream(), this.persistorAdapter, this.pushServer);
        this.threadPool.execute(handler);
    }

    /**
     * Creates the persistor adapter.
     * @param loggerFile The loggerfile used to setup the persistor.
     * @return StringPersistorAdapter using the given logger file.
     */
    public StringPersistorAdapter setupPersistorAdapter(final File loggerFile) {
        FileStringPersistor filePersistor = new FileStringPersistor(new PersistedStringCsvConverter());

        filePersistor.setFile(loggerFile);
        return new StringPersistorAdapter(filePersistor);
    }


}
