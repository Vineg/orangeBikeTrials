package ru.vineg.game;

import box2d.PhysicsWorld;
import org.andengine.engine.handler.ActHandlerList;
import ru.vineg.orangeBikeFree.IScene;

/**
 * Created by vineg on 17.01.2015.
 */
public class GameWorld extends SimpleActListener implements IGameWorld {
    private final PhysicsWorld physicsWorld;
    private final IScene scene;


    public GameWorld(PhysicsWorld physicsWorld, IScene scene) {
        this.physicsWorld = physicsWorld;
        this.scene = scene;
    }

    public PhysicsWorld getPhysicsWorld() {
        return physicsWorld;
    }

    public IScene getScene() {
        return scene;
    }
}
