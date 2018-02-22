package net.smappz.arcadia.actors;

import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.smappz.arcadia.descriptors.PlaneDescriptor;

abstract class ShootableActor extends SpriteActor {
    private int currentLife = 0;
    protected final PlaneDescriptor descriptor;

    protected ShootableActor(float actorToSprite, float spriteScale, PlaneDescriptor descriptor) {
        super(actorToSprite, spriteScale);
        this.descriptor = descriptor;
        currentLife = descriptor.getLife();
    }

    public void onShoot(Shoot shoot) {
        currentLife -= shoot.getPower();
        if (currentLife <= 0) {
            onDestroyed();
        }
    }

    protected void onDestroyed() {
        setVisible(false);
        setTouchable(Touchable.disabled);
    }

    protected void reset() {
        setVisible(true);
        setTouchable(Touchable.enabled);
        currentLife = descriptor.getLife();
    }
}
