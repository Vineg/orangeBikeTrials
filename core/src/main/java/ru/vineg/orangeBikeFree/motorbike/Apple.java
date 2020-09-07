package ru.vineg.orangeBikeFree.motorbike;

import ru.vineg.orangeBikeFree.MotodroidGameWorld;
import ru.vineg.structure.layers.IObject2d;
import box2d.ImageObject2dWrapper;
import box2d.MathUtils;
import box2d.PhysicsFactory;
import box2d.util.constants.PhysicsConstants;
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
public class Apple extends StaticSceneObject {

    private static final FixtureDef APPLE_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.01f, 0.5f, 0.5f, false, MotodroidGameWorld.CATEGORYBIT_OBJECT, MotodroidGameWorld.MASKBIT_OBJECT, (short) 0);
    private double rand;
    private double rotationFreq = 1.2;
    private double rotationAmp = MathUtils.degToRad(13);
    private double linearAmp = 11 * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
    private double y;

    public Apple(MotodroidGameWorld gameWorld, Vector2 position, Vector2 size, TextureRegion texture, FixtureDef fixtureDef) {
        super(gameWorld, new ImageObject2dWrapper(texture), fixtureDef);
        IObject2d sceneObject = getSceneObject();
        sceneObject.setScale(size.x/sceneObject.getWidth());
        sceneObject.changeOriginToCenter();
        sceneObject.setPosition(position.x,position.y);
        this.bodySize=size.x;
        place();
        getBody().setUserData(this);
        rand = (position.x + position.y) % 10 / 10f;
        this.y = getBody().getPosition().y;
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        if(!exists()){
            return;
        }
        float t= gameWorld.getPhysicsWorld().getTime();
        getBody().setTransform(getBody().getPosition().x, (float) (y + linearAmp * Math.cos(rotationFreq * t + rand * 360)), (float) (rotationAmp * Math.cos(2 * t * rotationFreq * (1 + rand / 4))));

        super.onUpdate(pSecondsElapsed);
    }


    public static Apple createApple(MotodroidGameWorld gameWorld, Vector2 position) {
        TextureRegion texture = gameWorld.textureManager.apple;

        Vector2 size = new Vector2(40, 40);
        return new Apple(gameWorld, position, size, texture, APPLE_FIXTURE_DEF);
    }

}
