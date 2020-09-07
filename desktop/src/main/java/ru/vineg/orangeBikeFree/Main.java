package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "motodroid";
		cfg.useGL30 = false;

        cfg.height = 400;
		cfg.width = 1200;
//        cfg.backgroundFPS=0;
//        cfg.foregroundFPS=0;
        cfg.vSyncEnabled=true;
        if (Preferences.smoothMap) {
             cfg.samples = 8;
        }

        Motodroid game = new Motodroid(new Preferences());
//        game.setUrl("http://vineg.cf/api/replay/281");
        new LwjglApplication(game, cfg);
	}
}
