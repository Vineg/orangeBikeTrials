package ru.vineg.orangeBikeFree.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import ru.vineg.event.ActionListener;
import ru.vineg.orangeBikeFree.EditableLevel;
import ru.vineg.orangeBikeFree.LevelSource;
import ru.vineg.orangeBikeFree.ui.ImageButton;
import ru.vineg.std.ActionEvent;
import ru.vineg.orangeBikeFree.*;

import java.util.ArrayList;
import java.util.List;

public class LevelScreen extends GameScreen {
    private boolean renderDebug = false;
    private SimpleStage controlsStage;
    private TextureAtlas atlas;
    private GameScene gameScene;
    private float controlsZoom = 1.1f;

    /**/
    private boolean testing = false;
    private LevelsScreen listStage;
    private ImageButton rotateLeftButton;
    private ImageButton rotateRightButton;
    private ImageButton downButton;
    private ImageButton upButton;

    public LevelScreen(final Motodroid game, final Skin skin, final LevelSource level, final LevelsScreen levelsScreen) {
        super(game, skin);
        atlas = Motodroid.manager.get("packed/buttons.atlas", TextureAtlas.class);
        setListStage(levelsScreen);
        createUI();
        gameScene = new GameScene(game, level.getLevel());

        if (level instanceof EditableLevel) {
            TextButtonData editButton = ((EditableLevel) level).getEditButtonData();
            addPauseButton(editButton);
        }

        gameScene.gameLogics.setActionListener(new ActionListener() {
                                                   @Override
                                                   public void actionPerformed(ActionEvent e) {
                                                       LevelsSource levelsSource = levelsScreen.getLevelsSource();
                                                       switch (e.getId()) {
                                                           case 1:
                                                               gameScene.levelReplay.finishTime = gameScene.gameLogics.getSimulationTime();
                                                               List<TextButtonData> additionalButtons=new ArrayList<TextButtonData>(additionalPauseButtons);
                                                               if (!testing) {
                                                                   Motodroid.gamePreferences.trackLevelFinish(level.getNumber(), levelsSource.getLevelFails(level.getNumber()));
                                                                   levelsSource.unlock(level.getNumber() + 1);
                                                                   TextButtonData nextLevelButton = new TextButtonData(" next level ");
                                                                   nextLevelButton.addListener(new GameClickListener() {
                                                                       @Override
                                                                       public void clicked(InputEvent event, float x, float y) {
                                                                           Motodroid.mainMenuScreen.uiStage=Motodroid.mainMenuScreen.mainMenuStage;
                                                                           getListStage().setLevel(level.getNumber() + 1);
                                                                       }
                                                                   });
                                                                   if (getListStage() != null) {
                                                                       additionalButtons.add(nextLevelButton);
                                                                   }
                                                               }
                                                               game.setLevelFinishScreen(levelsScreen, gameScene.levelReplay, additionalButtons);
                                                               break;
                                                           case 2:
                                                               levelsSource.levelFailed(level.getNumber());
                                                               gameScene.restart();
                                                               break;
                                                       }
                                                   }
                                               }
        );
    }

    public LevelsScreen getListStage() {
        if (listStage == null) {
            return Motodroid.mainMenuScreen.getMyLevelsStage();
        }
        return listStage;
    }

    public void setListStage(LevelsScreen levelsScreen) {
        this.listStage = levelsScreen;
    }


