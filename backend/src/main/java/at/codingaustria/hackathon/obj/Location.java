package at.codingaustria.hackathon.obj;

import java.util.Objects;

public class Location {
    private double latitude;
    private double longitude;
    private String facilityName;

    public Location() {
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(double latitude, double longitude, String facilityName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.facilityName = facilityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getFacilityName() {
        return facilityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return Double.compare(latitude, location.latitude) == 0
            && Double.compare(longitude, location.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
