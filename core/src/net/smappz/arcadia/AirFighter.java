package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.smappz.arcadia.util.FloatUtil;

import java.util.HashMap;
import java.util.Map;


class AirFighter extends Actor {
    private static final float SPEED = 800.f;
    private static final float PITCH_LEVEL_1 = 100f;
    private static final float PITCH_LEVEL_2 = 200f;

    private Sprite sprite;
    private float targetX = 1000;
    private float targetY = 100;
    private Pitch pitch = Pitch.Flat;
    private Map<Pitch,TextureAtlas.AtlasRegion> regions = new HashMap<>();

    AirFighter() {
        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("PlanesSmall.atlas"));
        regions.put(Pitch.Left2, textureAtlas.findRegion("0001"));
        regions.put(Pitch.Left, textureAtlas.findRegion("0002"));
        regions.put(Pitch.Flat, textureAtlas.findRegion("0003"));
        regions.put(Pitch.Right, textureAtlas.findRegion("0004"));
        regions.put(Pitch.Right2, textureAtlas.findRegion("0005"));

        sprite = new Sprite(regions.get(pitch));
        sprite.setPosition(targetX, targetY);
        sprite.scale(1f);
    }

    void moveTo(float x, float y) {
        targetX = x;
        targetY = y;
    }

    private void updateFrame(Pitch newPitch) {
        pitch = newPitch;
        sprite.setRegion(regions.get(pitch));
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void act (float delta) {
        super.act(delta);
        // position to center
        float curX = sprite.getX() + sprite.getWidth()/2;
        float curY = sprite.getY() + sprite.getHeight()/2;

        if (FloatUtil.equals(curX, targetX) && FloatUtil.equals(curY, targetY)) {
            return; // no need to move
        }

        float deltaX = targetX - curX;
        float deltaY = targetY - curY;

        // update pitch
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
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

}
