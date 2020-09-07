package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import ru.vineg.ControlsAction;
import ru.vineg.event.ActionListener;
import ru.vineg.graphics.DefaultCamera;
import ru.vineg.graphics.Graphics;
import ru.vineg.orangeBikeFree.phys.GameLogic;
import ru.vineg.orangeBikeFree.reflection.LevelEntry;
import ru.vineg.orangeBikeFree.reflection.LevelReplay;
import ru.vineg.orangeBikeFree.reflection.MControlsAction;
import ru.vineg.std.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 24.10.13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
public class GameScene {
    private final int WIDTH;
    private final int HEIGHT;
    private final DefaultCamera physicsCamera;
    private final SimpleStage backgroundStage;
    private final Sprite backgroudSprite;
    //    private final SpriteCache backgroundCache;
    private Camera stageCamera;
    //    private DefaultCamera physicsCamera;
    private SpriteBatch batch = Motodroid.batch;
    private MotodroidGameGraphics stage;
    //private Stage backgroundStage;
    private LevelEntry level;
    private Box2DDebugRenderer debugRenderer;
    public GameLogic gameLogics;
    private MyFPSLogger fpsLogger;
    private TextureRegion background;
    private Motodroid game;
    private boolean renderGraphics = true;
    private boolean renderDebug = false;
    private int controlY;
    private int controlX;
    //private Thread logicsThread;
    private boolean running = true;
    private long time;
    public LevelReplay levelReplay;
    private LevelReplay runningReplay;
    private boolean replay = false;
    private BikeSoundController bikeSound;
    private Camera backgroundCamera = new OrthographicCamera();

    public GameScene(Motodroid motodroid, LevelEntry level) {
        this(motodroid, level, false);
    }

    public GameScene(Motodroid motodroid, LevelEntry level, boolean preview) {
        time = System.currentTimeMillis();
        this.game = motodroid;
        this.level = level;
        this.levelReplay = new LevelReplay(level);
        WIDTH = Graphics.getMinWidth();
        HEIGHT = Graphics.getMinHeight();

        backgroundStage = new SimpleStage();
        backgroundStage.center();

        physicsCamera = new DefaultCamera();

        debugRenderer = new Box2DDebugRenderer();

        loadTextures();

        backgroudSprite = new Sprite(background);
        backgroudSprite.setSize(backgroundStage.getWidth(), backgroundStage.getHeight());

//        backgroundCache = Motodroid.spriteCache;
//        backgroundCache.beginCache();
//        backgroundCache.add(backgroudSprite);
//        cacheId = backgroundCache.endCache();


        stage = new MotodroidGameGraphics(WIDTH, HEIGHT, batch);

        //backgroundStage = new Stage(WIDTH, HEIGHT, batch);
        stageCamera = stage.getCamera();
        //stage.setViewport(h, angularVelocity, false);


        addGameLogics();
        resume();
        if (!preview) {
//            addSound();
        }

        fpsLogger = new MyFPSLogger();
    }

    private void addSound() {
        bikeSound = new BikeSoundController(game.manager.get("sound/bike_sound1.ogg", Sound.class), gameLogics.getMotorbike(), gameLogics.getCameraController());
    }

    public GameScene(Motodroid game, LevelReplay replay) {
        this(game, replay.level, false);
        this.replay = true;
        this.levelReplay = replay;
        this.runningReplay = (LevelReplay) levelReplay.clone();
    }

    private void loadTextures() {

        background = Motodroid.textureManager.backgroundRegion;
        updateQuality();

    }

    public void updateQuality() {
        Texture.TextureFilter filter;
        if (GamePreferences.getQuality() == GamePreferences.Quality.high) {
            filter = Texture.TextureFilter.MipMapLinearLinear;
        } else {
            filter = Texture.TextureFilter.MipMapLinearNearest;
        }
//        background.setFilter(filter, filter);
        Motodroid.textureManager.updateQuality();
    }


    private void addGameLogics() {
        gameLogics = new GameLogic(stage, Motodroid.textureManager);
        gameLogics.setActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getId()) {
                    case 1:
                        restart();
                        break;
                    case 2:
                        restart();
                        break;
                }
            }
        });

        gameLogics.setLevel(level);
        gameLogics.onCreateResources();
        gameLogics.onCreateScene();


