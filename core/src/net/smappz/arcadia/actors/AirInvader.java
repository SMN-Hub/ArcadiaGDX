package net.smappz.arcadia.actors;

import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.GameListener;
import net.smappz.arcadia.util.Route;
import net.smappz.arcadia.util.RouteDriver;

import static net.smappz.arcadia.ArcadiaGame.INSTANCE;


public class AirInvader extends ShootableActor {

    private float lastShoot = 0f;
    private final RouteDriver driver;

    @SuppressWarnings("DefaultLocale")
    AirInvader(int invader, Route route) {
        super(0.f, 0.3f, INSTANCE.getPlane(invader));
        setImage(ArcadiaGame.RESOURCES.getTextureRegion(String.format("invader%02d", invader-10)));

        driver = new RouteDriver(this, route);
        driver.setRotate(false);
        restart();
    }

    void restart() {
        reset();
        driver.start();
    }

    @Override
    public void act (float delta) {
        // Cycle execution
        if (driver.isOver()) {
            setVisible(false);
        }
        super.act(delta);

        // update position
        driver.act(delta, descriptor.getSpeed());

        if (!isVisible())
            return;

        // shoot
        if (descriptor.getShootId() != -1) {
            lastShoot += delta;
            if (lastShoot > descriptor.getShootFrequency()) {
                GameListener listener = ArcadiaGame.INSTANCE.getListener();
                listener.enemyShoot(descriptor.getShootId(), getPosition(), -90f);
                lastShoot = 0;
            }
        }
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
