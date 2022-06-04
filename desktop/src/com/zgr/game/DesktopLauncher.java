package com.zgr.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.zgr.game.Flappy;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle(Flappy.TITLE);
		config.setWindowedMode(Flappy.WIDTH, Flappy.HEIGHT);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new Flappy(), config);
	}
}
