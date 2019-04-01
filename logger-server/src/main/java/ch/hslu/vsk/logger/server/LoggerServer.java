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
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Logger-Server which handles the incoming request from the logger component.
 */
public final class LoggerServer {

    public static void main(String[]args) {

        System.out.println("Server listening on 59090");
        try (ServerSocket listen = new ServerSocket(59090);){
            var filePersistor = new FileStringPersistor(new PersistedStringCsvConverter());
            var file = File.createTempFile("test", "tmp");
            System.out.println(file.getAbsoluteFile());
            filePersistor.setFile(file);
            var persistorAdapter = new StringPersistorAdapter(filePersistor);

            while (listen.isBound()) {
                Socket client = listen.accept();
                LogServerCommunicationHandler handler = new LogServerCommunicationHandler(client.getInputStream(), client.getOutputStream(), persistorAdapter);
                handler.addMessageType(new LogMessage());
                handler.addMessageType(new ResultMessage());
                Thread t = new Thread(handler);
                t.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
