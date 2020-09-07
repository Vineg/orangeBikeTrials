package ru.vineg.orangeBikeFree.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Vineg on 05.04.2014.
 */
public class GameClickListener extends ClickListener {
    @Override
    public void cancel() {
        touchUp(null,0,0,0,0);
        super.cancel();
    }
}
