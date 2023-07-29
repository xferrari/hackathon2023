package at.codingaustria.hackathon.obj;

import java.util.List;

public class Route {
    List<Location> targets;
    double costs;

    public Route() {
    }

    public Route(List<Location> targets, double costs) {
        this.targets = targets;
        this.costs = costs;
    }


    public List<Location> getTargets() {
        return targets;
    }


    public double getCosts() {
        return costs;
    }
}
