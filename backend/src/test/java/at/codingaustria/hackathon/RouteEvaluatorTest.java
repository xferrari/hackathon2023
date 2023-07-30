package at.codingaustria.hackathon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import at.codingaustria.hackathon.evaluation.RouteEvaluator;
import at.codingaustria.hackathon.evaluation.RouteNotFoundException;
import at.codingaustria.hackathon.obj.Location;
import com.graphhopper.directions.api.client.ApiException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RouteEvaluatorTest {

  @Test
  public void test() throws RouteNotFoundException, ApiException {
    var locations = List.of(
        new Location(48.2082, 16.3738), //Wien
        new Location(47.2692, 11.4041) //Innsbruck
    );

    assertEquals(
        476282.807, RouteEvaluator.getFullRouteInformation(locations).getCosts()
    );
  }

  @Test
  public void test_with_stop() throws RouteNotFoundException, ApiException {
    var locations = List.of(
        new Location(48.2082, 16.3738), //Wien
        new Location(47.2692, 11.4041), //Innsbruck
        new Location(46.6167, 14.3000) //Klagenfurt
    );

    assertEquals(
        795842.042, RouteEvaluator.getFullRouteInformation(locations).getCosts()
    );
  }
}
