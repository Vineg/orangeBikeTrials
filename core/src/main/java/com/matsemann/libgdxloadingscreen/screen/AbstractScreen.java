package com.matsemann.libgdxloadingscreen.screen;

import com.badlogic.gdx.Screen;
import ru.vineg.orangeBikeFree.Motodroid;

/**
 * @author Mats Svensson
 */
public abstract class  AbstractScreen implements Screen {

    protected Motodroid game;

    public AbstractScreen(Motodroid game) {
        this.game = game;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
