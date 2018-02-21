package net.smappz.arcadia;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import net.smappz.arcadia.descriptors.GameDescriptor;
import net.smappz.arcadia.descriptors.PlaneDescriptor;

public class ArcadiaGame extends Game {
    public static ArcadiaGame INSTANCE = null;
	private final GameDescriptor descriptors;
	private GameScreen gameScreen;

    public ArcadiaGame() {
        descriptors = loadDescriptors();
        INSTANCE = this;
    }

    @Override
	public void create() {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

	private GameDescriptor loadDescriptors() {
		Json json = new Json();
        return json.fromJson(GameDescriptor.class, Gdx.files.internal("descriptors.json"));
    }

    PlaneDescriptor getPlane(int id) {
        return descriptors.getPlanes().get(id);
    }

	@Override
	public void dispose() {
		gameScreen.dispose();
	}

	@Override
	public void pause () {
		Gdx.app.exit();
	}
}
