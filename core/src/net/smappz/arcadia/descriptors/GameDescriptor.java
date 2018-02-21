package net.smappz.arcadia.descriptors;

import java.util.ArrayList;

public class GameDescriptor {
    private ArrayList<PlaneDescriptor> planes = new ArrayList<>();
    private ArrayList<ShotDescriptor> shots = new ArrayList<>();

    public ArrayList<PlaneDescriptor> getPlanes() {
        return planes;
    }

    public void setPlanes(ArrayList<PlaneDescriptor> planes) {
        this.planes = planes;
    }

    public ArrayList<ShotDescriptor> getShots() {
        return shots;
    }

    public void setShots(ArrayList<ShotDescriptor> shots) {
        this.shots = shots;
    }
}
