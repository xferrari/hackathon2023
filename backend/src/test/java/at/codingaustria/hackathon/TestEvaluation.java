package at.codingaustria.hackathon;

import at.codingaustria.hackathon.evaluation.RouteCompatator;
import at.codingaustria.hackathon.evaluation.RouteNotFoundException;
import at.codingaustria.hackathon.obj.Location;
import com.graphhopper.directions.api.client.ApiException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEvaluation {

    @Test
    public void test() throws RouteNotFoundException, ApiException {
        var locations = List.of(
                new Location(48.1082, 16.2738),
                new Location(48.2082, 16.3738),
                new Location(47.2692, 11.4041)
        );

        var locations2 = List.of(
                new Location(48.1082, 16.2738),
                new Location(47.1692, 11.3041),
                new Location(47.1592, 11.3141)
        );

        List<Location> a = RouteCompatator.compareRoutes(locations, locations2);
        assertEquals(6, a.size());
    }

    @Test
    public void testDoubledValues() throws RouteNotFoundException, ApiException {
        var locations = List.of(
                new Location(48.1082, 16.2738),
                new Location(48.2082, 16.3738),
                new Location(47.2692, 11.4041)
        );

        var locations2 = List.of(
                new Location(48.1082, 16.2738),
                new Location(48.2082, 16.3738),
                new Location(47.2692, 11.4041)
        );

        List<Location> a = RouteCompatator.compareRoutes(locations, locations2);
        assertEquals(3, a.size());
    }
}
