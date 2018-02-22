package net.smappz.arcadia;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

import net.smappz.arcadia.actors.Shoot;

import static net.smappz.arcadia.ArcadiaGame.RESOURCES;

public class Fireworks extends Group {

    void shoot(int shootId, Vector2 origin, float orientation) {
        TextureAtlas.AtlasRegion region = RESOURCES.getTextureRegion(String.format("shot0%d", shootId));
        Shoot shoot = new Shoot(region, origin, orientation, ArcadiaGame.INSTANCE.getShot(shootId));
        addActor(shoot);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        SnapshotArray<Actor> children = getChildren();
        Actor[] items = children.begin();
        for (int i=0; i<children.size; i++) {
            Actor actor = items[i];
            if (actor != null && !actor.isVisible()) {
                children.removeIndex(i);
            }
        }
        children.end();
    }
}
