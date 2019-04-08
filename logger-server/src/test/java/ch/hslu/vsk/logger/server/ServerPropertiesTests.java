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

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the server properties.
 */
final class ServerPropertiesTests {

    @Test
    public void loadServerProperitesCreatesDefaultFileTest() throws IOException {
        // Arrange
        ServerProperties properties = new ServerProperties();
        File configFile = new File("Server.properties");
        configFile.delete();
        configFile.deleteOnExit();

        // Act
        assertEquals(configFile.isFile(), false);
        properties.loadProperties();

        // Assert
        assertEquals(configFile.isFile(), true);
        assertEquals(properties.getServerPort(), 1337);
        assertThat(properties.getLoggerFile().getAbsolutePath()).contains("Server").contains(".log");
    }

    @Test
    public void loadServerProperitesFileTest() throws IOException {
        int port = 123;
        String filePath = "file.log";

        new File(filePath).delete();
        // Arrange
        this.setUpConfigFile(filePath, port);

        ServerProperties properties = new ServerProperties();
        File configFile = new File("Server.properties");
        configFile.deleteOnExit();

        // Act
        properties.loadProperties();

        // Assert
        assertEquals(true, configFile.isFile());
        assertEquals(properties.getServerPort(), port);
        assertThat(properties.getLoggerFile().getAbsolutePath()).isEqualTo(new File(filePath).getAbsolutePath());
    }

    private void setUpConfigFile(String filePath, int port) throws IOException {
        File configFile = new File("Server.properties");

        if(configFile.createNewFile()){
            FileWriter writer = new FileWriter(configFile);
            writer.append(String.format("ch.hslu.vsk.server.port=%s\n", port));
            writer.append(String.format("ch.hslu.vsk.server.logfile=%s", filePath));
            writer.flush();
            writer.close();
        }
    }
}
