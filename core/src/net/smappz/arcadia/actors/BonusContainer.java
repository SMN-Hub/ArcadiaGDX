package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import net.smappz.arcadia.descriptors.PlaneDescriptor;
import net.smappz.arcadia.util.LinearDriver;

public class BonusContainer extends SpriteActor {
    private final Bonus bonus;
    private final LinearDriver driver;

    BonusContainer(TextureAtlas.AtlasRegion region, Vector2 position, Bonus bonus) {
        super(0f, 2f, 1f);
        this.bonus = bonus;
        setImage(region);
        driver = new LinearDriver(this, position, -90, false);
        driver.start();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // update position
        driver.act(delta, -Scroller.SCROLL_SPEED);

        // prune if no longer visible
        if (isOut())
            remove();
    }

    public void apply(AirFighter fighter) {
        PlaneDescriptor descriptor = fighter.getDescriptor();
        switch (bonus) {
            case UP_P: // power up
                descriptor.increaseFrequency();
                break;
            case UP_M: // motor/speed up
                descriptor.increaseSpeed();
                break;
            case UP_H: // health up
                fighter.increaseCurrentLife();
                break;
            case UP_R: // Laser boost
                descriptor.increaseShoot();
                break;
            case WP_3: // additional weapon
                fighter.increaseCannons();
                break;
        }
    }
}
