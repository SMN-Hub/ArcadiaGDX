package net.smappz.arcadia.util;

import net.smappz.arcadia.Shoot;
import net.smappz.arcadia.descriptors.PlaneDescriptor;

public class ShootableActor extends SpriteActor {
    private int currentLife = 0;
    private final PlaneDescriptor descriptor;

    public ShootableActor(PlaneDescriptor descriptor, float actorToSprite, float spriteScale) {
        super(actorToSprite, spriteScale);
        this.descriptor = descriptor;
        currentLife = descriptor.getLife();
    }

    public void onShoot(Shoot shoot) {
        setVisible(false);
    }
}
