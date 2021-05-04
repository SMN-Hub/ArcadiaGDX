package net.smappz.arcadia.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.GameListener;

import static net.smappz.arcadia.ArcadiaGame.INSTANCE;


public class Invader extends ShootableActor {

    private float lastShoot = 0f;
    private final Vector2 startPoint;

    @SuppressWarnings("DefaultLocale")
    Invader(int invader, Vector2 startPoint) {
        super(0.f, 0.3f, INSTANCE.getPlane(invader));
        setImage(ArcadiaGame.RESOURCES.getTextureRegion(String.format("invader%02d", invader-10)));

        this.startPoint = startPoint;
        restart();
    }

    void restart() {
        reset();
        setPosition(startPoint);
    }

    @Override
    public void act (float delta) {
        if (!isVisible())
            return;

        // Position already updated bu InvaderSquadron
        super.act(delta);

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
