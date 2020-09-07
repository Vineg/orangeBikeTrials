package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ru.vineg.graphics.DefaultCamera;
import ru.vineg.graphics.Graphics;

/**
 * Created by Vineg on 01.05.2014.
 */
public class SimpleStage extends Stage {

    public final SpriteBatch spriteBatch;

    protected final DefaultCamera camera = new DefaultCamera();

    public SimpleStage(float width, float height, SpriteBatch batch, boolean centerCamera) {
        super(new ExtendViewport(width, height), batch);
        this.spriteBatch = batch;
        setCamera(camera);
        getViewport().update(Graphics.getScreenWidth(), Graphics.getScreenHeight(), centerCamera);
    }

    public SimpleStage(float scale) {
        this(Graphics.getMinWidth() * scale, Graphics.getMinHeight() * scale);
    }

    public void setCamera(Camera camera) {
        getViewport().setCamera(camera);
    }

    public SimpleStage(float width, float height) {
        this(width, height, true);
    }

    public SimpleStage(float width, float height, boolean centerCamera) {
        this(width, height, new SpriteBatch(), centerCamera);
    }

    public SimpleStage(boolean centerCamera) {
        this(Graphics.minWidth, Graphics.minHeight, centerCamera);
    }


    public SimpleStage(float width, float height, SpriteBatch batch) {
        this(width, height, batch, true);
    }

    public SimpleStage() {
        this(true);
    }

    public void updateViewport(boolean center) {
        getViewport().update(Graphics.getScreenWidth(), Graphics.getScreenHeight(), center);
    }

    public float getWidthScaled() {
        return getViewport().getWorldWidth() * getCameraObject().getZoomFactor();
    }

    public float getHeightScaled() {
        return getViewport().getWorldHeight() * getCameraObject().getZoomFactor();
    }

    public void center() {
        getViewport().update(Graphics.getScreenWidth(), Graphics.getScreenHeight(), true);
    }


    public DefaultCamera getCameraObject() {
        return camera;
    }
}
