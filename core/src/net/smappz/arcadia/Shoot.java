package net.smappz.arcadia;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import net.smappz.arcadia.util.LinearDriver;
import net.smappz.arcadia.util.SpriteActor;

public class Shoot extends SpriteActor {
    private static final float SPEED = 500.f;

    private final LinearDriver driver;

    Shoot(TextureAtlas.AtlasRegion region, Vector2 position, float orientation) {
        super(90f, 4f);
        setImage(region);
        driver = new LinearDriver(this, position, orientation);
        driver.start();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // update position
        driver.act(delta, SPEED);
    }
}
