package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.GameListener;
import net.smappz.arcadia.util.ManualDriver;

import java.util.HashMap;
import java.util.Map;

import static net.smappz.arcadia.ArcadiaGame.RESOURCES;


public class AirFighter extends ShootableActor {
    private static final float PITCH_LEVEL_1 = 100f;
    private static final float PITCH_LEVEL_2 = 200f;

    private Pitch pitch = Pitch.Flat;
    private Map<Pitch,TextureAtlas.AtlasRegion> regions = new HashMap<>();
    private boolean shooting = false;
    private float lastShoot = 0f;
    private final GameListener listener;
    private final ManualDriver driver;

    public AirFighter(GameListener listener) {
        this(listener, 900, 360);
    }

    public AirFighter(GameListener listener, float initialX, float initialY) {
        super(0f, 2f, ArcadiaGame.INSTANCE.getPlane(0));
        this.listener = listener;
        regions.put(Pitch.Left2, RESOURCES.getTextureRegion("plane01"));
        regions.put(Pitch.Left, RESOURCES.getTextureRegion("plane02"));
        regions.put(Pitch.Flat, RESOURCES.getTextureRegion("plane03"));
        regions.put(Pitch.Right, RESOURCES.getTextureRegion("plane04"));
        regions.put(Pitch.Right2, RESOURCES.getTextureRegion("plane05"));

        setImage(regions.get(pitch));

        driver = new ManualDriver(this, new Vector2(initialX, initialY));
        driver.start();
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
        lastShoot = descriptor.getShootFrequency();
    }

    public void moveTo(float x, float y) {
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
        driver.act(delta, descriptor.getSpeed());

        // shoot
        lastShoot += delta;
        if (lastShoot > descriptor.getShootFrequency()) {
            if (shooting) {
                // canon position
                listener.friendlyShoot(1, centerCanon(), 180f);
                listener.friendlyShoot(1, leftCanon(), 180f);
                listener.friendlyShoot(1, rightCanon(), 180f);
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
