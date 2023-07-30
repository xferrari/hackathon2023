package at.codingaustria.hackathon;

import static org.junit.jupiter.api.Assertions.*;

import at.codingaustria.hackathon.controller.RouteContoller;
import at.codingaustria.hackathon.evaluation.RouteEvaluator;
import at.codingaustria.hackathon.evaluation.RouteNotFoundException;
import at.codingaustria.hackathon.obj.Location;
import at.codingaustria.hackathon.obj.Route;
import com.graphhopper.directions.api.client.ApiException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RouteControllerTest {

  private RouteContoller sut;

  @BeforeEach
  public void setup() {
    sut = new RouteContoller();
  }

  @Test
  public void mergeRoutes() throws RouteNotFoundException, ApiException {
    List<Location> list1 = new ArrayList<>();
    list1.add(new Location(48.21612, 16.373137));
    list1.add(new Location(48.4017869, 15.9847379));
    list1.add(new Location(48.3739927, 16.6357775));
    list1.add(new Location(48.1245335, 14.8823511));
    Route route1 = RouteEvaluator.getFullRouteInformation(list1);

    List<Location> list2 = new ArrayList<>();
    list2.add(new Location(48.4017869, 15.9847379));
    list2.add(new Location(48.3739927, 16.6357775));
    list2.add(new Location(48.1245335, 14.8823511));
    Route route2 = RouteEvaluator.getFullRouteInformation(list2);

    List<Location> list3 = new ArrayList<>();
    list3.add(new Location(48.0053016, 16.231961));
    list3.add(new Location(48.4017869, 15.9847379));
    list3.add(new Location(48.1245335, 14.8823511));
    Route route3 = RouteEvaluator.getFullRouteInformation(list3);

    List<Location> list4 = new ArrayList<>();
    list4.add(new Location(47.845516, 16.516668));
    list4.add(new Location(47.7766024, 17.0326694));
    list4.add(new Location(47.6000949, 16.6252523));
    list4.add(new Location(47.8966677, 16.6438987));
    Route route4 = RouteEvaluator.getFullRouteInformation(list4);

    List<Route> allRoutes = new ArrayList<>();

    allRoutes.add(route1);
    allRoutes.add(route2);
    allRoutes.add(route3);
    allRoutes.add(route4);

    var allLocations = new ArrayList<Location>();
    allLocations.addAll(list1);
    allLocations.addAll(list2);
    allLocations.addAll(list3);
    allLocations.addAll(list4);

    var result = sut.mergeRoutes(allRoutes);

    var allRetrievedLocations = new ArrayList<Location>();
    for (var route : result.getBody()) {
      allRetrievedLocations.addAll(route.getTargets());
    }

    assertTrue(allLocations.containsAll(allRetrievedLocations));
    printMissingLocations(allLocations, allRetrievedLocations);
    assertTrue(allRetrievedLocations.containsAll(allLocations));
  }

  private void printMissingLocations(List<Location> allLocations, List<Location> retrievedLocations) {
    for (var location : allLocations) {
      if (!retrievedLocations.contains(location)) {
        System.out.println(location.getLatitude() + ", " + location.getLongitude());
      }
    }
  }

}
