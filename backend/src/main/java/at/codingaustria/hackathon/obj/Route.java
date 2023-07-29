package at.codingaustria.hackathon.obj;

import java.util.List;

public class Route {
    List<Location> targets;
    List<Location> path;
    double costs;

    public Route(List<Location> targets, List<Location> path, double costs) {
        this.targets = targets;
        this.path = path;
        this.costs = costs;
    }

    public Route() {

    }

    public List<Location> getTargets() {
        return targets;
    }

    public List<Location> getPath() {
        return path;
    }

    public double getCosts() {
        return costs;
    }
}
