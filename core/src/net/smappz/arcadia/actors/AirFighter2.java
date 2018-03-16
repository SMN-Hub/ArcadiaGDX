package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.smappz.arcadia.ArcadiaGame;

import static com.badlogic.gdx.graphics.g2d.Batch.X1;
import static com.badlogic.gdx.graphics.g2d.Batch.X2;
import static com.badlogic.gdx.graphics.g2d.Batch.X3;
import static com.badlogic.gdx.graphics.g2d.Batch.X4;
import static com.badlogic.gdx.graphics.g2d.Batch.Y1;
import static com.badlogic.gdx.graphics.g2d.Batch.Y2;
import static com.badlogic.gdx.graphics.g2d.Batch.Y3;
import static com.badlogic.gdx.graphics.g2d.Batch.Y4;
import static net.smappz.arcadia.ArcadiaGame.RESOURCES;


public class AirFighter2 extends AirFighter {
    private static final float UPDATE_FREQ = 0.04f;
    private float destroyDuration = -1;
    private float pitchDuration = 0;

    public AirFighter2() {
        this(360, 200);
    }

    public AirFighter2(float initialX, float initialY) {
        super(0f, 1f, 0.5f, ArcadiaGame.INSTANCE.getPlane(0), initialX, initialY);

        setImage(ArcadiaGame.RESOURCES.getTextureRegion("terron000"));
        start();
    }

    @Override
    protected void updateFrame(Pitch newPitch) {
        // nop
    }

    @Override
    public void act (float delta) {
        if (!isVisible())
            return;

        if (destroyDuration >= 0) {
            // update destruction
            destroyDuration += delta;
            if (!stepDestroy()) {
                destroyDuration = -1;
                setVisible(false);
                ArcadiaGame.INSTANCE.getListener().onFighterDestroy();
            }
        } else {
            // update pitch
            pitchDuration += delta;
            stepPitch();
            super.act(delta);
        }
    }

    @Override
    protected void onDestroyed() {
        setTouchable(Touchable.disabled);
        // start destroy animation
        destroyDuration = 0;
        stepDestroy();
    }

    private boolean stepDestroy() {
        // destroy animation from 50 to 62 then from 100 to 115
        int destroyStep = Math.round(destroyDuration / UPDATE_FREQ);
        if (destroyStep > 28) {
            return false;
        } else if (destroyStep > 13) {
            setImage(getRegion(destroyStep + 100 - 13));
            return true;
        } else {
            setImage(getRegion(destroyStep + 50));
            return true;
        }
    }

    private void stepPitch() {
        // pitch animation from 0 to 49
        int pitchStep = Math.round(pitchDuration / UPDATE_FREQ);
        if (pitchStep > 49) {
            pitchStep = 0;
            pitchDuration = 0;
        }
        setImage(getRegion(pitchStep));
    }

    private TextureAtlas.AtlasRegion getRegion(int step) {
        return RESOURCES.getTextureRegion(String.format("terron%03d", step));
    }

    @Override
    protected Vector2 centerCanon() {
        return new Vector2(getX() + getWidth() / 2, getY() + getHeight());
    }
    @Override
    protected Vector2 leftCanon() {
        return new Vector2(getX(), getY() + getHeight() / 2);
    }
    @Override
    protected Vector2 rightCanon() {
        return new Vector2(getX() + getWidth(), getY() + getHeight() / 2);
    }
    @Override
    protected Vector2 backCanon() {
        return new Vector2(getX() + getHeight() / 2, getY());
    }

    @Override
    public float[] getVertices() {
        float[] vertices = new float[8];
        vertices[0] = getX();
        vertices[1] = getY();
        vertices[2] = getX();
        vertices[3] = getY() + getHeight();
        vertices[4] = getX() + getWidth();
        vertices[5] = getY() + getHeight();
        vertices[6] = getX() + getWidth();
        vertices[7] = getY();
        return vertices;
    }
}
