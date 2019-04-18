package ch.hslu.vsk.logger.component;

import ch.hslu.vsk.logger.component.services.NetworkService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the NetworkService
 */
public class NetworkServiceTest {

    @Test
    public void testSingleton(){
        NetworkService service = NetworkService.getInstance("localhost:59090");

        NetworkService service1 = NetworkService.getInstance("localhost:59090");

        assertTrue(service == service1);
    }
}
