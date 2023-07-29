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
        new Location(48.2082, 16.3738),
        new Location(47.2692, 11.4041)
    );

    assertEquals(
        476282.807,RouteEvaluator.getFullRouteInformation(locations).getCosts()
    );
  }
}
