package net.smappz.arcadia.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import net.smappz.arcadia.descriptors.LevelScore;

import java.util.Locale;

import static net.smappz.arcadia.AbstractScreen.HEIGHT;
import static net.smappz.arcadia.ArcadiaGame.RESOURCES;

public class HUD extends Group {
    private final LevelScore score;
    private final AirFighter fighter;
    private Label label;

    public HUD(LevelScore score, AirFighter fighter) {
        this.score = score;
        this.fighter = fighter;
        Color fontColor = new Color(1, 1, 1, 1);
        Label.LabelStyle style = new Label.LabelStyle(RESOURCES.getFont(), fontColor);
        label = new Label("Arcadia GDX", style);
        label.setFontScale(0.75f);
        label.setPosition(10, HEIGHT - label.getPrefHeight() - 10);
        addActor(label);
        label.toFront();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        String hudText = String.format(Locale.getDefault(), "Score: %010d             Health: %3d             FPS: %d",
                score.getScore(), fighter.getCurrentLife(), Gdx.graphics.getFramesPerSecond());
        label.setText(hudText);
    }
}
