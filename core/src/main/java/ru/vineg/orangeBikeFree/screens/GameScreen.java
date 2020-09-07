package ru.vineg.orangeBikeFree.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import ru.vineg.orangeBikeFree.GameScene;
import ru.vineg.orangeBikeFree.Motodroid;

import java.util.ArrayList;
import java.util.List;

/**
 * handles gamePause behavior
 */
public abstract class GameScreen implements Screen {

    private final Skin skin;
    public boolean paused = false;
    List<TextButtonData> additionalPauseButtons = new ArrayList<TextButtonData>();
    private Stage uiStage;
    private Window window;
    private boolean hidden = true;
    private Stage currentUIStage;

    protected GameScreen(Motodroid game, Skin skin) {
        this.skin = skin;


        uiStage = new UIStage();


//        popupStage.setCamera(popUpCamera);
    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        hidden = false;
        if (currentUIStage != null) {
            show(currentUIStage);
        }
    }

    @Override
    public void hide() {
        hidden = true;
//        pauseGame();
    }

    @Override
    public void render(float delta) {
        if (paused) {
            uiStage.act(delta);
            uiStage.draw();
        }
    }

    public void pauseGame() {

        show(uiStage);
        if (paused) {
            return;
        }

        getGameScene().pauseGame();

        paused = true;
        window = new Window("  ", skin, "dialog-dim");
        TextButton restart = new TextButton("  restart level  ", skin);
        TextButton resume = new TextButton("  resume  ", skin);
        TextButton menu = new TextButton("  menu  ", skin);
        TextButton trace = new TextButton(Motodroid.game.gamePreferences.isTracing() ? " stop tracing " : " start tracing ", skin);
        window.defaults().spaceBottom(10).minWidth(150);


        window.add(resume);
        window.row();
        window.add(restart);
        window.row();
        window.add(menu);
        window.row();

        for (TextButtonData but : additionalPauseButtons) {
            TextButton button = new TextButton(but.label, skin);
            button.addListener(but.clickListener);
            window.add(button);
            window.row();
        }

        if (Motodroid.game.gamePreferences.isDebugging()) {
            window.add(trace);
            window.row();
        }
        window.pad(30, 40, 10, 40);
        window.pack();


        trace.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Motodroid.game.gamePreferences.isTracing()) {
                    Motodroid.game.gamePreferences.stopTracing();
                } else {
                    Motodroid.game.gamePreferences.startTracing();
                }
                super.clicked(event, x, y);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });
        menu.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Motodroid.game.setMenuScreen();
                window.remove();
                super.clicked(event, x, y);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });
        restart.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restart();
                super.clicked(event, x, y);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });
        resume.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeGame();

                super.clicked(event, x, y);    //To change body of overridden methods use File | Settings | File Templates.
            }
        });
        window.setPosition(uiStage.getWidth() / 2 - window.getWidth() / 2, uiStage.getHeight() / 2 - window.getHeight() / 2);
        uiStage.addActor(window);

    }


    public void addPauseButton(TextButtonData button) {
        additionalPauseButtons.add(button);
    }


    protected void show(Stage stage) {
        currentUIStage = stage;
        if (!hidden) {
            Gdx.input.setInputProcessor(stage);
        }
    }

    @Override
    public void dispose() {
        getGameScene().dispose();
    }

    protected abstract GameScene getGameScene();

    protected void restart() {
        getGameScene().restart();
        resumeGame();
    }


    @Override
    public void pause() {
        getGameScene().pause();
    }

    protected abstract Stage getControlsStage();

    @Override
    public void resume() {
        pauseGame();
    }

    public void resumeGame() {
        window.remove();
        paused = false;
        getGameScene().resumeGame();
        show(getControlsStage());
    }

    public void addPauseButtons(ArrayList<Button> additionalPauseButtons) {
        additionalPauseButtons.addAll(additionalPauseButtons);
    }
}
