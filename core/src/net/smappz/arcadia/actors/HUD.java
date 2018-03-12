package net.smappz.arcadia.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import static net.smappz.arcadia.ArcadiaGame.RESOURCES;

public class HUD extends Group {
    private Label label;

    public HUD() {
        Color fontColor = new Color(1, 1, 1, 0);
        Label.LabelStyle style = new Label.LabelStyle(RESOURCES.getFont(), fontColor);
        label = new Label("Arcadia GDX", style);
        label.setFontScale(0.25f);
        addActor(label);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

//        String hubText = String.format("Score: %d   Health: %d\nFPS: %d", Gdx.graphics.getFramesPerSecond());
        String hubText = String.format("FPS: %d", Gdx.graphics.getFramesPerSecond());
    }
}
