package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
    private int cannonRange = 1;
    private final ManualDriver driver;

    public AirFighter() {
        this(360, 200);
    }

    public AirFighter(float initialX, float initialY) {
        super(-90f, 2f, ArcadiaGame.INSTANCE.getPlane(0));
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
                GameListener listener = ArcadiaGame.INSTANCE.getListener();
                // canon position
                listener.friendlyShoot(descriptor.getShootId(), centerCanon(), 90f);
                switch (cannonRange) {
                    case 5:
                        listener.friendlyShoot(3, backCanon(), -90f);
                    case 4:
                        listener.friendlyShoot(1, leftCanon(), 180f);
                        listener.friendlyShoot(1, rightCanon(), 0f);
                    case 3:
                        listener.friendlyShoot(1, leftCanon(), 135f);
                        listener.friendlyShoot(1, rightCanon(), 45f);
                    case 2:
                        listener.friendlyShoot(1, leftCanon(), 90f);
                        listener.friendlyShoot(1, rightCanon(), 90f);
                }
                //System.out.println("Plan pos x=" + getX() + " y=" + getY());
                //System.out.println("Shoot pos x=" + shoot.getX() + " y=" + shoot.getY());
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

    @Override
    public void onDamage(int damage) {
        super.onDamage(damage);
        ArcadiaGame.INSTANCE.getListener().onFighterDamage(damage);
    }

    @Override
    protected void onDestroyed() {
        // TODO destroy animation
        setVisible(false);
        setTouchable(Touchable.disabled);
        ArcadiaGame.INSTANCE.getListener().onFighterDestroy();
    }

    private Vector2 centerCanon() {
        return new Vector2(getX() + getHeight() / 2, getY());
    }
    private Vector2 leftCanon() {
        return new Vector2(getX(), getY() - getWidth() / 2);
    }
    private Vector2 rightCanon() {
        return new Vector2(getX() + getHeight(), getY() - getWidth() / 2);
    }
    private Vector2 backCanon() {
        return new Vector2(getX() + getHeight() / 2, getY() - getWidth());
    }

    public void increaseCannons() {
        if (cannonRange < 5)
            cannonRange++;
    }
}
