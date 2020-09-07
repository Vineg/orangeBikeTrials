package ru.vineg.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import net.dermetfan.gdx.graphics.g2d.AnimatedSprite;
import org.andengine.engine.handler.IUpdateHandler;

/**
 * Created with IntelliJ IDEA.
 * User: vineg
 * Date: 10/2/13
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IScene {

    void act(float v);

    void registerUpdateHandler(IUpdateHandler cameraController);

    void removeUpdateHandler(IUpdateHandler handler);

    void addActor(Actor actor);

    ICamera2d getCameraObject();


    void reset();

    void addSprite(Sprite graphics);

    void removeSprite(Sprite graphics);

    boolean isInCamera(float x, float y);

    void addStaticSprite(Sprite sprite);

    void removeStaticSprite(Sprite sprite);

    void addStaticSprite(Sprite sprite, int layer);

    void addAnimation(AnimatedSprite animation);

    void addSprite(Sprite object, int layer);
}
