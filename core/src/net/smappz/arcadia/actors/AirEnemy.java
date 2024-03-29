package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.util.Driver;
import net.smappz.arcadia.util.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.smappz.arcadia.ArcadiaGame.INSTANCE;
import static net.smappz.arcadia.ArcadiaGame.RESOURCES;


public class AirEnemy extends ShootableActor {
    private static final float TIME_TO_PITCH = 1.f;
    private static final float TIME_TO_DESTROY = 0.2f;
    private static final float ROUTE_TO_SPRITE = 90f;

    private final Map<Pitch,TextureAtlas.AtlasRegion> pitchRegions = new HashMap<>();
    private final ArrayList<TextureAtlas.AtlasRegion> destroyRegions = new ArrayList<>();
    private Pitch pitch = Pitch.Flat;
    private float pitchDuration = -1;
    private int destroyStep = -1;
    private float destroyDuration = -1;
    private final Driver driver;

    @SuppressWarnings("DefaultLocale")
    AirEnemy(int plane, Route route) {
        super(ROUTE_TO_SPRITE, 3f, INSTANCE.getPlane(plane));
        pitchRegions.put(Pitch.Left, RESOURCES.getTextureRegion(String.format("enemy00%d1", plane)));
        pitchRegions.put(Pitch.Flat, RESOURCES.getTextureRegion(String.format("enemy00%d2", plane)));
        pitchRegions.put(Pitch.Right, RESOURCES.getTextureRegion(String.format("enemy00%d3", plane)));

        destroyRegions.add(RESOURCES.getTextureRegion(String.format("enemy00%d4", plane)));
        destroyRegions.add(RESOURCES.getTextureRegion(String.format("enemy00%d5", plane)));
        destroyRegions.add(RESOURCES.getTextureRegion(String.format("enemy00%d6", plane)));

        updateFrame(pitch);

        driver = route.createDriver(this);
        reset();
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

    @Override
    public void reset() {
        pitchDuration = -1;
        destroyDuration = -1;
        destroyStep = -1;
        super.reset();
        updateFrame(Pitch.Flat);
        driver.start();
    }

    @Override
    public void act (float delta) {
        if (!isVisible())
            return;

        if (isOut()) {
            setVisible(false);
            return;
        }

        // update position
        driver.act(delta, descriptor.getSpeed());
        super.act(delta);
        autoShoot(delta);

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
