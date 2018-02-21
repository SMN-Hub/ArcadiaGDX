package net.smappz.arcadia.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.graphics.g2d.Batch.*;

public abstract class SpriteActor extends Actor {
    private final float actorToSprite;
    private final float spriteScale;
    private Sprite sprite = null;

    protected SpriteActor(float actorToSprite, float spriteScale) {
        this.actorToSprite = actorToSprite;
        this.spriteScale = spriteScale;
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
        setWidth(getImageWidth());
        setHeight(getImageHeight());
//        sprite.setOriginCenter();
//        setOrigin(Align.center);
        if (initial) {
            setRotation(0); // initial rotation
        }
    }

    public void setPosition(Vector2 position) {
        sprite.setCenter(position.x, position.y);
        // center to top-left corner
        Vector2 topLeftCoords = new Vector2(-getImageWidth() / 2, -getImageHeight() / 2);
        topLeftCoords.rotate(getRotation());
        setPosition(position.x + topLeftCoords.x, position.y + topLeftCoords.y);
    }

    public Vector2 getPosition() {
        // center position
        Vector2 centerCoords = new Vector2(getImageWidth() / 2, getImageHeight() / 2);
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
}
