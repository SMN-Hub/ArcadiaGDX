package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static net.smappz.arcadia.AbstractScreen.HEIGHT;
import static net.smappz.arcadia.AbstractScreen.WIDTH;
import static net.smappz.arcadia.ArcadiaGame.RESOURCES;

public class Scroller extends Actor {
    private static final float SCROLL_SPEED = -40;
    private Texture background;

    public Scroller() {
        background = RESOURCES.getBackGround();
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void act (float delta) {
        super.act(delta);
        moveBy(0, SCROLL_SPEED * delta);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        batch.draw(background, 0, 0, 0, (int) getY(), WIDTH, HEIGHT);
    }

}