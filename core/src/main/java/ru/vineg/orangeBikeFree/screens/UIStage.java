package ru.vineg.orangeBikeFree.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import ru.vineg.graphics.Graphics;
import ru.vineg.orangeBikeFree.*;

/**
 * Created by Vineg on 16.03.14.
 */
public class UIStage extends SimpleStage {

    public UIStage() {
        super(MotodroidUI.scale);
        getViewport().update(Graphics.getScreenWidth(),Graphics.getScreenHeight(),true);
    }

    public void show(){}
}
