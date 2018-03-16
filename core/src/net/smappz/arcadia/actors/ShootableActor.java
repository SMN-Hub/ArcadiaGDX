package net.smappz.arcadia.actors;

import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.smappz.arcadia.descriptors.PlaneDescriptor;

abstract class ShootableActor extends SpriteActor {
    private int currentLife = 0;
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

    protected void reset() {
        setVisible(true);
        setTouchable(Touchable.enabled);
        currentLife = descriptor.getLife();
    }

    public PlaneDescriptor getDescriptor() {
        return descriptor;
    }
}
