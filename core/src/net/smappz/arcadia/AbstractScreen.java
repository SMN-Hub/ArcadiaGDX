package net.smappz.arcadia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public abstract class AbstractScreen extends ScreenAdapter {
    public static final int WIDTH = 720;
    public static final int HEIGHT = 1280;

    Stage stage;

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(WIDTH, HEIGHT)); //ScreenViewport());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        act(delta);
        stage.draw();
    }

    protected void act(float delta) {
        stage.act(delta);
    }

    @Override
    public void dispose () {
        if (stage != null)
            stage.dispose();
    }
}
