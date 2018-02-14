package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

class Fireworks extends Group {
    private TextureAtlas.AtlasRegion regionShoot1;

    Fireworks() {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("shots.atlas"));
        regionShoot1 = textureAtlas.findRegion("0001");
    }

    void shoot(Vector2 origin, float orientation) {
        Shoot shoot = new Shoot(regionShoot1, origin, orientation);
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
