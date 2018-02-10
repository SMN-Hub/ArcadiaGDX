package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.smappz.arcadia.util.Driver;
import net.smappz.arcadia.util.Route;


class AirEnemy extends Actor {
    private enum Pitch {
        Left,
        Flat,
        Right;

        private TextureAtlas.AtlasRegion region;
        public TextureAtlas.AtlasRegion getRegion() {
            return region;
        }
        public void setRegion(TextureAtlas.AtlasRegion region) {
            this.region = region;
        }
    }

    private static final float SPEED = 400.f;
    private static final float TIME_TO_PITCH = 0.2f;
    private static final float ROUTE_TO_SPRITE = 90f;

    private Sprite sprite;
    private Pitch pitch = Pitch.Flat;
    private float pitchDuration = -1;
    private final Driver driver;

    AirEnemy(int plane, Route route) {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("enemy.atlas"));
        Pitch.Left.setRegion(textureAtlas.findRegion(String.format("00%d1", plane)));
        Pitch.Flat.setRegion(textureAtlas.findRegion(String.format("00%d2", plane)));
        Pitch.Right.setRegion(textureAtlas.findRegion(String.format("00%d3", plane)));

        driver = new Driver(route);

        sprite = new Sprite(pitch.getRegion());
        sprite.setPosition(driver.getPosition().getX(), driver.getPosition().getY());
        sprite.scale(2f);

        restart();
    }

    private void updateFrame(Pitch newPitch) {
        pitch = newPitch;
        sprite.setRegion(pitch.getRegion());
    }

    private void restart() {
        driver.start();
        sprite.setRotation(driver.getAngle() + ROUTE_TO_SPRITE);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void act (float delta) {
        super.act(delta);

        // Cycle execution
        if (driver.isOver()) {
            restart();
        }
        // update position
        driver.act(delta, SPEED);
        // center to sprite position
        float curX = driver.getPosition().getX() - sprite.getWidth()/2;
        float curY =  driver.getPosition().getY() - sprite.getHeight()/2;
        sprite.setPosition(curX, curY);

        // update orientation
        float rotation = driver.getRotation();
        if (rotation != 0) {
            sprite.setRotation(driver.getAngle() + ROUTE_TO_SPRITE);

            // update pitch
            pitchDuration = 0;
            if ((rotation > 0 && rotation < 180) || (rotation < -180)) {
                updateFrame(Pitch.Left);
            } else {
                updateFrame(Pitch.Right);
            }
        }
        if (pitchDuration >= 0) {
            pitchDuration += delta;
            if (pitchDuration > TIME_TO_PITCH) {
                updateFrame(Pitch.Flat);
                pitchDuration = -1;
            }
        }
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

}
