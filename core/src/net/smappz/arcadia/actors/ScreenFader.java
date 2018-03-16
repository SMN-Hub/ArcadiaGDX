package net.smappz.arcadia.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.smappz.arcadia.ArcadiaGame;

import static net.smappz.arcadia.AbstractScreen.HEIGHT;
import static net.smappz.arcadia.AbstractScreen.WIDTH;

class ScreenFader extends Actor {
    private boolean active = false;
    private final Texture fadePixel;

    ScreenFader() {
        fadePixel = ArcadiaGame.RESOURCES.getFadeTexture();
    }

    void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        if (active) {
            batch.setColor(0, 0, 0, 0.3f);
            batch.draw(fadePixel, 0, 0, WIDTH, HEIGHT);
        }
    }
}
