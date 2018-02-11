package net.smappz.arcadia;

import com.badlogic.gdx.scenes.scene2d.Group;

import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.RouteDriver;

import java.util.ArrayList;
import java.util.List;

class AirSquadron extends Group{

    private static final float DECAL = 90f;
    private List<AirEnemy> actors = new ArrayList<>();
    private boolean cycle = false;

    AirSquadron(int plane, Route route, int nb) {
        for (int l=0; l<nb; l++) {
            Route r = new Route(route);
            if (l > 0) {
                RouteDriver.backWard(r, l*DECAL);
            }
            AirEnemy ae = new AirEnemy(plane, r);
            addEnemy(ae);
        }
    }

    void setCycle(boolean cycle) {
        this.cycle = cycle;
    }

    @Override
    public void act (float delta) {
        super.act(delta);

        // Cycle execution
        if (cycle && isOut()) {
            restart();
        }
    }

    private void addEnemy(AirEnemy ae) {
        addActor(ae);
        actors.add(ae);
    }

    private boolean isOut() {
        for (AirEnemy actor : actors) {
            if (!actor.isOut())
                return false;
        }
        return true;
    }

    private void restart() {
        for (AirEnemy actor : actors) {
            actor.restart();
        }
    }
}
