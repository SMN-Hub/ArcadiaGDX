package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.RouteDriver;

import java.util.ArrayList;
import java.util.List;

public class AirSquadron extends Group{

    private static final float DECAL = 90f;
    private List<AirEnemy> actors = new ArrayList<>();
    private boolean cycle = false;

    public AirSquadron(Army army, Route route, int... planes) {
        army.addActor(this);
        for (int l=0; l<planes.length; l++) {
            int plane = planes[l];
            Route r = new Route(route);
            if (l > 0) {
                RouteDriver.backWard(r, l*DECAL);
            }
            AirEnemy ae = new AirEnemy(plane, r);
            addEnemy(ae);
        }
    }

    public void setCycle(boolean cycle) {
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

    @Override
    public void draw (Batch batch, float parentAlpha) {
        // Skip, just a container
    }

    private void addEnemy(AirEnemy ae) {
        getParent().addActor(ae); // assign to parent directly
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
