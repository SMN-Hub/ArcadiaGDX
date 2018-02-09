package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;


class AirFighter extends Actor {
    private enum Pitch {
        Left2,
        Left1,
        Flat,
        Right1,
        Right2;

        private TextureAtlas.AtlasRegion region;
        public TextureAtlas.AtlasRegion getRegion() {
            return region;
        }
        public void setRegion(TextureAtlas.AtlasRegion region) {
            this.region = region;
        }
    }

    private static final float SPEED = 800.f;
    private static final float PITCH_LEVEL_1 = 100f;
    private static final float PITCH_LEVEL_2 = 200f;

    private Sprite sprite;
    private float targetX = 120;
    private float targetY = 100;
    private Pitch pitch = Pitch.Flat;

    AirFighter() {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("PlanesSmall.atlas"));
        Pitch.Left2.setRegion(textureAtlas.findRegion("0001"));
        Pitch.Left1.setRegion(textureAtlas.findRegion("0002"));
        Pitch.Flat.setRegion(textureAtlas.findRegion("0003"));
        Pitch.Right1.setRegion(textureAtlas.findRegion("0004"));
        Pitch.Right2.setRegion(textureAtlas.findRegion("0005"));

        sprite = new Sprite(pitch.getRegion());
        sprite.setPosition(targetX, targetY);
        sprite.scale(1f);
    }

    void moveTo(float x, float y) {
        targetX = x;
        targetY = y;
    }

    private void updateFrame(Pitch newPitch) {
        pitch = newPitch;
        sprite.setRegion(pitch.getRegion());
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void act (float delta) {
        super.act(delta);
        // position to center
        float curX = sprite.getX() + sprite.getWidth()/2;
        float curY = sprite.getY() + sprite.getHeight()/2;

        if (equals(curX, targetX) && equals(curY, targetY)) {
            return; // no need to move
        }

        float deltaX = targetX - curX;
        float deltaY = targetY - curY;

        // update pitch
        Pitch newPitch = Pitch.Flat;
        if (deltaY > PITCH_LEVEL_2)
            newPitch = Pitch.Right2;
        else if (deltaY > PITCH_LEVEL_1)
            newPitch = Pitch.Right1;
        else if (deltaY < -PITCH_LEVEL_2)
            newPitch = Pitch.Left2;
        else if (deltaY < -PITCH_LEVEL_1)
            newPitch = Pitch.Left1;
        if (newPitch != pitch) {
            updateFrame(newPitch);
        }

        // update placement
        float fullDistance = (float)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        float maxDistance = SPEED * delta;
        if (fullDistance > maxDistance) {
            curX += deltaX * maxDistance / fullDistance;
            curY += deltaY * maxDistance / fullDistance;
        } else {
            curX = targetX;
            curY = targetY;
        }
        // center to sprite position
        curX -= sprite.getWidth()/2;
        curY -= sprite.getHeight()/2;
        sprite.setPosition(curX, curY);
        System.out.println(String.format("x=%f, y=%f", curX, curY));
    }

    private static boolean equals(float x, float y) {
        return Math.abs(x - y) < 0.02f;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

}
