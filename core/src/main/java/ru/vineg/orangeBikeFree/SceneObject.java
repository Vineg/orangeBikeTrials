package ru.vineg.orangeBikeFree;

import ru.vineg.game.IGameWorld;
import ru.vineg.game.IPhysicsWorld;
import ru.vineg.structure.layers.IObject2d;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import box2d.PhysicsConnector;
import box2d.PhysicsFactory;
import box2d.util.constants.PhysicsConstants;
import org.andengine.engine.handler.IUpdateHandler;
import ru.vineg.structure.layers.LayerObject2d;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 19.09.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
public class SceneObject extends LayerObject2d implements IVector2,IUpdateHandler,IPhysicsObject{
    public float bodySize;
    private Body body;
    protected final IGameWorld gameWorld;
    public final LayerObject2d object;
    private float rotation;
    protected BodyDef.BodyType bodyType=BodyDef.BodyType.DynamicBody;
    private final FixtureDef fixtureDef;
    private boolean removed;
    public PhysicsConnector physicsConnector;
    private int offsetY=0;


    public SceneObject(IGameWorld gameWorld, LayerObject2d object, FixtureDef fixtureDef) {

        this.object = object;
        this.gameWorld = gameWorld;
        this.bodyType = BodyDef.BodyType.DynamicBody;
        this.fixtureDef = fixtureDef;
    }

    public PhysicsConnector getPhysicsConnector() {
        return physicsConnector;
    }

    public IObject2d getSceneObject() {
        return object;
    }

    public Body getBody() {

        if (exists()) {
            return body;
        } else {
            return null;
        }
    }



    public Vector2 getPosition() {
        return body.getPosition().scl(PhysicsConstants.METER_TO_PIXEL_RATIO_DEFAULT);
    }



    public boolean exists() {
        return !removed;
    }


    @Override
    public float getWidth() {
        return bodySize;
    }

    @Override
    public float getHeight() {
        return bodySize;
    }

    @Override
    public float getX() {
        return (float) (body.getPosition().x*PhysicsConstants.METER_TO_PIXEL_RATIO_DEFAULT+offsetY*Math.sin(body.getAngle()));
    }

    @Override
    public float getY() {
        return (float) (body.getPosition().y*PhysicsConstants.METER_TO_PIXEL_RATIO_DEFAULT-offsetY*Math.cos(body.getAngle()));
    }

    public void setRotation(float radians) {
        getSceneObject().setRotation(radians);
        this.rotation = radians;
    }


    @Override
    public float getRotation() {
        return body.getAngle();
    }

    @Override
    public float getOriginX() {
        return object.getOriginX();
    }

    @Override
    public float getOriginY() {
        return object.getOriginY();
    }

    @Override
    public float getScaleX() {
        return object.getScaleX();
    }

    @Override
    public float getScaleY() {
        return object.getScaleY();
    }

    public void place(float radius){
        place(gameWorld.getPhysicsWorld(),2*radius);
    }

    public void place() {
        place(gameWorld.getPhysicsWorld(),Math.abs(object.getWidth()*object.getScaleX()));
    }

    public void place(IPhysicsWorld physicsWorld, float bodySize) {
        setStartPosition(object.getStartPosition());
        this.bodySize=bodySize;

        body = PhysicsFactory.createCircleBody(physicsWorld, object.getX(), object.getY()+offsetY, bodySize / 2, rotation, bodyType, fixtureDef);
        body.setTransform(body.getPosition(), object.getRotation());
        body.setUserData(this);
        placeOnScene();
        physicsConnector = new PhysicsConnector(object, this, true, true, 1, gameWorld);
//        physicsConnector.setOffsetY(offsetY);
        physicsWorld.registerPhysicsConnector(physicsConnector);
        physicsWorld.registerUpdateHandler(this);
    }

    public void setUpdateRotation(boolean b){
        physicsConnector.setUpdateRotation(b);
    }

    public void dispose() {
        removed = true;
        gameWorld.getPhysicsWorld().safeDestroy(body);
        gameWorld.getPhysicsWorld().removeUpdateHandler(this);
        gameWorld.getScene().removeUpdateHandler(this);
        gameWorld.getPhysicsWorld().unregisterPhysicsConnector(physicsConnector);
        object.dispose();
    }

    @Override
    public void setObjectOrigin(float x, float y) {
        object.setObjectOrigin(x,y);
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
    }

    @Override
    public void reset() {
    }

    public void placeOnScene(){};

    @Override
    public void act(float pSecondsElapsed) {

    }


    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }
}
