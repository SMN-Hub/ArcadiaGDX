package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.util.Formation;
import net.smappz.arcadia.util.Route;

import java.util.ArrayList;
import java.util.List;

public class AirSquadron extends Group implements Squadron {

    private final List<ShootableActor> actors = new ArrayList<>();

    public AirSquadron(Army army, Route route, Formation formation, int... planes) {
        army.addActor(this);
        for (int l=0; l<planes.length; l++) {
            int plane = planes[l];
            Route r = formation.place(route, l);
            ShootableActor ae = plane > 10 ? new Invader(plane, r) : new AirEnemy(plane, r);
            addEnemy(ae);
        }
    }

    @Override
    public void act (float delta) {
        if (!ArcadiaGame.INSTANCE.getListener().isFinished()) {
            super.act(delta);
        }
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        // Skip, just a container
    }

    private void addEnemy(ShootableActor ae) {
        getParent().addActor(ae); // assign to parent directly
        actors.add(ae);
    }

    private boolean isOut() {
        for (ShootableActor actor : actors) {
            if (!actor.isOut())
                return false;
        }
        return true;
    }

    public void restart() {
        for (ShootableActor actor : actors) {
            actor.reset();
        }
    }
}
