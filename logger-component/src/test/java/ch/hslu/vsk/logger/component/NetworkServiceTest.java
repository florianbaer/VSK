package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.component.services.NetworkService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the NetworkService
 */
public class NetworkServiceTest {

    @Test
    public void testNormalSetup(){
        NetworkService service = NetworkService.getInstance();

        assertTrue("Host: 127.0.0.1, Port: 59090".equals(service.getConnectionDetails()));
    }

    @Test
    public void testSingleton(){
        NetworkService service = NetworkService.getInstance();

        NetworkService service1 = NetworkService.getInstance();

        assertTrue(service == service1);
    }
}
