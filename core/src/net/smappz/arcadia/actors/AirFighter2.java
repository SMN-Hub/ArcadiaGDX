package net.smappz.arcadia.actors;

import net.smappz.arcadia.ArcadiaGame;


public class AirFighter2 extends AirFighter {
    private static final float TIME_TO_DESTROY = 0.2f;
    private int destroyStep = -1;
    private float destroyDuration = -1;

    public AirFighter2() {
        this(360, 200);
    }

    public AirFighter2(float initialX, float initialY) {
        super(-90f, 2f, ArcadiaGame.INSTANCE.getPlane(0), initialX, initialY);

        setImage(ArcadiaGame.RESOURCES.getTextureRegion("terron000"));
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
            if (destroyDuration > TIME_TO_DESTROY) {
                if (!stepDestroy()) {
                    destroyDuration = -1;
                    setVisible(false);
                }
            }
            return; // skip rest
        } else {
            // update pitch
        }
        super.act(delta);
    }

    @Override
    protected void onDestroyed() {
        super.onDestroyed();
        // start destroy animation
        destroyDuration = 0;
        stepDestroy();
    }

    private boolean stepDestroy() {
        // destroy animation from 50 to 62 then from 100 to 115
        destroyStep++;
//        if (destroyStep < destroyRegions.size()) {
//            setImage(destroyRegions.get(destroyStep));
//            return true;
//        } else {
//            destroyStep = -1;
//            return false;
//        }
        return false;
    }
}
