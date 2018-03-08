package net.smappz.arcadia.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.smappz.arcadia.AbstractScreen;
import net.smappz.arcadia.ArcadiaGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = AbstractScreen.WIDTH;
		config.height = AbstractScreen.HEIGHT;
		new LwjglApplication(new ArcadiaGame(), config);
	}
}
