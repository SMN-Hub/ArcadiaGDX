package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import net.smappz.arcadia.actors.AirFighter;
import net.smappz.arcadia.actors.Scroller;
import net.smappz.arcadia.actors.Shoot;
import net.smappz.arcadia.util.TimeEvent;
import net.smappz.arcadia.util.Timeline;

import static net.smappz.arcadia.ArcadiaGame.RESOURCES;

public class SplashScreen extends AbstractScreen {
    private static final float FADE = 4;
    private Timeline timeline = new Timeline();
    private AirFighter fighter;
    private Shoot shoot;
    private Color fontColor = new Color(255, 255, 255, 0);
    private Label label;

    @Override
    public void show() {
        // Stage and actors
        super.show();

        Scroller scroller = new Scroller();
        stage.addActor(scroller);
        scroller.toBack();

        Label.LabelStyle style = new Label.LabelStyle(RESOURCES.getFont(), fontColor);
        label = new Label("Arcadia GDX", style);
        label.setFontScale(4);
        Group labelContainer = new Group();
        labelContainer.setRotation(90);
        labelContainer.addActor(label);
        stage.addActor(labelContainer);
        label.setZIndex(200);

        fighter = new AirFighter(null, 1400, 360);
        stage.addActor(fighter);
        fighter.setZIndex(100);

        TextureAtlas.AtlasRegion region = RESOURCES.getTextureRegion("shot01");
        shoot = new Shoot(region, new Vector2(900, 360), 180f, ArcadiaGame.INSTANCE.getShot(1));


        // controls
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ArcadiaGame.INSTANCE.enter();
                return true;
            }
        });

        // Scenario
        timeline.addEvent(10, new TimeEvent() {
            @Override
            public void trigger() {
                fighter.moveTo(900, 360);
            }
        });
        timeline.addEvent(15, new TimeEvent() {
            @Override
            public void trigger() {
                stage.addActor(shoot);
                shoot.setZIndex(20);
            }
        });
        timeline.addEvent(25, new TimeEvent() {
            @Override
            public void trigger() {
                ArcadiaGame.INSTANCE.enter();
            }
        });
    }

    @Override
    public void act(float delta) {
//        label.setWidth(label.getPrefWidth());
//        label.setHeight(label.getPrefHeight());
        if (fontColor.a < 1) {
            fontColor.a += delta / FADE;
            label.setFontScale(4 + fontColor.a);
            label.setY(-200 - fontColor.a * 100);
            label.setX(HEIGHT / 2 - label.getPrefWidth() / 2);
        }
        timeline.act(delta);
        if (shoot.isVisible() && shoot.getX() < Math.abs(label.getY()) + Math.abs(label.getPrefHeight())) {
            shoot.setVisible(false);
            fontColor.set(0xff6c11ff);
            label.invalidateHierarchy();
            //label.getStyle().background = new FrameDrawable(stage.getCamera());
        }
        super.act(delta);
    }
}
