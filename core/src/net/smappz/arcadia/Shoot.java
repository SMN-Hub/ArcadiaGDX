package net.smappz.arcadia;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import net.smappz.arcadia.descriptors.ShotDescriptor;
import net.smappz.arcadia.util.LinearDriver;
import net.smappz.arcadia.util.SpriteActor;

public class Shoot extends SpriteActor {
    private final LinearDriver driver;
    private final ShotDescriptor descriptor;

    Shoot(TextureAtlas.AtlasRegion region, Vector2 position, float orientation, ShotDescriptor descriptor) {
        super(90f, 4f);
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
    }

    public int getPower() {
        return descriptor.getPower();
    }
}
