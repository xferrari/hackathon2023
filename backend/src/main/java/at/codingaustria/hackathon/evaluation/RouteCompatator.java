package at.codingaustria.hackathon.evaluation;

import at.codingaustria.hackathon.obj.Location;
import com.graphhopper.directions.api.client.ApiException;

import java.util.ArrayList;
import java.util.List;

public class RouteCompatator {
    public static List<Location> compareRoutes(List<Location> route, List<Location> route2) throws RouteNotFoundException, ApiException {
        List<Location> result = new ArrayList<>();
        Location locTest = route.get(0);
        ArrayList<Location> tempList = new ArrayList<>(route);
        tempList.remove(0);
        result.add(locTest);
        result.addAll(RouteCompatator.compareRoutes(tempList, new ArrayList<>(route2), locTest));
        return result;
    }

    public static List<Location> compareRoutes(List<Location> route, List<Location> route2, Location curLocation) throws RouteNotFoundException, ApiException {
        List<Location> result = new ArrayList<>();
        if (route.isEmpty() && route2.isEmpty()) {
            return result;
        } else if (route.isEmpty()) {
            curLocation = route2.get(0);
            result.add(curLocation);
            route2.remove(0);
            result.addAll(compareRoutes(route, route2, curLocation));
            return result;
        } else if (route2.isEmpty()) {
            curLocation = route.get(0);
            result.add(curLocation);
            route.remove(0);
            result.addAll(compareRoutes(route, route2, curLocation));
            return result;
        }

        if (compareLocation(route.get(0), curLocation) < compareLocation(route2.get(0), curLocation)) {
            curLocation = route.get(0);
            result.add(curLocation);
            route.remove(0);
            result.addAll(compareRoutes(route, route2, curLocation));
            return result;
        }
        curLocation = route2.get(0);
        result.add(curLocation);
        route2.remove(0);
        result.addAll(compareRoutes(route, route2, curLocation));
        return result;


    }

    private static double compareLocation(Location loc1, Location loc2) throws RouteNotFoundException, ApiException {
        return RouteEvaluator.distance(loc1.getLatitude(), loc2.getLatitude(), loc1.getLongitude(), loc2.getLongitude());
    }
}

