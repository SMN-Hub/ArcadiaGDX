package net.smappz.arcadia.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.smappz.arcadia.GameScreen;

public class Scroller extends Actor {
    private static final float SCROLL_SPEED = -40;
    private Texture background;

    public Scroller() {
        background = new Texture(Gdx.files.internal("space_0.png"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void act (float delta) {
        super.act(delta);
        this.moveBy(SCROLL_SPEED * delta, 0);
        if (getX() > GameScreen.WIDTH || getX() < -GameScreen.WIDTH)
            setX(((int)getX()) % GameScreen.WIDTH);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.draw(background, 0, 0, (int) getX(), 0, GameScreen.WIDTH, GameScreen.HEIGHT);
    }

}
