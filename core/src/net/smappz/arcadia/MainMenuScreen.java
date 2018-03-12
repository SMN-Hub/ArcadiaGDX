package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import net.smappz.arcadia.actors.Scroller;
import net.smappz.arcadia.levels.Level_1;

import static net.smappz.arcadia.ArcadiaGame.RESOURCES;

class MainMenuScreen extends AbstractScreen {

    MainMenuScreen() {

    }

    @Override
    public void show() {
        // Stage and actors
        super.show();

        Scroller scroller = new Scroller();
        stage.addActor(scroller);
        scroller.toBack();

        Label.LabelStyle style = new Label.LabelStyle(RESOURCES.getFont(), new Color(1, 1, 1, 1));
        Label label1 = new Label("Level 1", style);
        label1.setFontScale(2);
        label1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                ArcadiaGame.INSTANCE.startLevel(new Level_1());
                return true;
            }
        });
        Label label2 = new Label("Level 2", style);
        label2.setFontScale(2);

        Table table = new Table();
        table.setFillParent(true);
        table.add(label1).spaceBottom(10);
        table.row();
        table.add(label2);
        stage.addActor(table);

        // controls
        Gdx.input.setInputProcessor(stage);
    }
}
