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

    private static String PROPERTIES_LOGFILE = "ch.hslu.vsk.server.logfile";
    private static String PROPERTIES_PORT = "ch.hslu.vsk.server.port";

    public static void main(String[]args) {
        LoggerServer server = new LoggerServer();

        Thread serverThread = new Thread(server);

        Scanner keyboard = new Scanner(System.in);
        serverThread.start();
        char input = ' ';
        while(input != 'q' && input != 'Q') {
            System.out.println("Press 'q' or 'Q' to quit the server");
            input = keyboard.next().charAt(0);
        }

        System.out.println("Server will close within the next 5 Seconds.");
        serverThread.interrupt();
    }

    @Override
    public void run() {
        try {
            Properties serverProperties = this.loadProperties();

            String loggerPort = serverProperties.getProperty(PROPERTIES_PORT);
            File loggerFile = this.loadLoggerFileProperty(serverProperties);

            System.out.println("Server listening on port: " + loggerPort);
            try (ServerSocket listen = new ServerSocket(Integer.parseInt(loggerPort))) {
                // to check if the server was aborted from user input
                listen.setSoTimeout(5000);

                FileStringPersistor filePersistor = new FileStringPersistor(new PersistedStringCsvConverter());

                filePersistor.setFile(loggerFile);
                StringPersistorAdapter persistorAdapter = new StringPersistorAdapter(filePersistor);
                Socket client;
                ExecutorService threadPool = Executors.newCachedThreadPool();
                while (!(Thread.currentThread().isInterrupted())) {
                    try {
                        client = listen.accept();

                        LogServerCommunicationHandler handler = new LogServerCommunicationHandler(client.getInputStream(), client.getOutputStream(), persistorAdapter);
                        handler.addMessageType(new LogMessage());
                        handler.addMessageType(new ResultMessage());
                        threadPool.execute(handler);
                    }
                    catch (SocketTimeoutException ex){
                        // do nothing, it seems to be the only way to check if the console application has to be
                        // closed by input of the user...
                        // https://stackoverflow.com/questions/2983835/how-can-i-interrupt-a-serversocket-accept-method
                    }
                }

                threadPool.shutdown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex){
            System.out.println("Error while initialization of the server...");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private File loadLoggerFileProperty(Properties serverProperties) throws IOException {
        var logFilePath = serverProperties.getProperty(PROPERTIES_LOGFILE);
        File logFile = null;
        if(logFilePath == null || logFilePath.isEmpty()) {
            System.out.println("No valid LogFile defined, logging to tmp File...");
            logFile = File.createTempFile("Server", ".log");
        }
        else{
            logFile = new File(logFilePath);
        }

        System.out.println(String.format("Logging to file : '%s'", logFile.getAbsoluteFile()));
        return logFile;
    }

    private Properties loadProperties() {
        Properties serverProperties = new Properties();
        try {
            serverProperties.load(LoggerServer.class.getClassLoader().getResourceAsStream("Server.properties"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        return serverProperties;
    }
}
