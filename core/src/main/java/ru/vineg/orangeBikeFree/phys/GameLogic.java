package ru.vineg.orangeBikeFree.phys;

import box2d.FixedStepPhysicsWorld;
import box2d.PhysicsWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.andengine.engine.handler.IUpdateHandler;
import ru.vineg.event.ActionListener;
import ru.vineg.graphics.ICamera2d;
import ru.vineg.orangeBikeFree.CameraController;
import ru.vineg.orangeBikeFree.MotodroidGameGraphics;
import ru.vineg.orangeBikeFree.MotodroidGameWorld;
import ru.vineg.orangeBikeFree.entities.GameTextureManager;
import ru.vineg.map.GameVars;
import ru.vineg.map.VertexMap;
import ru.vineg.orangeBikeFree.motorbike.Apple;
import ru.vineg.orangeBikeFree.motorbike.Finish;
import ru.vineg.orangeBikeFree.motorbike.Killer;
import ru.vineg.orangeBikeFree.motorbike.Motorbike;
import ru.vineg.orangeBikeFree.reflection.LevelEntry;
import ru.vineg.std.ActionEvent;


/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 18:47:08 - 19.03.2010
 */
public class GameLogic implements IUpdateHandler {


    public static GameTextureManager textureManager;
    private final MotodroidGameGraphics mScene;


    private Motorbike motorbike;
    private LevelEntry level;
    private int applesLeft;
    public VertexMap map;
    private MotodroidGameWorld gameWorld;
    private boolean endGame = false;
    private ActionListener actionListener;
    private Runnable pauseFunction;
    private long tick;

    public CameraController getCameraController() {
        return cameraController;
    }

    private CameraController cameraController;

    public PhysicsWorld getPhysicsWorld() {
        return mPhysicsWorld;
    }

    private PhysicsWorld mPhysicsWorld;
//    private IAssetManager assets;

    public GameLogic(MotodroidGameGraphics mScene, GameTextureManager textureManager) {
        this.mScene = mScene;
        this.textureManager = textureManager;
    }


    public void onCreateResources() {
        map = level.getMap();
    }

    private void createPhysicsWorld() {

        if (GameVars.fixedStep) {
            this.mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -13f), false, 1, 1);
        } else {
            this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, -10f), false, 1, 1);
        }
//        if (Motodroid.isDebugging()) {
//            this.mPhysicsWorld = new PhysicsWorld(new Vector2i(0, -10f), true, 1, 6);
//        } else {
//            this.mPhysicsWorld = new PhysicsWorld(new Vector2i(0, -10f), true, 1, 6);
//        }

        gameWorld = new MotodroidGameWorld(mPhysicsWorld, mScene, this.textureManager);

    }


    public void onCreateScene() {

//        if (level == null) {
//            throw new Exception("no level specified");
//        }

        createPhysicsWorld();
        //IAssetManager assetManager = this.getAssets();

        placeResources();
        //addControls(mScene, motorbike);


        this.mPhysicsWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(final Contact pContact) {

            }

            @Override
            public void endContact(Contact contact) {


            }

            @Override
            public void preSolve(Contact pContact, Manifold oldManifold) {
                pContact.setEnabled(true);
                final Body bodyA = pContact.getFixtureA().getBody();
                final Body bodyB = pContact.getFixtureB().getBody();

                Object body1Type = bodyA.getUserData();
                Object body2Type = bodyB.getUserData();

                if (checkAppleCollision(body1Type) ||
                        checkAppleCollision(body2Type)) {
                    pContact.setEnabled(false);
                    return;
                }



                checkCollisionType(body1Type);
                checkCollisionType(body2Type);


            }

            private boolean checkAppleCollision(Object bodyType) {
                if (bodyType != null) {
                    if (bodyType instanceof Apple) {
                        if (((Apple) bodyType).exists()) {
                            Apple appleBody = (Apple) bodyType;
                            appleBody.dispose();
                            map.removeApple(appleBody);
                            applesLeft--;
                        }
                        return true;
                    } else if (bodyType instanceof Finish) {
                        if (applesLeft == 0) {
                            winLevel();
                        }
                        return true;
                    }
                }
                return false;
            }

            private void checkCollisionType(Object bodyType) {
                if (bodyType != null) {
                    if (bodyType.equals("head") || bodyType instanceof Killer)
                        endGame = true;

                }
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }

        });

    }


    private void placeResources() {
        applesLeft = map.getApples().size();
        map.placeOnScene(gameWorld);

        Finish.createFinish(gameWorld, map.getFinish());
        motorbike = new Motorbike(gameWorld, map.getStart());
        mScene.addActor(motorbike);

        ICamera2d camera = mScene.getCameraObject();
        camera.setZoomFactor(1f);
        cameraController = new CameraController(motorbike, camera);

        onUpdate(0.1f);
    }

    private void endGame() {
        pause(600, new Runnable() {
            @Override
            public void run() {
//                System.out.println("game over");

                actionListener.actionPerformed(new ActionEvent(this, 2, "fail"));

//                disposeResources();
//
//                try {
//                    onCreateScene();
//                } catch (Exception e) {
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                }
            }
        });

    }


    private int pause;

    private void pause(int mSec, Runnable runnable) {
        this.pause = mSec;
        this.pauseFunction = runnable;
    }

    public void disposeResources() {

        map.dispose();
        mPhysicsWorld.mUpdateHandlers.clear();
        mPhysicsWorld.dispose();
        motorbike.dispose();

        mScene.removeUpdateHandler(this);

    }

    private void winLevel() {
        pause(600, new Runnable() {
            @Override
            public void run() {
                actionListener.actionPerformed(new ActionEvent(this, 1, "win"));
            }
        });
    }

//    public IAssetManager getAssets() {
//        return assets;
//    }

//    public AssetManager getAssets() {
//        AssetManager assetManager = new AssetManager();
//        return assetManager;
//    }


    public void setControls(float controlX, float controlY) {
        if (motorbike != null) {
            motorbike.setControl(controlY, controlX);
        }
    }

    public void changeDirection() {
        if (running()) {
            motorbike.swap();
        }
    }

    private boolean running() {
        return !endGame && pauseFunction == null;
    }

    public void setLevel(LevelEntry level) {
        this.level = level;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        if (mPhysicsWorld == null) {
            return;
        }
        pause -= pSecondsElapsed * 1000;
        if (endGame) {
            endGame();
            endGame = false;
        }
        if (pauseFunction != null) {
            if (pause < 0) {
                Runnable runnable = pauseFunction;
                pauseFunction = null;
                runnable.run();
            }
        } else {
//            mScene.act(pSecondsElapsed);
            this.mPhysicsWorld.onUpdate(pSecondsElapsed);
            tick++;
            cameraController.onUpdate(pSecondsElapsed);

        }

    }

    @Override
    public void reset() {
        tick=0;
        disposeResources();
        try {
            onCreateScene();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public void pause() {
        synchronized (this) {
            pause(Integer.MAX_VALUE, new Runnable() {
                @Override
                public void run() {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
        }
    }


    public float getSimulationTime() {
        return mPhysicsWorld.getTime();
    }

    public Motorbike getMotorbike() {
        return motorbike;
    }

    public long getTick() {
        return tick;
    }


//    public BikePosition getBikePosition(){
//        return motorbike.getFullPosition();
//    }
}
