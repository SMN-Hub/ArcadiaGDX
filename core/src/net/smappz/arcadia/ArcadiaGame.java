package net.smappz.arcadia;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

import net.smappz.arcadia.descriptors.GameDescriptor;
import net.smappz.arcadia.descriptors.PlaneDescriptor;
import net.smappz.arcadia.descriptors.ShotDescriptor;
import net.smappz.arcadia.levels.Level;
import net.smappz.arcadia.levels.Level_1;
import net.smappz.arcadia.util.ResourceManager;

public class ArcadiaGame extends Game {
    public static ArcadiaGame INSTANCE = null;
	public static ResourceManager RESOURCES = null;
	private GameDescriptor descriptors;

    private SplashScreen splashScreen;
    private GameScreen gameScreen;

    @Override
	public void create() {
		INSTANCE = this;
		RESOURCES = new ResourceManager();
		descriptors = loadDescriptors();

        splashScreen = new SplashScreen();
		gameScreen = new GameScreen();

        splash();
	}

	private GameDescriptor loadDescriptors() {
		Json json = new Json();
        return json.fromJson(GameDescriptor.class, Gdx.files.internal("descriptors.json"));
    }

	public PlaneDescriptor getPlane(int id) {
		return descriptors.getPlanes().get(id);
	}

	public ShotDescriptor getShot(int id) {
		return descriptors.getShots().get(id-1);
	}

	@Override
	public void dispose() {
		gameScreen.dispose();
		RESOURCES.dispose();
	}

	@Override
	public void pause () {
		Gdx.app.exit();
	}

	void splash() {
        setScreen(splashScreen);
	}

	void enter() {
        //TODO menu
        startLevel();
	}

	void startLevel() {
        Level level = new Level_1();
        gameScreen.setLevel(level);
        setScreen(gameScreen);
	}
}
