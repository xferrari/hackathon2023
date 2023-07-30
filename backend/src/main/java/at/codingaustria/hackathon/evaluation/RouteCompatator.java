package at.codingaustria.hackathon.evaluation;

import at.codingaustria.hackathon.obj.Location;
import at.codingaustria.hackathon.obj.Route;
import com.graphhopper.directions.api.client.ApiException;

import java.util.ArrayList;
import java.util.List;

public class RouteCompatator {
    public static Route compareRoutes(List<Location> locations1, List<Location> locations2) throws RouteNotFoundException, ApiException {
        List<Location> initial = new ArrayList<>();
        initial.addAll(locations1);
        initial.addAll(locations2);
        locations1 = new ArrayList<>(locations1);
        locations2 = new ArrayList<>(locations2);
        Location curLocation = locations1.get(0);
        locations1.remove(0);
        return compareRoutes(locations1, locations2, curLocation, initial);
    }

    private static List<Location> getAll(List<Location> locations1, List<Location> locations2, Location curLocation) {
        var all = new ArrayList<Location>();
        all.add(curLocation);
        all.addAll(locations1);
        all.addAll(locations2);
        return all;
    }

    public static Route compareRoutes(List<Location> locations1, List<Location> locations2, Location curLocation, List<Location> initial) throws RouteNotFoundException, ApiException {
        List<Location> result = new ArrayList<>();
        if (locations1.isEmpty() && locations2.isEmpty()) {
            result.add(curLocation);
            return new Route(result, 0);
        } else if (locations1.isEmpty()) {
            var additionalCosts = compareLocation(locations2.get(0), curLocation);
            result.add(curLocation);
            curLocation = locations2.get(0);
            locations2.remove(0);
            var route = compareRoutes(locations1, locations2, curLocation, initial);
            result.addAll(route.getTargets());
            return new Route(result, route.getCosts() + additionalCosts);
        } else if (locations2.isEmpty()) {
            var additionalCosts = compareLocation(locations1.get(0), curLocation);
            result.add(curLocation);
            curLocation = locations1.get(0);
            locations1.remove(0);
            var route = compareRoutes(locations1, locations2, curLocation, initial);
            result.addAll(route.getTargets());
            return new Route(result, route.getCosts() + additionalCosts);
        }

        double additionalCosts1 = compareLocation(locations1.get(0), curLocation);
        double additionalCosts2 = compareLocation(locations2.get(0), curLocation);
        if (additionalCosts1 < additionalCosts2) {
            result.add(curLocation);
            curLocation = locations1.get(0);
            locations1.remove(0);
            var route = compareRoutes(locations1, locations2, curLocation, initial);
            result.addAll(route.getTargets());
            return new Route(result, route.getCosts() + additionalCosts1);
        }
        result.add(curLocation);
        curLocation = locations2.get(0);
        locations2.remove(0);
        var route = compareRoutes(locations1, locations2, curLocation, initial);
        result.addAll(route.getTargets());
        return new Route(result, route.getCosts() + additionalCosts2);
    }

    private static double compareLocation(Location loc1, Location loc2) throws RouteNotFoundException, ApiException {
        return RouteEvaluator.distance(loc1.getLatitude(), loc2.getLatitude(), loc1.getLongitude(), loc2.getLongitude());
    }
}

