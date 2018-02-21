package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.HashMap;
import java.util.Map;

class Fireworks extends Group {
    private final TextureAtlas textureAtlas;
    private final Map<Integer,TextureAtlas.AtlasRegion> regions = new HashMap<>();

    Fireworks() {
        textureAtlas = new TextureAtlas(Gdx.files.internal("shots.atlas"));
    }

    void shoot(int shootId, Vector2 origin, float orientation) {
        TextureAtlas.AtlasRegion region = regions.get(shootId);
        if (region == null) {
            region = textureAtlas.findRegion(String.format("000%d", shootId));
            regions.put(shootId, region);
        }
        Shoot shoot = new Shoot(region, origin, orientation);
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
