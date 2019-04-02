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
import ch.hslu.vsk.logger.common.messagepassing.LogServerCommunicationHandler;
import ch.hslu.vsk.logger.common.messagepassing.messages.LogMessage;
import ch.hslu.vsk.logger.common.messagepassing.messages.ResultMessage;
import ch.hslu.vsk.stringpersistor.impl.FileStringPersistor;
import ch.hslu.vsk.stringpersistor.impl.PersistedStringCsvConverter;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Logger-Server which handles the incoming request from the logger component.
 */
public final class LoggerServer implements Runnable {

    private ExecutorService threadPool = null;
    private StringPersistorAdapter persistorAdapter = null;



    @Override
    public void run() {
        try {
            ServerProperties serverProperties = new ServerProperties();
            serverProperties.loadProperties();
            int loggerPort = serverProperties.loadServerPortProperty();
            File loggerFile = serverProperties.loadLoggerFileProperty();

            System.out.println("Server listening on port: " + loggerPort);
            try (ServerSocket listen = new ServerSocket(loggerPort)) {

                persistorAdapter = this.setupPersistorAdapter(loggerFile);

                Socket client;

                this.threadPool = Executors.newCachedThreadPool();

                while (!(Thread.currentThread().isInterrupted())) {
                    try {
                        client = listen.accept();

                        this.handleMessage(client);
                    }
                    catch (SocketTimeoutException ex){
                        // do nothing, it seems to be the only way to check if the console application has to be
                        // closed by input of the user...
                        // https://stackoverflow.com/questions/2983835/how-can-i-interrupt-a-serversocket-accept-method
                    }
                }

                this.threadPool.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex){
            System.out.println("Error while initialization of the server...");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


    private void handleMessage(Socket client) throws IOException {
        LogServerCommunicationHandler handler = new LogServerCommunicationHandler(client.getInputStream(), client.getOutputStream(), this.persistorAdapter);
        this.setupMessageTypes(handler);
        this.threadPool.execute(handler);
    }

    public void setupMessageTypes(LogServerCommunicationHandler handler) {
        handler.addMessageType(new LogMessage());
        handler.addMessageType(new ResultMessage());
    }

    public StringPersistorAdapter setupPersistorAdapter(File loggerFile) {
        FileStringPersistor filePersistor = new FileStringPersistor(new PersistedStringCsvConverter());

        filePersistor.setFile(loggerFile);
        return new StringPersistorAdapter(filePersistor);
    }


}
