package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.GameListener;
import net.smappz.arcadia.descriptors.PlaneDescriptor;
import net.smappz.arcadia.util.ManualDriver;

import java.util.HashMap;
import java.util.Map;

import static net.smappz.arcadia.ArcadiaGame.RESOURCES;


public abstract class AirFighter extends ShootableActor {
    private static final float PITCH_LEVEL_1 = 100f;
    private static final float PITCH_LEVEL_2 = 200f;

    private boolean shooting = false;
    private float lastShoot = 0f;
    private int cannonRange = 1;
    private final ManualDriver driver;

    public AirFighter(float actorToSprite, float spriteScale, PlaneDescriptor descriptor, float initialX, float initialY) {
        super(actorToSprite, spriteScale, descriptor);

        driver = new ManualDriver(this, new Vector2(initialX, initialY));
        driver.start();
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
        lastShoot = descriptor.getShootFrequency();
    }

    public void moveTo(float x, float y) {
        if (isVisible())
            driver.setTarget(x, y);
    }

    protected abstract void updateFrame(Pitch newPitch);

    @Override
    public void act (float delta) {
        if (!isVisible())
            return;

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
        updateFrame(newPitch);
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
