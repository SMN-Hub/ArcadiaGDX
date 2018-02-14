package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import net.smappz.arcadia.util.ManualDriver;
import net.smappz.arcadia.util.SpriteActor;

import java.util.HashMap;
import java.util.Map;


class AirFighter extends SpriteActor {
    private static final Vector2 INITIAL_POSITION = new Vector2(1000, 200);
    private static final float SPEED = 800.f;
    private static final float PITCH_LEVEL_1 = 100f;
    private static final float PITCH_LEVEL_2 = 200f;
    private static final float SHOOT_FREQUENCY = 0.2f;

    private Pitch pitch = Pitch.Flat;
    private Map<Pitch,TextureAtlas.AtlasRegion> regions = new HashMap<>();
    private boolean shooting = false;
    private float lastShoot = 0f;
    private final GameListener listener;
    private final ManualDriver driver;

    AirFighter(GameListener listener) {
        super(0f, 2f);
        this.listener = listener;
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("PlanesSmall.atlas"));
        regions.put(Pitch.Left2, textureAtlas.findRegion("0001"));
        regions.put(Pitch.Left, textureAtlas.findRegion("0002"));
        regions.put(Pitch.Flat, textureAtlas.findRegion("0003"));
        regions.put(Pitch.Right, textureAtlas.findRegion("0004"));
        regions.put(Pitch.Right2, textureAtlas.findRegion("0005"));

        setImage(regions.get(pitch));

        driver = new ManualDriver(this, INITIAL_POSITION);
        driver.start();
    }

    void setShooting(boolean shooting) {
        this.shooting = shooting;
        lastShoot = SHOOT_FREQUENCY;
    }

    void moveTo(float x, float y) {
        driver.setTarget(x, y);
    }

    private void updateFrame(Pitch newPitch) {
        pitch = newPitch;
        setImage(regions.get(pitch));
    }

    @Override
    public void act (float delta) {
        super.act(delta);

        // update position
        driver.act(delta, SPEED);

        // shoot
        lastShoot += delta;
        if (lastShoot > SHOOT_FREQUENCY) {
            if (shooting) {
                // canon position
                listener.friendlyShoot(centerCanon(), 180f);
                listener.friendlyShoot(leftCanon(), 180f);
                listener.friendlyShoot(rightCanon(), 180f);
            }
            lastShoot = 0;
        }

        // update pitch
        float deltaY = driver.getDirection();
        Pitch newPitch = Pitch.Flat;
        if (deltaY > PITCH_LEVEL_2)
            newPitch = Pitch.Right2;
        else if (deltaY > PITCH_LEVEL_1)
            newPitch = Pitch.Right;
        else if (deltaY < -PITCH_LEVEL_2)
            newPitch = Pitch.Left2;
        else if (deltaY < -PITCH_LEVEL_1)
            newPitch = Pitch.Left;
        if (newPitch != pitch) {
            updateFrame(newPitch);
        }
    }

    private Vector2 centerCanon() {
        return new Vector2(getX(Align.left), getY(Align.center));
    }
    private Vector2 leftCanon() {
        return new Vector2(getX(Align.center), getY(Align.bottom));
    }
    private Vector2 rightCanon() {
        return new Vector2(getX(Align.center), getY(Align.top));
    }
}
