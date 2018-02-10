package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import net.smappz.arcadia.util.Route;

class GameScreen extends ScreenAdapter {
    private Stage stage;
    private AirFighter fighter;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT)); //ScreenViewport());
        fighter = new AirFighter();

        stage.addActor(fighter);
        createEnemies();

        Gdx.input.setInputProcessor(stage);
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                fighter.moveTo(x, y);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                fighter.moveTo(x, y);
            }
        });
    }

    private void createEnemies() {
        Route r1 = new Route();
        r1.addPoint(-30, 200).addPoint(600, 200).addPoint(900, 300).addPoint(1000, 400);
        r1.addPoint(900, 500).addPoint(800, 600).addPoint(700, 650).addPoint(600, 600);
        r1.addPoint(500, 500).addPoint(400, 300).addPoint(400, -30);
        AirEnemy a = new AirEnemy(1, r1);
        stage.addActor(a);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
