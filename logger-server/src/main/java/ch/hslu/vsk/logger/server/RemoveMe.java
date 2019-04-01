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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Dummy-Klasse. Löschen, sobald eigene Quellen vorhanden sind.
 */
public final class RemoveMe {

    public static void main(String[]args) throws IOException {

        try (ServerSocket listener = new ServerSocket(59090)){

            System.out.println("The Server is running...");
            System.out.println("Waiting for Connections");

            while(true){
                try (Socket socket = listener.accept()){
                    InputStream inputStream = socket.getInputStream();

                    DataInputStream in = new DataInputStream(inputStream);


                    while(true){
                        String token = in.readUTF();

                        System.out.print("ID: " + token + " | ");

                        String message = in.readUTF();

                        System.out.print("Message: " + message  + " | ");

                        String end = in.readUTF();

                        System.out.print("End-Signal: " + end  + " | ");

                        System.out.println("");
                    }
                }
            }
        }

    }
}
