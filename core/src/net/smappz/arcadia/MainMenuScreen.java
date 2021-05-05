package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import net.smappz.arcadia.actors.Scroller;
import net.smappz.arcadia.levels.Level;
import net.smappz.arcadia.levels.Level_1;
import net.smappz.arcadia.levels.Level_2;
import net.smappz.arcadia.levels.Level_3;
import net.smappz.arcadia.levels.Level_4;
import net.smappz.arcadia.levels.Level_5;
import net.smappz.arcadia.levels.Level_6;

import static net.smappz.arcadia.ArcadiaGame.RESOURCES;

class MainMenuScreen extends AbstractScreen {
    private static final int TITLE_Y = 900;
    private static final Level[] levels = new Level[] {new Level_1(), new Level_2(), new Level_3(), new Level_4(), new Level_5(), new Level_6()};

    MainMenuScreen() {

    }

    @Override
    public void show() {
        // Stage and actors
        super.show();

        // Header
        Scroller scroller = new Scroller();
        scroller.setVerticalPosition(TITLE_Y);
        stage.addActor(scroller);
        scroller.toBack();
        Label.LabelStyle titleStyle = new Label.LabelStyle(RESOURCES.getFont(), new Color(0xff6c11ff));
        Label title = new Label("Arcadia GDX", titleStyle);
        title.setFontScale(3);
        title.setY(TITLE_Y + 200);
        title.setX(WIDTH / 2 - title.getPrefWidth() / 2);
        title.setZIndex(200);
        stage.addActor(title);

        // Level list
        Container<ScrollPane> tableContainer = new Container<>();
        tableContainer.setSize(WIDTH, TITLE_Y);
        tableContainer.setPosition(0, 0);
        tableContainer.fillX();
        Table table = new Table(RESOURCES.getSkin());
//        table.setFillParent(true);
        final ScrollPane scroll = new ScrollPane(table, RESOURCES.getSkin());
        scroll.setSmoothScrolling(true);
//        table.add(label1).spaceBottom(10);
        for (int i=0; i<levels.length; i++) {
            table.row().fillX();
            final Level level = levels[i];
            Label label = new Label("" + (i+1) + " " + level.getTitle(), RESOURCES.getSkin(), "menuLabel");
            label.setFontScale(2);
            float height = label.getPrefHeight();
            table.add(label).prefWidth(WIDTH - height - 50).center();
            ImageButton go = new ImageButton(RESOURCES.getSkin(), "menuRunButton");
            go.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y,
                                         int pointer, int button) {
                    ArcadiaGame.INSTANCE.startLevel(level);
                    return true;
                }
            });
            table.add(go).width(height).height(height); //.right();
        }
        tableContainer.setActor(scroll);
        stage.addActor(tableContainer);

        // controls
        Gdx.input.setInputProcessor(stage);
    }
}
