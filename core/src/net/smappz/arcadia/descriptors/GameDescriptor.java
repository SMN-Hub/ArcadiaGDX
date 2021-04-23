package net.smappz.arcadia.descriptors;

import com.badlogic.gdx.utils.IntMap;

@SuppressWarnings("unused")
public class GameDescriptor {
    private IntMap<PlaneDescriptor> planes;
    private IntMap<ShotDescriptor> shots;

    public PlaneDescriptor getPlane(int id) {
        return planes.get(id);
    }

    public ShotDescriptor getShot(int id) {
        return shots.get(id);
    }
}
