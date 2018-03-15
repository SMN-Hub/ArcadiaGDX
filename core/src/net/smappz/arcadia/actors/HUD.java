package net.smappz.arcadia.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import net.smappz.arcadia.descriptors.LevelScore;

import java.util.Locale;

import static net.smappz.arcadia.AbstractScreen.HEIGHT;
import static net.smappz.arcadia.AbstractScreen.WIDTH;
import static net.smappz.arcadia.ArcadiaGame.RESOURCES;

public class HUD extends Group {
    private final LevelScore score;
    private final AirFighter fighter;
    private final Label label;
    private final Label gameOver;
    private final ScreenFader fader;

    public HUD(LevelScore score, AirFighter fighter) {
        this.score = score;
        this.fighter = fighter;

        fader = new ScreenFader();
        addActor(fader);
        fader.setZIndex(1000);


        BitmapFont font = RESOURCES.getFont();
        Label.LabelStyle style = new Label.LabelStyle(font, new Color(1, 1, 1, 1));
        label = new Label("Arcadia GDX", style);
        label.setFontScale(0.75f);
        label.setPosition(10, HEIGHT - label.getPrefHeight() - 10);
        addActor(label);
        label.toFront();

        gameOver = new Label("Game Over", new Label.LabelStyle(font, new Color(0xff6c11ff)));
        gameOver.setFontScale(2f);
        gameOver.setPosition(WIDTH / 2 - gameOver.getPrefWidth() / 2, 800);
        gameOver.setVisible(false);
        addActor(gameOver);
        gameOver.setZIndex(1000);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        String hudText = String.format(Locale.getDefault(), "Score: %010d             Health: %3d             FPS: %d",
                score.getScore(), fighter.getCurrentLife(), Gdx.graphics.getFramesPerSecond());
        label.setText(hudText);
    }

    public void gameOver() {
        fader.setActive(true);
        gameOver.setVisible(true);
    }

    public void finish() {
        gameOver.setText("Level complete");
        gameOver.setPosition(WIDTH / 2 - gameOver.getPrefWidth() / 2, 800);
        gameOver();
    }
}
