package at.codingaustria.hackathon.evaluation;

import at.codingaustria.hackathon.obj.Location;
import at.codingaustria.hackathon.obj.Route;
import com.graphhopper.directions.api.client.ApiException;

import java.util.ArrayList;
import java.util.List;

public class RouteCompatator {
    public static Route compareRoutes(List<Location> locations1, List<Location> locations2) throws RouteNotFoundException, ApiException {
        List<Location> result = new ArrayList<>();
        Location locTest = locations1.get(0);
        var additionalCosts = compareLocation(locations2.get(0), locTest);
        ArrayList<Location> tempList = new ArrayList<>(locations1);
        tempList.remove(0);
        result.add(locTest);
        var route = RouteCompatator.compareRoutes(tempList, new ArrayList<>(locations2), locTest);
        result.addAll(route.getTargets());
        return new Route(
            result,
            additionalCosts + route.getCosts()
        );
    }

    public static Route compareRoutes(List<Location> locations1, List<Location> locations2, Location curLocation) throws RouteNotFoundException, ApiException {
        List<Location> result = new ArrayList<>();
        if (locations1.isEmpty() && locations2.isEmpty()) {
            return new Route(result, 0);
        } else if (locations1.isEmpty()) {
            var additionalCosts = compareLocation(locations2.get(0), curLocation);
            curLocation = locations2.get(0);
            result.add(curLocation);
            locations2.remove(0);
            var route = compareRoutes(locations1, locations2, curLocation);
            result.addAll(route.getTargets());
            return new Route(result, route.getCosts() + additionalCosts);
        } else if (locations2.isEmpty()) {
            var additionalCosts = compareLocation(locations1.get(0), curLocation);
            curLocation = locations1.get(0);
            result.add(curLocation);
            locations1.remove(0);
            var route = compareRoutes(locations1, locations2, curLocation);
            result.addAll(route.getTargets());
            return new Route(result, route.getCosts() + additionalCosts);
        }

        double additionalCosts1 = compareLocation(locations1.get(0), curLocation);
        double additionalCosts2 = compareLocation(locations2.get(0), curLocation);
        if (additionalCosts1 < additionalCosts2) {
            curLocation = locations1.get(0);
            result.add(curLocation);
            locations2.remove(0);
            var route = compareRoutes(locations1, locations2, curLocation);
            result.addAll(route.getTargets());
            return new Route(result, route.getCosts() + additionalCosts1);
        }
        curLocation = locations2.get(0);
        result.add(curLocation);
        locations2.remove(0);
        var route = compareRoutes(locations1, locations2, curLocation);
        result.addAll(route.getTargets());
        return new Route(result, route.getCosts() + additionalCosts2);


    }

    private static double compareLocation(Location loc1, Location loc2) throws RouteNotFoundException, ApiException {
        return RouteEvaluator.distance(loc1.getLatitude(), loc2.getLatitude(), loc1.getLongitude(), loc2.getLongitude());
    }
}

