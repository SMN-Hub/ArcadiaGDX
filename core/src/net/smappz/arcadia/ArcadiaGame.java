package net.smappz.arcadia;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import net.smappz.arcadia.descriptors.PlaneDescriptor;

import java.util.HashMap;
import java.util.Map;

public class ArcadiaGame extends Game {
	private GameScreen gameScreen;

	@Override
	public void create() {
        loadDescriptors();
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

	private Map<Integer,PlaneDescriptor> loadDescriptors() {
        Map<Integer,PlaneDescriptor> descriptors = new HashMap<>();
        JsonValue jsonValue = new JsonReader().parse(Gdx.files.internal("Planes.json"));
        for (JsonValue value : jsonValue) {
            Json json = new Json();
            PlaneDescriptor plane = json.fromJson(PlaneDescriptor.class, value.toJson(JsonWriter.OutputType.minimal));
            descriptors.put(plane.getId(), plane);
        }
        return descriptors;
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
