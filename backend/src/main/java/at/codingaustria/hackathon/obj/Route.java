package at.codingaustria.hackathon.obj;

import java.util.List;

public class Route {
    List<Location> targets;
    List<Location> path;

    public Route(List<Location> targets) {
        this.targets = targets;
    }

    public Route() {

    }

    public List<Location> getTargets() {
        return targets;
    }

    public List<Location> getPath() {
        return path;
    }

}
