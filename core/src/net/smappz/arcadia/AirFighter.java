package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.smappz.arcadia.util.FloatUtil;
import net.smappz.arcadia.util.ManualDriver;
import net.smappz.arcadia.util.Point;

import java.util.HashMap;
import java.util.Map;


class AirFighter extends Actor {
    private static final Point INITIAL_POSITION = new Point(1000, 200);
    private static final float SPEED = 800.f;
    private static final float PITCH_LEVEL_1 = 100f;
    private static final float PITCH_LEVEL_2 = 200f;
    private static final float SHOOT_FREQUENCY = 1f;
    private static final Point SHOOT_SPEED = new Point(-1000f, 0f);

    private Sprite sprite;
    private Pitch pitch = Pitch.Flat;
    private Map<Pitch,TextureAtlas.AtlasRegion> regions = new HashMap<>();
    private boolean shooting = false;
    private float lastShoot = 0f;
    private final GameListener listener;
    private final ManualDriver driver;

    AirFighter(GameListener listener) {
        this.listener = listener;
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("PlanesSmall.atlas"));
        regions.put(Pitch.Left2, textureAtlas.findRegion("0001"));
        regions.put(Pitch.Left, textureAtlas.findRegion("0002"));
        regions.put(Pitch.Flat, textureAtlas.findRegion("0003"));
        regions.put(Pitch.Right, textureAtlas.findRegion("0004"));
        regions.put(Pitch.Right2, textureAtlas.findRegion("0005"));

        sprite = new Sprite(regions.get(pitch));
        sprite.scale(1f);

        driver = new ManualDriver(this, INITIAL_POSITION);
        driver.start();
    }

    void setShooting(boolean shooting) {
        this.shooting = shooting;
        lastShoot = SHOOT_FREQUENCY;
    }

    void moveTo(float x, float y) {
        driver.setTarget(x, y);
    }

    private void updateFrame(Pitch newPitch) {
        pitch = newPitch;
        sprite.setRegion(regions.get(pitch));
    }

    @Override
    protected void positionChanged () {
        sprite.setCenter(getX(), getY());
    }

    @Override
    public void act (float delta) {
        super.act(delta);

        // shoot
        lastShoot += delta;
        if (lastShoot > SHOOT_FREQUENCY) {
            // canon position
            Point canon = new Point(sprite.getX(), sprite.getY() + sprite.getHeight()/2);
            listener.friendlyShoot(canon, SHOOT_SPEED);
            lastShoot = 0;
        }

        // update pitch
        float deltaY = driver.getDirection();
        Pitch newPitch = Pitch.Flat;
        if (deltaY > PITCH_LEVEL_2)
            newPitch = Pitch.Right2;
        else if (deltaY > PITCH_LEVEL_1)
            newPitch = Pitch.Right;
        else if (deltaY < -PITCH_LEVEL_2)
            newPitch = Pitch.Left2;
        else if (deltaY < -PITCH_LEVEL_1)
            newPitch = Pitch.Left;
        if (newPitch != pitch) {
            updateFrame(newPitch);
        }
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

}