    private void createUI() {
        int bottomLeftRowHeight = 130;
        int bottomRightRowHeight = 166;
        int topRowHeight = (int) (bottomLeftRowHeight * 1.3);
        int topLeftCellSize = (int) (bottomLeftRowHeight * 1.3);


        controlsStage = new SimpleStage(controlsZoom) {
            @Override
            public boolean keyDown(int keycode) {
                return gameScene.keyDown(keycode);
            }

            @Override
            public boolean keyUp(int keyCode) {
                return gameScene.keyUp(keyCode);
            }
        };


        Table container = new Table();
        float stageWidth = controlsStage.getWidth();
        float stageHeight = controlsStage.getHeight();
        container.setSize(stageWidth, stageHeight);


        upButton = new ImageButton(atlas.findRegion("up"), atlas.findRegion("up-pressed"));
        downButton = new ImageButton(atlas.findRegion("down"), atlas.findRegion("down-pressed"));
        upButton.getImageCell().

                expand()

                .

                        pad(40, 20, 40, 40)

                .

                        bottom()

                .

                        left();

        downButton.getImageCell().

                expand()

                .

                        pad(20, 20, 40, 40)

                .

                        bottom()

                .

                        left();


        rotateLeftButton = new ImageButton(atlas.findRegion("ccw"), atlas.findRegion("ccw-pressed"));
        rotateRightButton = new ImageButton(atlas.findRegion("cw"), atlas.findRegion("cw-pressed"));
        rotateLeftButton.getImageCell().

                expand()

                .

                        pad(80, 20, 20, 5)

                .

                        top()

                .

                        left();

        rotateRightButton.getImageCell().

                expand()

                .

                        pad(80, 5, 20, 20)

                .

                        bottom()

                .

                        left();


        ImageButton swapButton = new ImageButton(atlas.findRegion("flip"), atlas.findRegion("flip-pressed"));
        //swapButton.setWidth(200);
        swapButton.getImageCell().

                expand(true, true)

                .

                        pad(20, 20, 20, 20)

                .

                        top()

                .

                        right();

        final ImageButton pauseButton = new ImageButton(atlas.findRegion("pause"), atlas.findRegion("pause-pressed"));
        //swapButton.setWidth(200);


        Table left = new Table();
        Table right = new Table();
//        left.debug();
        pauseButton.getImageCell().

                expand()

                .

                        left()

                .

                        top()

                .

                        size(80)

                .

                        pad(10, 10, 10, 10);

//        pauseButton.debug();
        left.row();
        left.add(pauseButton).

                size(topLeftCellSize).expand().left().top();

        left.row();
        left.add(upButton).left().bottom().size(bottomLeftRowHeight, stageHeight - bottomLeftRowHeight - topLeftCellSize);

        left.row();
        left.add(downButton).expand().left().top().size(bottomLeftRowHeight);

        container.add(left).left().expand();

        right.add(swapButton).expand().right().top().colspan(2).height(stageHeight - bottomLeftRowHeight).width(topRowHeight);

        right.row();
        right.add(rotateLeftButton).expand().right().bottom().size(170, bottomRightRowHeight).padBottom(40);
        right.add(rotateRightButton).right().bottom().size(170, bottomRightRowHeight).padBottom(40);
        container.add(right).right().pad(20, 10, 10, 10);
//        rotateLeftButton.debug();
//        container.debug();

        upButton.addListener(new

                                     GameClickListener() {
                                         @Override
                                         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                             gameScene.keyDown(Input.Keys.W);
//                                             return super.touchDown(event, x, y, pointer, button);    //To change body of overridden methods use File | Settings | File Templates.
                                             return true;
                                         }

                                         @Override
                                         public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                             gameScene.keyUp(Input.Keys.W);
//                                             super.touchUp(event, x, y, pointer, button);    //To change body of overridden methods use File | Settings | File Templates.
//                                             return true;
                                         }
                                     }
        );
        downButton.addListener(new

                                       GameClickListener() {
                                           @Override
                                           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                               gameScene.keyDown(Input.Keys.S);
//                                               return super.touchDown(event, x, y, pointer, button);    //To change body of overridden methods use File | Settings | File Templates.
                                               return true;
                                           }

                                           @Override
                                           public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                               gameScene.keyUp(Input.Keys.S);
//                                               super.touchUp(event, x, y, pointer, button);
//                                               return super.touchUp(event, x, y, pointer, button);    //To change body of overridden methods use File | Settings | File Templates.
                                           }
                                       }
        );
        rotateRightButton.addListener(new

                                              GameClickListener() {
                                                  @Override
                                                  public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                                      gameScene.keyDown(Input.Keys.D);
                                                      return true;
//                                                      return super.touchDown(event, x, y, pointer, button);    //To change body of overridden methods use File | Settings | File Templates.
                                                  }

                                                  @Override
                                                  public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                                      gameScene.keyUp(Input.Keys.D);
//                                                      super.touchUp(event, x, y, pointer, button);
//                                                      return super.touchUp(event, x, y, pointer, button);    //To change body of overridden methods use File | Settings | File Templates.
                                                  }
                                              }
        );

        rotateLeftButton.addListener(new

                                             GameClickListener() {
                                                 @Override
                                                 public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                                     gameScene.keyDown(Input.Keys.A);
                                                     return true;
//                                                     return super.touchDown(event, x, y, pointer, button);    //To change body of overridden methods use File | Settings | File Templates.
                                                 }

                                                 @Override
                                                 public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                                     gameScene.keyUp(Input.Keys.A);
//                                                     super.touchUp(event, x, y, pointer, button);
//                                                      return super.touchUp(event, x, y, pointer, button);    //To change body of overridden methods use File | Settings | File Templates.
                                                 }
                                             }
        );

        swapButton.addListener(new

                                       GameClickListener() {
                                           boolean down=false;
                                           @Override
                                           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                               if(down){return true; }
                                               gameScene.keyDown(Input.Keys.SPACE);
                                               down=true;
                                               return true;
//                                               return super.touchDown(event, x, y, pointer, button);    //To change body of overridden methods use File | Settings | File Templates.
                                           }

                                           @Override
                                           public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                               down=false;
                                           }
                                       }
        );

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

        processInput();


        controlsStage.act(v);


        gameScene.render(v);
        controlsStage.draw();


        if (renderDebug) {
//            Table.drawDebug(controlsStage);
        }

        super.render(v);

        //batch.setProjectionMatrix(stageCamera.combined);

    }

    private void processInput() {
//        if(rotateLeftButton.isPressed())
    }


    @Override
    public GameScene getGameScene() {
        return gameScene;
    }

    @Override
    protected Stage getControlsStage() {
        return controlsStage;
    }

    @Override
    public void show() {
        super.show();
    }

    public void setTesting(boolean testing) {
        this.testing = testing;
    }

    @Override
    public void resume() {
        gameScene.resume();
        super.resume();
    }

    @Override
    public void pause() {
        gameScene.pause();
        pauseGame();
    }
}
