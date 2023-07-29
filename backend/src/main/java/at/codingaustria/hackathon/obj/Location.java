package at.codingaustria.hackathon.obj;

public class Location {
    double latitude;
    double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean hasAltitude() {
        return latitude != 0;
    }

    public boolean hasLongitude() {
        return longitude != 0;
    }
}
