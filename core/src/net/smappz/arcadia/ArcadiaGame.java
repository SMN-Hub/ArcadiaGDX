package net.smappz.arcadia;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import net.smappz.arcadia.descriptors.GameDescriptor;
import net.smappz.arcadia.descriptors.PlaneDescriptor;
import net.smappz.arcadia.descriptors.ShotDescriptor;

public class ArcadiaGame extends Game {
    public static ArcadiaGame INSTANCE = null;
	private GameDescriptor descriptors;
	private GameScreen gameScreen;

    @Override
	public void create() {
		INSTANCE = this;
		descriptors = loadDescriptors();
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

	ShotDescriptor getShot(int id) {
		return descriptors.getShots().get(id-1);
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
