package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import net.smappz.arcadia.ArcadiaGame;
import net.smappz.arcadia.util.FrameSet;

import static net.smappz.arcadia.ArcadiaGame.RESOURCES;


public class AirFighter2 extends AirFighter {
    private static final float PITCH_FREQ = 0.04f;
    private static final float DESTROY_FREQ = PITCH_FREQ * 1.5f;
    // pitch animation from 0 to 49
    private final FrameSet PITCH_FRAME = new FrameSet(PITCH_FREQ).cycle().addRange(0, 49);
    //private final FrameSet SHIELD_FRAME = new FrameSet(UPDATE_FREQ).cycle().addRange(63, 99);
    // destroy animation from 50 to 62 then from 100 to 115
    private final FrameSet DESTROY_FRAME = new FrameSet(DESTROY_FREQ).addRange(50, 62).addRange(100, 115);
    private boolean isDestroyed = false;

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

        if (isDestroyed) {
            // update destruction
            if (!stepDestroy(delta)) {
                setVisible(false);
                ArcadiaGame.INSTANCE.getListener().onFighterDestroy();
            }
        } else {
            // update pitch
            stepPitch(delta);
            super.act(delta);
        }
    }

    @Override
    protected void onDestroyed() {
        setTouchable(Touchable.disabled);
        // start destroy animation
        isDestroyed = true;
        stepDestroy(DESTROY_FREQ);
    }

    private boolean stepDestroy(float delta) {
        if (!DESTROY_FRAME.hasNext())
            return false;

        Integer destroyStep = DESTROY_FRAME.next(delta);
        if (destroyStep != null) {
            setImage(getRegion(destroyStep));
        }
        return true;
    }

    private void stepPitch(float delta) {
        Integer pitchStep = PITCH_FRAME.next(delta);
        if (pitchStep != null) {
            setImage(getRegion(pitchStep));
        }
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
