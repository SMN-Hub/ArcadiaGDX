package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.RouteDriver;

import java.util.HashMap;
import java.util.Map;


class AirEnemy extends Actor {
    private static final float SPEED = 400.f;
    private static final float TIME_TO_PITCH = 1.f;
    private static final float ROUTE_TO_SPRITE = 90f;

    private Sprite sprite;
    private Map<Pitch,TextureAtlas.AtlasRegion> regions = new HashMap<>();
    private Pitch pitch = Pitch.Flat;
    private float pitchDuration = -1;
    private final RouteDriver driver;
    private boolean cycle = false;

    AirEnemy(int plane, Route route) {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("enemy.atlas"));
        regions.put(Pitch.Left, textureAtlas.findRegion(String.format("00%d1", plane)));
        regions.put(Pitch.Flat, textureAtlas.findRegion(String.format("00%d2", plane)));
        regions.put(Pitch.Right, textureAtlas.findRegion(String.format("00%d3", plane)));

        sprite = new Sprite(regions.get(pitch));
        sprite.scale(2f);

        driver = new RouteDriver(this, route);
        restart();
    }

    public void setCycle(boolean cycle) {
        this.cycle = cycle;
    }

    private void updateFrame(Pitch newPitch) {
        pitch = newPitch;
        sprite.setRegion(regions.get(pitch));
    }

    void restart() {
        driver.start();
    }

    @Override
    protected void positionChanged () {
        sprite.setCenter(getX(), getY());
    }

    @Override
    protected void rotationChanged () {
        sprite.setRotation(getRotation() + ROUTE_TO_SPRITE);
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

    @Override
    public void draw (Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    boolean isOut() {
        return driver.isOver();
    }

}
