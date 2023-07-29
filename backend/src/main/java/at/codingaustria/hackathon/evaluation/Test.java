package at.codingaustria.hackathon.evaluation;

import at.codingaustria.hackathon.obj.Location;
import com.graphhopper.directions.api.client.ApiException;
import java.util.List;

public class Test {

  public static void main(String[] args) throws RouteNotFoundException, ApiException {
    var locations = List.of(
        new Location(48.2082, 16.3738),
        new Location(47.2692, 11.4041)
    );

    if(
        476282.807 != RouteEvaluator.getFullRouteInformation(locations).getCosts()
    ) {
      throw new RuntimeException();
    }
  }
}
