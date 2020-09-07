package ru.vineg.orangeBikeFree.motorbike;

import ru.vineg.orangeBikeFree.MotodroidGameWorld;
import ru.vineg.structure.layers.IObject2d;
import box2d.ImageObject2dWrapper;
import box2d.PhysicsFactory;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import ru.vineg.orangeBikeFree.StaticSceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 19.09.13
 * Time: 0:57
 * To change this template use File | Settings | File Templates.
 */
public class Finish extends StaticSceneObject {

    private static final FixtureDef APPLE_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.01f, 0.5f, 0.5f, false, MotodroidGameWorld.CATEGORYBIT_OBJECT, MotodroidGameWorld.MASKBIT_OBJECT, (short) 0);

    private Finish(MotodroidGameWorld gameWorld, Vector2 position, Vector2 size, TextureRegion texture, FixtureDef fixtureDef) {
        super(gameWorld, new ImageObject2dWrapper(texture), fixtureDef);
        IObject2d sceneObject = getSceneObject();
        sceneObject.changeOriginToCenter();
        sceneObject.setPosition(position.x,position.y);
        this.bodySize=size.x;
        place();
        getBody().setUserData(this);
    }

    public static Finish createFinish(MotodroidGameWorld gameWorld, Vector2 position) {
        TextureRegion texture = gameWorld.textureManager.finish;

        Vector2 size = new Vector2(80, 80);
        return new Finish(gameWorld, position, size, texture, APPLE_FIXTURE_DEF);
    }

}
