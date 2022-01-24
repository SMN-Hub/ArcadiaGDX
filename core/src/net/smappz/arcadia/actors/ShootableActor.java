package net.smappz.arcadia.actors;

import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.GameListener;
import net.smappz.arcadia.descriptors.PlaneDescriptor;

public abstract class ShootableActor extends SpriteActor {
    private int currentLife;
    private float lastShoot = 0f;
    protected final PlaneDescriptor descriptor;

    protected ShootableActor(float actorToSprite, float spriteScale, PlaneDescriptor descriptor) {
        this(actorToSprite, spriteScale, 1, descriptor);
    }

    protected ShootableActor(float actorToSprite, float spriteScale, float actorScale, PlaneDescriptor descriptor) {
        super(actorToSprite, spriteScale, actorScale);
        this.descriptor = descriptor.clone();
        currentLife = descriptor.getLife();
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public void increaseCurrentLife() {
        currentLife += 30;
        if (currentLife > descriptor.getLife())
            currentLife = descriptor.getLife();
    }

    public void onShoot(Shoot shoot) {
        onDamage(shoot.getPower());
    }

    public void onDamage(int damage) {
        currentLife -= damage;
        if (currentLife <= 0) {
            currentLife = 0;
            onDestroyed();
        }
    }

    protected void onDestroyed() {
        setVisible(false);
        setTouchable(Touchable.disabled);
    }

    public void reset() {
        setVisible(true);
        setTouchable(Touchable.enabled);
        currentLife = descriptor.getLife();
    }

    public PlaneDescriptor getDescriptor() {
        return descriptor;
    }

    protected void autoShoot(float delta) {
        // shoot
        if (descriptor.getShootId() != -1) {
            lastShoot += delta;
            if (lastShoot > descriptor.getShootFrequency()) {
                GameListener listener = ArcadiaGame.INSTANCE.getListener();
                if (listener != null) {
                    listener.enemyShoot(descriptor.getShootId(), getPosition(), -90f);
                }
                lastShoot = 0;
            }
        }
    }

}
