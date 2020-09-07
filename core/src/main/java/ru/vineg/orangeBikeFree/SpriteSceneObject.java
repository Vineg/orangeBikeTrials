package ru.vineg.orangeBikeFree;

import box2d.ImageObject2dWrapper;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Created by vineg on 02.11.2014.
 */
public class SpriteSceneObject extends SceneObject {
    public SpriteSceneObject(MotodroidGameWorld gameWorld, TextureRegion textureRegion, FixtureDef fixtureDef) {
        super(gameWorld, new ImageObject2dWrapper(textureRegion), fixtureDef);
    }
}
