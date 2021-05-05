package net.smappz.arcadia.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Army extends Group {

    @Override
    public void act (float delta) {
        super.act(delta);

    }

    public boolean isOver() {
        for (Actor child : getChildren()) {
            if (child instanceof SpriteActor && child.isVisible()) {
                return false;
            }
        }
        return true;
    }
}
