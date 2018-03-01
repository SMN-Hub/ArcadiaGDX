package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import net.smappz.arcadia.descriptors.ShotDescriptor;
import net.smappz.arcadia.util.LinearDriver;

public class Shoot extends SpriteActor {
    private final LinearDriver driver;
    private final ShotDescriptor descriptor;

    public Shoot(TextureAtlas.AtlasRegion region, Vector2 position, float orientation, ShotDescriptor descriptor) {
        super(0f, 4f);
        this.descriptor = descriptor;
        setImage(region);
        driver = new LinearDriver(this, position, orientation);
        driver.start();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // update position
        driver.act(delta, descriptor.getSpeed());

        // prune if no longer visible
        if (isOut())
            remove();
    }

    public int getPower() {
        return descriptor.getPower();
    }
}
