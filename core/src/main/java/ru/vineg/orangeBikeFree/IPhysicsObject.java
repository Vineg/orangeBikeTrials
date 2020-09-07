package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import org.andengine.engine.handler.IActHandler;

/**
 * Created by vineg on 18.01.2015.
 */
public interface IPhysicsObject extends IActHandler{
    public Body getBody();
}
