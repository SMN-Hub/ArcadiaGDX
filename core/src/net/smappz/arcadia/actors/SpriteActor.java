package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.smappz.arcadia.AbstractScreen;

import static com.badlogic.gdx.graphics.g2d.Batch.X1;
import static com.badlogic.gdx.graphics.g2d.Batch.X2;
import static com.badlogic.gdx.graphics.g2d.Batch.X3;
import static com.badlogic.gdx.graphics.g2d.Batch.X4;
import static com.badlogic.gdx.graphics.g2d.Batch.Y1;
import static com.badlogic.gdx.graphics.g2d.Batch.Y2;
import static com.badlogic.gdx.graphics.g2d.Batch.Y3;
import static com.badlogic.gdx.graphics.g2d.Batch.Y4;

public abstract class SpriteActor extends Actor {
    private final float actorToSprite;
    private final float spriteScale;
    private final float actorScale;
    private Sprite sprite = null;

    protected SpriteActor(float actorToSprite, float spriteScale, float actorScale) {
        this.actorToSprite = actorToSprite;
        this.spriteScale = spriteScale;
        this.actorScale = actorScale;
    }

    private float getImageWidth() {
        //return ((Math.abs(actorToSprite) == 90f) ? sprite.getHeight() : sprite.getWidth()) * spriteScale;
        return sprite.getWidth() * spriteScale;
    }

    private float getImageHeight() {
        //return ((Math.abs(actorToSprite) == 90f) ? sprite.getWidth() : sprite.getHeight()) * spriteScale;
        return sprite.getHeight() * spriteScale;
    }

    protected void setImage(TextureRegion region) {
        boolean initial = (sprite == null);
        if (sprite == null) {
            sprite = new Sprite(region);
        } else {
            sprite.setRegion(region);
        }
        sprite.setScale(spriteScale);
        setWidth(getImageWidth() * actorScale);
        setHeight(getImageHeight() * actorScale);
//        sprite.setOriginCenter();
//        setOrigin(Align.center);
        if (initial) {
            setRotation(0); // initial rotation
        }
    }

    public void setPosition(Vector2 position) {
        sprite.setCenter(position.x, position.y);
        // center to top-left corner
        Vector2 topLeftCoords = new Vector2(-getWidth() / 2, -getHeight() / 2);
        topLeftCoords.rotate(getRotation());
        setPosition(position.x + topLeftCoords.x, position.y + topLeftCoords.y);
    }

    public Vector2 getPosition() {
        // center position
        Vector2 centerCoords = new Vector2(getWidth() / 2, getHeight() / 2);
        return localToStageCoordinates(centerCoords);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void setRotation (float degrees) {
        super.setRotation(degrees + actorToSprite);
        sprite.setRotation(getRotation());
    }

    public float[] getVertices() {
        float[] spriteVertices = sprite.getVertices();
        float[] vertices = new float[8];
        vertices[0] = spriteVertices[X1];
        vertices[1] = spriteVertices[Y1];
        vertices[2] = spriteVertices[X2];
        vertices[3] = spriteVertices[Y2];
        vertices[4] = spriteVertices[X3];
        vertices[5] = spriteVertices[Y3];
        vertices[6] = spriteVertices[X4];
        vertices[7] = spriteVertices[Y4];
        return vertices;
    }

    public boolean isOut() {
        Rectangle bounds = sprite.getBoundingRectangle();
        return bounds.getX() > AbstractScreen.WIDTH || bounds.getX() + bounds.getWidth() < 0
                || bounds.getY() > AbstractScreen.HEIGHT || bounds.getY() + bounds.getHeight() < 0;
    }
}
