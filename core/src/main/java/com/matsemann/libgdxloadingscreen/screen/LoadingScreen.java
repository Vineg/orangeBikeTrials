package com.matsemann.libgdxloadingscreen.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ru.vineg.graphics.Graphics;
import ru.vineg.orangeBikeFree.Motodroid;

/**
 * @author Mats Svensson
 */
public class LoadingScreen extends AbstractScreen {

    private Stage stage;

    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;

    private float startX, endX;


    private float percent;

    private Actor loadingBar;

    public LoadingScreen(Motodroid game) {
        super(game);
    }

    @Override
    public void show() {
        // Tell the manager to load assets for the loading screen
        game.manager.load("data/loading.atlas", TextureAtlas.class);
        // Wait until they are finished loading
        game.manager.finishLoading();

        // Initialize the stage where we will place everything
        stage = new Stage();
        // Get our textureatlas from the manager
        TextureAtlas atlas = game.manager.get("data/loading.atlas", TextureAtlas.class);

        // Grab the regions from the atlas and create some images
        logo = new Image(atlas.findRegion("libgdx-logo"));
        loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        TextureAtlas.AtlasRegion region = atlas.findRegion("loading-frame-bg");
        region.getTexture().setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
//        region.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        loadingBg = new Image(region);

        // Add the loading bar animation
//        Animation anim = new Animation(0.05f, atlas.findRegions("loading-bar-anim") );
//        anim.setPlayMode(Animation.LOOP_REVERSED);
//        loadingBar = new LoadingBar(anim);

        // Or if you only need a static bar, you can do
        loadingBar = new Image(atlas.findRegion("loading-bar1"));

        // Add all the actors to the stage
        stage.addActor(screenBg);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);
        stage.addActor(logo);

        // Add everything to be loaded, for instance:
        // game.manager.load("data/assets1.pack", TextureAtlas.class);
        // game.manager.load("data/assets2.pack", TextureAtlas.class);
        // game.manager.load("data/assets3.pack", TextureAtlas.class);
    }

    @Override
    public void resize(int width, int height) {
        // Set our screen to always be XXX x 480 in bodySize
        width = 500;
        height = 500;
        stage.setViewport(new ExtendViewport(height,height));
        int screenWidth = Graphics.getScreenWidth();
        int screenHeight = Graphics.getScreenHeight();
        stage.getViewport().update(screenWidth, screenHeight,true);

        // Make the background fill the screen
        screenBg.setSize(height*screenWidth/screenHeight, height);

        // Place the logo in the middle of the screen and 100 px up
        logo.setX((stage.getWidth() - logo.getWidth()) / 2);
        logo.setY((stage.getHeight() - logo.getHeight()) / 2+40);

        // Place the loading frame in the middle of the screen
        loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY(logo.getY()-60);

        // Place the loading bar at the same spot as the frame, adjusted a few px
        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 5);

        // Place the image that will hide the bar on top of the bar, adjusted a few px
        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);
        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 440;

        // The rest of the hidden bar
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
    }

    @Override
    public void render(float delta) {

        if (game.manager.update()) { // Load some, will return true if done loading
            if (Gdx.input.isTouched()) { // If the screen is touched after the game is done loading, go to the main menu screen
                //game.setScreen(new MainMenuScreen(game));
            }
        }

        // Interpolate the percentage to make it more smooth
        float progress = game.manager.getProgress();

        percent = Interpolation.linear.apply(percent, progress, 0.04f);
        if (percent > 0.99&&game.manager.update()) {
            game.onReady();
        }

        // Update positions (and bodySize) to match the percentage
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();

        // Show the loading screen

        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        // Dispose the loading assets as we no longer need them
        game.manager.unload("data/loading.atlas");
    }
}
