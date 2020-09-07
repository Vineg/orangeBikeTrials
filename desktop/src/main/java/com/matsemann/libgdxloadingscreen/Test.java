package com.matsemann.libgdxloadingscreen;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.vineg.MyGdxGame;
import ru.vineg.orangeBikeFree.Preferences;

/**
 * @author Mats Svensson
 */
public class Test {

    public static void main(String[] args) {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "motodroid";
        cfg.useGL30 = false;

        cfg.height = 400;
        cfg.width = 1200;
//        cfg.backgroundFPS=0;
//        cfg.foregroundFPS=0;
        cfg.vSyncEnabled = true;
        if (Preferences.smoothMap) {
            cfg.samples = 8;
        }

        MyGdxGame game = new MyGdxGame();
//        game.setUrl("http://vineg.cf/api/level/2");
        new LwjglApplication(game, cfg);


    }
}
