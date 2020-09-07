package ru.vineg.orangeBikeFree.motorbike;

import org.andengine.engine.handler.IActHandler;
import ru.vineg.orangeBikeFree.MotodroidGameWorld;
import ru.vineg.structure.layers.IObject2d;
import box2d.PhysicsFactory;
import box2d.util.constants.PhysicsConstants;
import com.badlogic.gdx.gleed.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import org.andengine.engine.handler.IUpdateHandler;
import ru.vineg.orangeBikeFree.Motodroid;
import ru.vineg.orangeBikeFree.SceneObject;
import ru.vineg.orangeBikeFree.entities.GameTextureManager;
import ru.vineg.geometry.Line;
import ru.vineg.structure.layers.LayerObject2d;

/**
 * Created with IntelliJ IDEA.
 * User: kostik
 * Date: 9/14/13
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Motorbike extends Actor {
    //private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 1f);
    private static final FixtureDef BODY_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.8f, 0f, 0f, false, MotodroidGameWorld.CATEGORYBIT_VIRTUAL, MotodroidGameWorld.MASKBIT_VIRTUAL, (short) 0);
    //    private static final FixtureDef BODY_FIXTURE_DEF = PhysicsFactory.createFixtureDef(10f, 0f, 0f, false, MotodroidGameWorld.CATEGORYBIT_VIRTUAL, MotodroidGameWorld.MASKBIT_VIRTUAL, (short) 0);
    private static final FixtureDef HEAD_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.25f, 0.5f, 0.5f, false, MotodroidGameWorld.CATEGORYBIT_HEAD, MotodroidGameWorld.MASKBIT_HEAD, (short) 0);

    private final float bodyMass;
    private final float wheelMass;
    private final float[] mountPoints;
    private final Line initialBackMountLine;
    private final Line initialFrontMountLine;
    private final MotorbikeUpdateHandler updateHandler;
    private final int offsetY;
    private LevelTextureObject frontCoverObject;
    private LevelTextureObject backMountObject;
    private LevelTextureObject frontMountObject;
    private LevelTextureObject sprocketCoverObject;
    private LevelTextureObject backWheelObject;
    private LevelTextureObject frontWheelObject;
    private LevelTextureObject bikeObject;
    private Joint headJoint = null;
    private SceneObject hand;

    //final Body backWheel;
    Wheel frontWheel;
    final SceneObject body;
    private Wheel backWheel;
    private final SceneObject head;


    private float control;
    private float rotationControl = 0;
    private float rotationDirection = 0;
    public int direction = 1;
    int thrust = 1100;
    private float rotationTime = 70f / 60f / 6f;
    private float rotationPause = 230f / 60f / 6f;
    private float rotationTimeLeft;
    private float rotationPauseLeft;
    private float rotationPower = 170f;//impulse
    private float basicRotationVelocity = 12f;//rotation speed during rotation itself
    private final MotodroidGameWorld gameWorld;
    private float maxAngularVelocity = 130f;
    public boolean hasSound = false;
    private final float bikeScale;
    private LayerObject2d handObject;
    private LevelRenderer levelRenderer;
    private Layer bikeData;
    private Vector2 tmp=new Vector2(),tmp1=new Vector2();
    private Vector2 newPosition=new Vector2();

    public Motorbike(final MotodroidGameWorld gameWorld, Vector2 position) {
        super();

        bikeScale = 1f;

        this.gameWorld = gameWorld;

//        bikeObject = new LayerObject();

        Level bike = Motodroid.kryo.readObject(new ByteBufferInput(Motodroid.bikeData), Level.class);

        levelRenderer = new LevelRenderer(bike);
        gameWorld.getScene().levelRenderers.add(levelRenderer);


        bikeData = bike.getLayer(0);
        Array<TextureElement> textureElements = bikeData.getTextures();

        LevelTextureObject headObject = null;

        bikeObject=new LevelTextureObject(bikeData.getTexture("bike"));

        for (TextureElement levelObject : textureElements) {
            if(levelObject.getName().equals("body")){
                continue;
            }
            LevelTextureObject object = new LevelTextureObject(levelObject);
            switch (levelObject.getName()) {

                case "hand":
                    handObject = object;
                    break;
                case "head":
                    headObject = object;
                    break;
                case "frontWheel":
                    frontWheelObject = object;
                    break;
                case "backWheel":
                    backWheelObject = object;
                    break;
                case "sprocketCover":
                    sprocketCoverObject = object;
                    break;
                case "backMount":
                    backMountObject = object;
                    break;
                case "frontMount":
                    frontMountObject = object;
                    break;
                case "frontCover":
                    frontCoverObject=object;
                    break;
                default:
                    break;
            }
            object.changeParent(bikeObject);
        }


        mountPoints = ((PathElement) bikeData.getObject("mountPoints")).getPolyline().getVertices();

        Vector2 mountPoint = tmp.set(mountPoints[0], mountPoints[1]);
        initialBackMountLine =new Line(backMountObject.globalToLocal(mountPoint.cpy()),backMountObject.globalToLocal(tmp1.set(backWheelObject.getX(),backWheelObject.getY())));
        offsetY = 30;
        bikeObject.addGlobalPoint(mountPoint.cpy().add(0, offsetY));

        mountPoint = tmp.set(mountPoints[2], mountPoints[3]);
        initialFrontMountLine =new Line(frontMountObject.globalToLocal(mountPoint.cpy()),frontMountObject.globalToLocal(tmp1.set(frontWheelObject.getX(),frontWheelObject.getY())));
        bikeObject.addGlobalPoint(mountPoint.cpy().add(0, offsetY));

//        bikeObject.changeOrigin((frontWheelObject.getX() + backWheelObject.getX()) / 2, bikeObject.getY());=

        this.bikeObject.setPosition(position.x, position.y,true);


        this.bikeObject.setScale(bikeScale, bikeScale);

        body = new SceneObject(gameWorld, bikeObject, BODY_FIXTURE_DEF);
        bikeObject.setObjectOrigin(bikeObject.getWidth()/2,bikeObject.getHeight()/2- offsetY);
        body.setOffsetY(-offsetY/2);
        body.place();
        body.updateChildren=false;
        body.addGlobalPoint(bikeObject.getGlobalPoint(new Vector2(),0));
        body.addGlobalPoint(bikeObject.getGlobalPoint(new Vector2(),1));



        backWheel = new Wheel(gameWorld, body, backWheelObject);
        backWheel.place();
        backWheel.changeParent(body, true);
        frontWheel = new Wheel(gameWorld, body, frontWheelObject);
        frontWheel.place();
        frontWheel.changeParent(body, true);
        bodyMass = body.getBody().getMass();
        wheelMass = frontWheel.getBody().getMass();
        handObject.changeParent(body,true);

//        body.graphics.setScale(1);


        head = new SceneObject(gameWorld, headObject, HEAD_FIXTURE_DEF);
        head.bodySize = headObject.getWidth() / 1.1f * headObject.getScaleX();
        head.place();
        head.changeParent(body, true);
        head.getBody().setUserData("head");

        jointHead();

        addHand();

        updateHandler = new MotorbikeUpdateHandler();
        gameWorld.getPhysicsWorld().registerUpdateHandler(updateHandler);
        gameWorld.getPhysicsWorld().registerUpdateHandler(body.getPhysicsConnector());
        gameWorld.getPhysicsWorld().registerUpdateHandler(head.getPhysicsConnector());
        gameWorld.getPhysicsWorld().registerUpdateHandler(backWheel.getPhysicsConnector());
        gameWorld.getPhysicsWorld().registerUpdateHandler(frontWheel.getPhysicsConnector());
        gameWorld.getPhysicsWorld().registerUpdateHandler(new IActHandler() {
//            @Override
//            public void onUpdate(float pSecondsElapsed) {
//                updateGraphics();
//            }


            @Override
            public void act(float pSecondsElapsed) {
                updateGraphics();
            }

            Vector2 tmp=new Vector2();
            private void updateGraphics() {
                Vector2 position = backWheel.getPosition();
                updateMounts();
                sprocketCoverObject.setPosition(position.x, position.y);
                sprocketCoverObject.setRotation((backMountObject.getRotation()));
                position=frontWheel.getPosition();
                frontCoverObject.setPosition(position.x, position.y);
                frontCoverObject.setRotation((frontMountObject.getRotation()));
                tmp.set(mountPoints[0], mountPoints[1]);

            }

            Line backMountLine=new Line();
            Line frontMountLine=new Line();
            Vector2 offset=new Vector2();
            private void updateMounts() {
                Vector2 mountPoint = body.getGlobalPoint(tmp, 0);
//                Vector2 offset = this.offset.set(0, offsetY).rotateRad(body.getRotation());
                backMountLine.set(mountPoint.add(offset),backWheel.getPosition());
                mountPoint = body.getGlobalPoint(tmp, 1);
                frontMountLine.set(mountPoint.add(offset),frontWheel.getPosition());

                backMountObject.transform(initialBackMountLine,backMountLine);
                frontMountObject.transform(initialFrontMountLine,frontMountLine);
            }


        });

    }


    private void jointHead() {
        if (headJoint != null) {
            gameWorld.getPhysicsWorld().destroyJoint(headJoint);
        }
        head.getPhysicsStartPosition(newPosition).scl(PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
        head.getBody().setTransform(newPosition.x, newPosition.y, head.getBody().getAngle());
        WeldJointDef headJointDef = new WeldJointDef();
        headJointDef.initialize(head.getBody(), body.getBody(), newPosition);
        headJoint = gameWorld.getPhysicsWorld().createJoint(headJointDef);
    }

    public void swap() {
        Wheel tmpWheel = frontWheel;
        frontWheel = backWheel;
        backWheel = tmpWheel;

        Vector2 tmp = new Vector2().set(frontWheel.getStartPosition());
        frontWheel.setStartPosition(backWheel.getStartPosition());
        backWheel.setStartPosition(tmp);

        IObject2d tmpShape = frontWheel.physicsConnector.mShape;
        frontWheel.physicsConnector.mShape=backWheel.physicsConnector.mShape;
        backWheel.physicsConnector.mShape=tmpShape;

        direction *= -1;
        hand.dispose();
        bikeObject.setScale(bikeScale * direction, bikeScale);
        hand = addHand();
        jointHead();
    }


    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getSpeed() {
        return body.getBody().getLinearVelocity().len();
    }

    public void setControl(float controlY, float controlX) {
        this.control = controlY;
        this.rotationControl = controlX;
    }

    Vector2 size = new Vector2(1, 1);


    private SceneObject addHand() {
        handObject.reset();
        handObject.setScale(direction,1);
        hand = new SimpleSceneObject(gameWorld, handObject, BODY_FIXTURE_DEF);
        RevoluteJointDef handJointDef = new RevoluteJointDef();
        handJointDef.initialize(hand.getBody(), body.getBody(), hand.getBody().getPosition());
        gameWorld.getPhysicsWorld().createJoint(handJointDef);
        return hand;
    }

    public void dispose() {
        hand.dispose();
        body.dispose();
        frontWheel.dispose();
        backWheel.dispose();
        head.dispose();
        gameWorld.scene.levelRenderers.remove(levelRenderer);
        gameWorld.getPhysicsWorld().clearPhysicsConnectors();
        gameWorld.getPhysicsWorld().removeUpdateHandler(updateHandler);
    }

    public float getWheelSpeed() {
        return -backWheel.getBody().getAngularVelocity() * direction;
    }

    public float getMaxWheelSpeed() {
        return maxAngularVelocity;
    }

    public float getControl() {
        return control;
    }

//    public BikePosition getFullPosition() {
//        return new BikePosition(wheel1Pos, wheel1Vel, wheel2Pos, wheel2Vel, headPos, headVel, bodyPos);
//    }


    private class MotorbikeUpdateHandler implements IActHandler {
        public float pSecondsElapsed;
        public boolean isBrake = false;
        public float elasticity = 30f * bodyMass;
        public float frictionPower = elasticity / 20f;
        private Vector2 obj1Point=new Vector2();

        @Override
        public void act(float pSecondsElapsed) {
            hasSound = true;
            this.pSecondsElapsed = pSecondsElapsed;
            wheelInteraction(body, frontWheel);
            wheelInteraction(body, backWheel);
            //headInteraction(body, head);
            wheelBoost(frontWheel);
            rotateBody(pSecondsElapsed);
        }



        private void rotateBody(float pSecondsElapsed) {
            float rotationVelocity;
            float angularVelocity = body.getBody().getAngularVelocity();
            rotationVelocity = basicRotationVelocity * Math.signum(rotationDirection);


            if (rotationPauseLeft <= 0) {
                if (rotationControl != 0) {
                    rotationTimeLeft = (rotationTime);
                    rotationDirection = rotationControl;
                    rotationPauseLeft = rotationPause;
                    rotationVelocity = basicRotationVelocity * Math.signum(rotationDirection);
                    body.getBody().applyAngularImpulse(-rotationVelocity * bodyMass, true);
                }
            }


            if (rotationDirection > 0) {
                body.getBody().applyTorque(bodyMass * rotationPower / interval(angularVelocity, -5f, -2f), true);
            } else if (rotationDirection < 0) {
                body.getBody().applyTorque(bodyMass * rotationPower / interval(angularVelocity, 2f, 5f), true);
            } else {

            }
            //last rotation tick
            if (rotationTimeLeft <= 0 && rotationDirection != 0) {
                body.getBody().applyAngularImpulse(rotationVelocity * bodyMass, true);
                rotationDirection = 0;
                rotationTimeLeft = 0;
            }


            if (rotationTimeLeft > rotationTime * 2 / 3) {
                hand.getBody().setAngularVelocity(body.getBody().getAngularVelocity() + rotationVelocity * 1);
            } else if (rotationTimeLeft > 0) {
                hand.getBody().setAngularVelocity(body.getBody().getAngularVelocity() - rotationVelocity * 0.5f);
            } else {
                hand.getBody().setAngularVelocity(body.getBody().getAngularVelocity() - 5 * hand.getBody().getAngle() + 5 * body.getBody().getAngle());
            }

            rotationTimeLeft -= pSecondsElapsed;
            rotationPauseLeft -= pSecondsElapsed;

        }

        private float interval(float value, float min, float max) {
            return Math.max(Math.min(value, max), min);
        }


        private void wheelBoost(SceneObject firstWheel) {
            if (control > 0) {
                accelerate();
                if (isBrake)
                    brakeStop();
            } else if (control < 0) {
                if (!isBrake)
                    brakeStart();
            } else {
                if (isBrake)
                    brakeStop();
            }
        }

        private void accelerate() {

            float motorCapacity = thrust * bodyMass;
            float wheelAngularVelocity = getWheelSpeed();
            float torque = -direction * motorCapacity / Math.max(wheelAngularVelocity, 60f);
//                System.out.println(wheelAngularVelocity);
            if (wheelAngularVelocity < maxAngularVelocity) {
                backWheel.getBody().applyTorque(torque, true);
                body.getBody().applyTorque(-torque, true);
            }

        }

        private void brakeStart() {
            frontWheel.onBrake();
            backWheel.onBrake();
            isBrake = true;
        }

        private void brakeStop() {
            frontWheel.onBrakeStop();
            backWheel.onBrakeStop();
            isBrake = false;
        }


        public Vector2
                radiusVec = new Vector2(),
                obj2Velocity = new Vector2(),
                obj1Velocity = new Vector2(),
                force = new Vector2(),
                radius = new Vector2();

        private void wheelInteraction(SceneObject body, SceneObject wheel) {
            Vector2 obj2Point = wheel.getBody().getWorldCenter();
            wheel.getPhysicsStartPosition(obj1Point).scl(PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
            //new SimpleSceneObject(gameWorld, obj1Point.cpy().mul(PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT), motorbikeTextures.wheelTexture, WHEEL_FIXTURE_DEF);
            radius.set(obj1Point).scl(-1).add(obj2Point);
            radiusVec.set(radius).nor();


            obj2Velocity.set(wheel.getBody().getLinearVelocity());
            obj1Velocity.set(body.getBody().getLinearVelocityFromWorldPoint(wheel.getBody().getPosition()));
            //Vector2i tangent = radiusVec.cpy();
            //float t = tangent.x;
            //tangent.x = tangent.y;
            //tangent.y = t;
            //tangent.nor();
            Vector2 velocityDifference = obj2Velocity.add(obj1Velocity.scl(-1));
            //frictionPower=Math.min(frictionPower, velocityDifference.len()*wheelMass/pSecondsElapsed);
            //frictionPower=0;
            Vector2 frictionForce = velocityDifference.scl(-frictionPower);
            float k = Math.abs(Math.max(radiusVec.dot(velocityDifference), 0));

            force.set(radiusVec).scl((-elasticity * Math.min(radius.len(), 4) / (1 + 0.015f * k))).add(frictionForce);

            //float maxForce = 2 * radiusVec.len() * wheelMass * 60f * 60f;
            wheel.getBody().applyForce(force, obj2Point, true);
            body.getBody().applyForce(force.scl(-1), obj1Point, true);

        }
    }
}
