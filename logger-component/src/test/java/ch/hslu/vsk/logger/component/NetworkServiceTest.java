package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.component.services.NetworkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the NetworkService
 */
public class NetworkServiceTest {

    @Test
    public void testSingleton(){
        NetworkService service = NetworkService.getInstance();

        NetworkService service1 = NetworkService.getInstance();

        assertTrue(service == service1);
    }

    @Test
    public void testChangingConnDetails() throws IOException {
        ServerSocket socket = new ServerSocket(59090);

        NetworkService service = NetworkService.getInstance();

        assertTrue("Host: localhost, Port: 59090".equals(service.getConnectionDetails()));

        ServerSocket socket1 = new ServerSocket(59091);

        service.changeConnectionDetails("127.0.0.1:59091");

        assertTrue("Host: 127.0.0.1, Port: 59091".equals(service.getConnectionDetails()));

        service.sendLogMessageToServer("test");

        assertDoesNotThrow((Executable) IOException::new);

        socket.close();
        socket1.close();
    }
}