//        if (renderDebug) {
//            gameLogics.getCameraController().addCamera(new ICamera2dCameraWrapper(physicsCamera, PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT));
//        }

    }

    public void render(float deltaTime) {
        if (stage.cacheId == -1) {
            resume();
            return;
        }
        deltaTime = Math.min(deltaTime, 0.05f);
        if (bikeSound != null) {
            bikeSound.update(deltaTime);
        }

        if (replay) {
            long tick = gameLogics.getTick();
            while (runningReplay.peek() != null && tick >= runningReplay.peek().tick) {
                ControlsAction action = runningReplay.poll();
                if (action.down) {
                    keyDown(action.keyCode);
                } else {
                    keyUp(action.keyCode);
                }
            }
        }
        if (running) {
            gameLogics.onUpdate(deltaTime);
            stage.act(deltaTime);
        }


        if (game.gamePreferences.isDebugOutputEnabled()) {
//            fpsLogger.logMinFps();
        }

        //batch.setProjectionMatrix(stageCamera.combined);


        processInput();
//        stageCamera.update();

        backgroundCamera.position.x = -stageCamera.position.x / 10;
        backgroundCamera.position.y = -stageCamera.position.y / 10;

        if (renderGraphics) {
//            batch.setProjectionMatrix(backgroundStage.getCamera().combined);
//            batch.begin();
            float bgWidth = backgroundStage.getWidth();
            float bgHeight = backgroundStage.getHeight();
            float scale = 1f;


//            batch.disableBlending();
//            batch.draw(background, -1 - (stageCamera.position.x / 8) % 1, -1 + (-stageCamera.position.y / 8) % 1, bgWidth + 2, bgHeight + 2,
//                    (int) stageCamera.position.x / 8, (int) -stageCamera.position.y / 8, (int) (bgWidth * scale + 2), (int) (bgHeight * scale + 2), false, false);
//            backgroudSprite.draw(batch);
//            backgroundCache.setProjectionMatrix(backgroundStage.getCamera().combined);
//            backgroundCache.begin();
//            backgroundCache.draw(cacheId);
//            backgroundCache.end();

            Gdx.graphics.getGL20().glClearColor(0f / 255f, 80f / 255f, 172f / 255f, 1);
            Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

//            batch.enableBlending();


            batch.setProjectionMatrix(backgroundStage.getCamera().combined);
            batch.begin();
            stage.draw();
            batch.end();

            stage.drawMap();

            SpriteCache cache = Motodroid.spriteCache;

            Gdx.gl.glEnable(GL20.GL_BLEND);
            cache.setProjectionMatrix(stage.getCamera().combined);
            cache.begin();
            cache.draw(stage.cacheId);
            cache.end();

            //backgroundStage.draw();
        }

        if (renderDebug) {
            physicsCamera.update();
            debugRenderer.render(gameLogics.getPhysicsWorld().getWorld(), physicsCamera.combined);
        }


    }


    private void processInput() {
        synchronized (gameLogics) {
            gameLogics.setControls(controlX, controlY);
        }
    }


    public void dispose() {
//        logicsThread.interrupt();
        //batch.dispose();
    }

    public boolean keyDown(int keycode) {

        recordControls(keycode, true);
        switch (keycode) {
            case Input.Keys.W:
                controlY = 1;
                break;
            case Input.Keys.S:
                controlY = -1;
                break;
            case Input.Keys.D:
                controlX = 1;
                break;
            case Input.Keys.A:
                controlX = -1;
                break;
            case Input.Keys.SPACE:
                gameLogics.changeDirection();
                break;
        }
        return true;
    }

    private void recordControls(int keycode, boolean down) {
        if (!replay) {
            MControlsAction action = new MControlsAction(keycode, down);
            action.tick=gameLogics.getTick();
            levelReplay.add(action);
        }
    }


    public boolean keyUp(int keyCode) {
        recordControls(keyCode, false);
        switch (keyCode) {
            case Input.Keys.W:
                if (controlY > 0) {
                    controlY = 0;
                }
                break;
            case Input.Keys.S:
                if (controlY < 0) {
                    controlY = 0;
                }
                break;
            case Input.Keys.D:
                if (controlX > 0) {
                    controlX = 0;
                }
                break;
            case Input.Keys.A:
                if (controlX < 0) {
                    controlX = 0;
                }
                break;
        }


        return true;
    }


    public void restart() {
//        logicsThread.interrupt();

        controlY = 0;
        controlX = 0;
        stage.reset();
        gameLogics.reset();
        if (bikeSound != null) {
            bikeSound.stop();
            addSound();
        }
        if (replay) {
            runningReplay = (LevelReplay) levelReplay.clone();
        } else {
            levelReplay.clear();
        }
    }




    public void pause() {
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            return;
        }
        stage.cacheId = -1;
    }


    public void pauseGame() {
        running = false;
        if (bikeSound != null) {
            bikeSound.pause();
        }
    }

    public void resume() {
        if (stage.cacheId == -1) {
            stage.preRender();
            stage.draw(gameLogics.map);
            stage.finalizePreRender();
        }
    }

    public void resumeGame() {
        running = true;
        if (bikeSound != null) {
            bikeSound.resume();
        }
    }
}
