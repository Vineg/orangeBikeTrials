package ru.vineg.orangeBikeFree.motorbike;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import ru.vineg.structure.layers.LayerObject2d;
import ru.vineg.orangeBikeFree.MotodroidGameWorld;
import ru.vineg.orangeBikeFree.SceneObject;

/**
 * Created by vineg on 03.11.2014.
 */
public class SimpleSceneObject extends SceneObject{
    public SimpleSceneObject(MotodroidGameWorld gameWorld, LayerObject2d object, FixtureDef fixtureDef) {
        super(gameWorld, object, fixtureDef);
        place();
    }
}
