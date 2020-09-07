package ru.vineg.orangeBikeFree.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import ru.vineg.orangeBikeFree.LevelSource;
import ru.vineg.orangeBikeFree.*;
import ru.vineg.orangeBikeFree.reflection.LevelReplay;
import ru.vineg.orangeBikeFree.ui.ImageButton;

public class LevelReplayScreen extends GameScreen {
    private final Motodroid game;
    private final LevelReplay replay;
    private boolean renderDebug = false;
    private Stage controlsStage;
    private TextureAtlas atlas;
    private GameScene gameScene;
    private Stage uiStage;
    private boolean paused = false;
    private float popUpZoom = 0.7f;
    private float controlsZoom = 1.2f;

    public LevelReplayScreen(Motodroid game, Skin skin, LevelReplay replay) {
        super(game, skin);
        gameScene = new GameScene(game, replay);
        this.replay=replay;
        this.game = game;
        createUI();
    }


    private void createUI() {
        int bottomLeftRowHeight = 130;
        int topLeftCellSize = (int) (bottomLeftRowHeight * 1.3);

        atlas = Motodroid.manager.get("packed/buttons.atlas", TextureAtlas.class);



        uiStage = new SimpleStage(popUpZoom);
//        popupStage.setCamera(popUpCamera);
        controlsStage = new SimpleStage(controlsZoom);


        Table container = new Table();
        float stageWidth = controlsStage.getWidth();
        float stageHeight = controlsStage.getHeight();
        container.setSize(stageWidth, stageHeight);
//        container.debug();


        final ImageButton pauseButton = new ImageButton(atlas.findRegion("pause"), atlas.findRegion("pause-pressed"));
        //swapButton.setWidth(200);


        Table t1 = new Table();
        Table t2 = new Table();
//        t1.debug();
        pauseButton.getImageCell().expand().left().top().size(80).pad(10, 10, 10, 10);

//        pauseButton.debug();
        t1.row();
        t1.add(pauseButton).size(topLeftCellSize).expand().left().top();
        t1.row().expand();


        container.add(t1).left().top().expand();

        container.add(t2).right().pad(20, 10, 10, 10);

        pauseButton.addListener(new

                                        GameClickListener() {
                                            @Override
                                            public void clicked(InputEvent event, float x, float y) {
                                                pauseGame();
                                                super.clicked(event, x, y);    //To change body of overridden methods use File | Settings | File Templates.
                                            }
                                        }
        );

        controlsStage.addActor(container);
        Gdx.input.setInputProcessor(controlsStage);
    }


    @Override
    public void render(float v) {
        //fpsLogger.log();


        controlsStage.act(v);
        if (paused) {
            uiStage.act(v);
        }

        gameScene.render(v);
        controlsStage.draw();
        if (paused) {
            uiStage.draw();
        }


        if (renderDebug) {
//            Table.drawDebug(controlsStage);
        }

        super.render(v);

        //batch.setProjectionMatrix(stageCamera.combined);

    }

    @Override
    public void pause() {
        gameScene.pause();
    }


    @Override
    protected void restart() {
        game.setLevel(new LevelSource(replay.level));
    }

    @Override
    protected GameScene getGameScene() {
        return gameScene;
    }

    @Override
    protected Stage getControlsStage() {
        return controlsStage;
    }
}
