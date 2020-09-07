package ru.vineg.orangeBikeFree.motorbike;

import ru.vineg.orangeBikeFree.MotodroidGameWorld;
import ru.vineg.structure.layers.IObject2d;
import box2d.ImageObject2dWrapper;
import box2d.PhysicsFactory;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import ru.vineg.orangeBikeFree.StaticSceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 19.09.13
 * Time: 0:57
 * To change this template use File | Settings | File Templates.
 */
public class Killer extends StaticSceneObject {

    private static final FixtureDef KILLER_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.01f, 0.5f, 0.5f, false, MotodroidGameWorld.CATEGORYBIT_OBJECT, MotodroidGameWorld.MASKBIT_OBJECT, (short) 0);
    private float rotationSpeed = 3.14f/3f;
    private float rand = (float) Math.random();

    public Killer(MotodroidGameWorld gameWorld, Vector2 centerPosition, Vector2 size, TextureRegion texture, FixtureDef fixtureDef) {
        super(gameWorld, new ImageObject2dWrapper(texture), fixtureDef);
        IObject2d sceneObject = getSceneObject();
        sceneObject.changeOriginToCenter();
        sceneObject.setScale(size.x/sceneObject.getWidth());
        sceneObject.setPosition(centerPosition.x,centerPosition.y);
        this.bodySize=size.x;
        place();
        getBody().setUserData(this);
        getBody().setAngularVelocity(rotationSpeed);
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        float t = gameWorld.getPhysicsWorld().getTime();
        Body body = getBody();
        Vector2 position = body.getPosition();
        body.setTransform(position.x, position.y, (t * rotationSpeed * (1 + rand / 4)));
//        if (Math.random() < 0.1) {
//            System.out.println("update"+this.rand);
//        }
        super.onUpdate(pSecondsElapsed);
    }


    public static Killer createKiller(MotodroidGameWorld gameWorld, Vector2 position) {
        TextureRegion texture = gameWorld.textureManager.killer;

        Vector2 size = new Vector2(30, 30);
        return new Killer(gameWorld, position, size, texture, KILLER_FIXTURE_DEF);
    }

}
