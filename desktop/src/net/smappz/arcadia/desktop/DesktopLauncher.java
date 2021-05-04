package net.smappz.arcadia.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import net.smappz.arcadia.AbstractScreen;
import net.smappz.arcadia.ArcadiaGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Arcadia GDX");
		config.setWindowedMode(AbstractScreen.WIDTH, AbstractScreen.HEIGHT);
		new Lwjgl3Application(new ArcadiaGame(false), config);
	}
}
