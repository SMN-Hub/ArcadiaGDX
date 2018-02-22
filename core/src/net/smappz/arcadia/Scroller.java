package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

class Scroller extends Actor {
    private static final float SCROLL_SPEED = 40;
    private Texture background;

    Scroller() {
        background = new Texture(Gdx.files.internal("space_0.png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        toBack();
    }

    @Override
    public void act (float delta) {
        super.act(delta);
        this.moveBy(SCROLL_SPEED * delta, 0);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.draw(background, 0, 0, (int) getX(), 0, GameScreen.WIDTH, GameScreen.HEIGHT);
    }

}
