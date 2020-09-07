package ru.vineg.orangeBikeFree;

import box2d.ImageObject2dWrapper;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 19.09.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
public class StaticSceneObject extends SceneObject {

    public StaticSceneObject(MotodroidGameWorld gameWorld, ImageObject2dWrapper sprite, FixtureDef fixtureDef) {
        super(gameWorld, sprite, fixtureDef);
        bodyType= BodyDef.BodyType.StaticBody;
    }

    @Override
    public void placeOnScene() {
        gameWorld.getScene().removeStaticSceneObject(this);
        gameWorld.getScene().addStaticSceneObject(this);
    }

}
