package ru.vineg.game;

import box2d.PhysicsConnector;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import org.andengine.engine.handler.IActHandler;
import org.andengine.engine.handler.IUpdateHandler;
import ru.vineg.orangeBikeFree.motorbike.Motorbike;

/**
 * Created by vineg on 18.01.2015.
 */
public interface IPhysicsWorld {
    void safeDestroy(Body body);

    void unregisterPhysicsConnector(PhysicsConnector physicsConnector);

    void registerPhysicsConnector(PhysicsConnector physicsConnector);

    Body createDynamicBody(BodyDef bodyDef);

    void destroyJoint(Joint joint);

    Joint createJoint(JointDef jointDef);

    void clearPhysicsConnectors();

    void registerUpdateHandler(IActHandler updateHandler);

    void removeUpdateHandler(IActHandler updateHandler);

    float getTime();

    void onUpdate(float time);
}
