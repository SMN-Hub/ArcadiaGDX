package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.RouteDriver;
import net.smappz.arcadia.util.SpriteActor;

import java.util.HashMap;
import java.util.Map;


class AirEnemy extends SpriteActor {
    private static final float SPEED = 200.f;
    private static final float TIME_TO_PITCH = 1.f;
    private static final float ROUTE_TO_SPRITE = 90f;

    private Map<Pitch,TextureAtlas.AtlasRegion> regions = new HashMap<>();
    private Pitch pitch = Pitch.Flat;
    private float pitchDuration = -1;
    private final RouteDriver driver;
    private boolean cycle = false;

    AirEnemy(int plane, Route route) {
        super(ROUTE_TO_SPRITE, 2f);
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("enemy.atlas"));
        regions.put(Pitch.Left, textureAtlas.findRegion(String.format("00%d1", plane)));
        regions.put(Pitch.Flat, textureAtlas.findRegion(String.format("00%d2", plane)));
        regions.put(Pitch.Right, textureAtlas.findRegion(String.format("00%d3", plane)));

        updateFrame(pitch);

        driver = new RouteDriver(this, route);
        restart();
    }

    void setCycle(boolean cycle) {
        this.cycle = cycle;
    }

    private void updateFrame(Pitch newPitch) {
        pitch = newPitch;
        setImage(regions.get(pitch));
    }

    void restart() {
        driver.start();
    }

    @Override
    public void act (float delta) {
        super.act(delta);

        // Cycle execution
        if (cycle && driver.isOver()) {
            restart();
        }
        // update position
        driver.act(delta, SPEED);

        // update orientation
        float rotation = driver.getDirection();
        if (rotation != 0) {
            // update pitch
            pitchDuration = 0;
            if ((rotation > 0 && rotation < 180) || (rotation < -180)) {
                updateFrame(Pitch.Left);
            } else {
                updateFrame(Pitch.Right);
            }
        }
        if (pitchDuration >= 0) {
            pitchDuration += delta;
            if (pitchDuration > TIME_TO_PITCH) {
                updateFrame(Pitch.Flat);
                pitchDuration = -1;
            }
        }
    }

    boolean isOut() {
        return driver.isOver();
    }

}
