package ru.vineg.orangeBikeFree.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ru.vineg.orangeBikeFree.*;
import ru.vineg.orangeBikeFree.reflection.LevelReplay;

import ru.vineg.AccessDeniedException;
import java.util.List;

public class MainMenu implements Screen {
    private final LevelsSource levels;
    private boolean renderDebug = false;
    public com.badlogic.gdx.scenes.scene2d.Stage uiStage;
    private GameScene gameScene;
    public Skin skin;
    public Motodroid game;
    public LevelsScreen levelsScreen;
    public MainMenuStage mainMenuStage;
    public SettingsMenuStage settingsStage;
//    private LevelsScreen myLevelsStage;

    public MainMenu(Motodroid motodroid, Skin skin) {


        this.skin = skin;

        this.game = motodroid;

        levels = game.levels;


        int unlockedLevel = levels.getUnlockedLevel();
        gameScene = new GameScene(motodroid, levels.get(unlockedLevel).getLevel(), true);


        initStages();

        setUiStage(mainMenuStage);

    }

    private void initStages() {
        levelsScreen = new LevelsScreen(this, game.getLevelsSource());
        settingsStage = new SettingsMenuStage(this);
        mainMenuStage = new MainMenuStage(this);
    }


    private void setUiStage(UIStage stage) {
//        MotodroidBackendApi.submitLevel("test12");
        if(stage==null){return;}
        uiStage = stage;
        stage.show();
        show();
    }

    public void mainMenu() {
        setUiStage(mainMenuStage);
    }

    public void levelsSelectMenu() {
//        if (GamePreferences.isUpdateLevelsList()) {
        if(levelsScreen.getLevelsSource().isChanged()){
           levelsScreen = new LevelsScreen(this, game.getLevelsSource());
        }
        setUiStage(levelsScreen);
//        }else{
//            setUiStage(levelsScreen);
//        }
    }

    public void myLevelsSelectMenu() {
            setUiStage(getMyLevelsStage());
    }

    public LevelsScreen getMyLevelsStage(){
        try {
            return new MyLevelsScreen(this, game.getMyLevelsSource());
        } catch (AccessDeniedException e) {
            Motodroid.message("failed to open levels directory");
            return null;
        }
    }

    public void settingsMenu() {
        setUiStage(settingsStage);
    }

    @Override
    public void dispose() {
        gameScene.dispose();
    }

    @Override
    public void render(float v) {
        //fpsLogger.log();

//        Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);

        if (renderDebug) {
//            Table.drawDebug(uiStage);
        }


        gameScene.render(v);

        uiStage.act(v);
        uiStage.draw();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        gameScene.resumeGame();
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void hide() {
        gameScene.pauseGame();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pause() {
        gameScene.pauseGame();
        gameScene.pause();
    }

    @Override
    public void resume() {
        gameScene.resumeGame();
        gameScene.resume();
        Gdx.input.setInputProcessor(uiStage);
    }

//    public void updateList() {
//        popupStage.dispose();
//        createUI();
//        show();
//    }


    public void levelFinished(LevelsScreen levels, LevelReplay replay, List<TextButtonData> additionalPauseButtons) {
        setUiStage(new LevelFinishedStage(this, levels, replay,additionalPauseButtons));
    }


    public void setStage(UIStage listStage) {
        setUiStage(listStage);
    }
}
