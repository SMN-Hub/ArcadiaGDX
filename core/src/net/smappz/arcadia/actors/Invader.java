package net.smappz.arcadia.actors;

import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.RouteDriver;

import static net.smappz.arcadia.ArcadiaGame.INSTANCE;


public class Invader extends ShootableActor {

    private final RouteDriver driver;

    @SuppressWarnings("DefaultLocale")
    Invader(int invader, Route route) {
        super(0.f, 0.3f, INSTANCE.getPlane(invader));
        setImage(ArcadiaGame.RESOURCES.getTextureRegion(String.format("invader%02d", invader-10)));

        driver = route.createDriver(this);
        driver.setRotate(false);
        reset();
    }

    @Override
    public void reset() {
        super.reset();
        driver.start();
    }

    @Override
    public void act (float delta) {
        if (!isVisible())
            return;

        // update position
        driver.act(delta, descriptor.getSpeed());

        if (isOut()) {
            setVisible(false);
            return;
        }
        super.act(delta);
    }

    @Override
    public boolean isOut() {
        return false;
    }

    @Override
    public void onDestroyed() {
        setTouchable(Touchable.disabled);
        setVisible(false);
        ArcadiaGame.INSTANCE.getListener().onEnemyDestroy(this);
    }
}
