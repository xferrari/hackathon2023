package at.codingaustria.hackathon;

import at.codingaustria.hackathon.obj.Location;

import java.util.Comparator;

import static at.codingaustria.hackathon.CoordinatesHelper.distance;

public class LocationComparator {

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public static double distanceBetweenLocations(Location l1, Location l2) {
        if(l1.hasAltitude() && l2.hasAltitude()) {
            return distance(l1.getLatitude(), l2.getLatitude(), l1.getLongitude(), l2.getLongitude(), l1.getAltitude(), l2.getAltitude());
        }
        return l1.distanceTo(l2);
    }
}
