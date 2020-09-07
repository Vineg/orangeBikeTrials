package ru.vineg.game;

import box2d.PhysicsWorld;
import ru.vineg.orangeBikeFree.IScene;

/**
 * Created by vineg on 03.02.2015.
 */
public interface IGameWorld {
    IScene getScene();

    PhysicsWorld getPhysicsWorld();
}
