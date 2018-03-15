package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.GameListener;
import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.RouteDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.smappz.arcadia.ArcadiaGame.INSTANCE;
import static net.smappz.arcadia.ArcadiaGame.RESOURCES;


public class AirEnemy extends ShootableActor {
    private static final float TIME_TO_PITCH = 1.f;
    private static final float TIME_TO_DESTROY = 0.2f;
    private static final float ROUTE_TO_SPRITE = 90f;

    private Map<Pitch,TextureAtlas.AtlasRegion> pitchRegions = new HashMap<>();
    private ArrayList<TextureAtlas.AtlasRegion> destroyRegions = new ArrayList<>();
    private Pitch pitch = Pitch.Flat;
    private float pitchDuration = -1;
    private int destroyStep = -1;
    private float destroyDuration = -1;
    private final RouteDriver driver;
    private float lastShoot = 0f;
    private boolean cycle = false;

    AirEnemy(int plane, Route route) {
        super(ROUTE_TO_SPRITE, 3f, INSTANCE.getPlane(plane));
        pitchRegions.put(Pitch.Left, RESOURCES.getTextureRegion(String.format("enemy00%d1", plane)));
        pitchRegions.put(Pitch.Flat, RESOURCES.getTextureRegion(String.format("enemy00%d2", plane)));
        pitchRegions.put(Pitch.Right, RESOURCES.getTextureRegion(String.format("enemy00%d3", plane)));

        destroyRegions.add(RESOURCES.getTextureRegion(String.format("enemy00%d4", plane)));
        destroyRegions.add(RESOURCES.getTextureRegion(String.format("enemy00%d5", plane)));
        destroyRegions.add(RESOURCES.getTextureRegion(String.format("enemy00%d6", plane)));

        updateFrame(pitch);

        driver = new RouteDriver(this, route);
        restart();
    }

    public void setCycle(boolean cycle) {
        this.cycle = cycle;
    }

    private void updateFrame(Pitch newPitch) {
        pitch = newPitch;
        setImage(pitchRegions.get(pitch));
    }

    private boolean stepDestroy() {
        destroyStep++;
        if (destroyStep < destroyRegions.size()) {
            setImage(destroyRegions.get(destroyStep));
            return true;
        } else {
            destroyStep = -1;
            return false;
        }
    }

    void restart() {
        pitchDuration = -1;
        destroyDuration = -1;
        destroyStep = -1;
        reset();
        updateFrame(Pitch.Flat);
        driver.start();
    }

    @Override
    public void act (float delta) {
        // Cycle execution
        if (driver.isOver()) {
            if (cycle) {
                restart();
            } else {
                setVisible(false);
            }
        }
        super.act(delta);

        // update position
        driver.act(delta, descriptor.getSpeed());

        if (!isVisible())
            return;

        // update destruction
        if (destroyDuration >= 0) {
            destroyDuration += delta;
            if (destroyDuration > TIME_TO_DESTROY) {
                if (!stepDestroy()) {
                    destroyDuration = -1;
                    setVisible(false);
                }
            }
            return; // skip rest
        }
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
        // shoot
        if (descriptor.getShootId() != -1) {
            lastShoot += delta;
            if (lastShoot > descriptor.getShootFrequency()) {
                GameListener listener = ArcadiaGame.INSTANCE.getListener();
                listener.enemyHomingShoot(descriptor.getShootId(), getPosition());
                lastShoot = 0;
            }
        }
    }

    @Override
    public boolean isOut() {
        return driver.isOver();
    }

    boolean isDestroyed() {
        return !isVisible() && destroyStep > 0;
    }

    @Override
    public void onDestroyed() {
        setTouchable(Touchable.disabled);
        // start destroy animation
        destroyDuration = 0;
        stepDestroy();
        ArcadiaGame.INSTANCE.getListener().onEnemyDestroy(this);
    }
}
