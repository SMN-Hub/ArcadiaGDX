package net.smappz.arcadia.actors;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.util.Formation;
import net.smappz.arcadia.util.Route;

import java.util.ArrayList;
import java.util.List;

import static net.smappz.arcadia.AbstractScreen.WIDTH;

public class InvaderSquadron extends Group implements Squadron {
    private final List<Invader> invaders = new ArrayList<>();
    private boolean goRight = true;
    private float speed = Float.MAX_VALUE;

    public InvaderSquadron(Army army, Route route, Formation formation, int... planes) {
        army.addActor(this);
        for (int l=0; l<planes.length; l++) {
            int plane = planes[l];
            Route r = formation.place(route, l);
            Invader inv = new Invader(plane, r.getPoint(0));
            invaders.add(inv);
            army.addActor(inv);
            speed = Math.min(speed, inv.getDescriptor().getSpeed());
        }
    }

    @Override
    public void act (float delta) {
        if (ArcadiaGame.INSTANCE.getListener().isFinished())
            return;

        float minX = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float minY = Float.MAX_VALUE;
        boolean empty = true;
        for (ShootableActor actor : invaders) {
            if (!actor.isVisible())
                continue;
            empty = false;
            Rectangle rect = actor.getBoundingRectangle();
            minX = Math.min(minX, rect.getX());
            maxX = Math.max(maxX, rect.getX() + rect.width);
            minY = Math.min(minY, rect.getY());
        }
        if (empty)
            return;
        float deltaX = goRight ? delta * speed : -delta * speed;
        float deltaY = 0;
        if ((goRight && maxX + deltaX >= WIDTH) || (!goRight && minX + deltaX <= 0)) {
            deltaX = goRight ? WIDTH - maxX : -minX;
            deltaY = - Formation.DECAL_Y / 2;
            goRight = !goRight;
        }
        for (ShootableActor actor : invaders) {
            if (!actor.isVisible())
                continue;
            Vector2 pos = actor.getPosition();
            pos.add(deltaX, deltaY);
            actor.setPosition(pos);
        }

        super.act(delta);
    }

    @Override
    public void restart() {
        for (ShootableActor actor : invaders) {
            actor.reset();
        }
    }
}
