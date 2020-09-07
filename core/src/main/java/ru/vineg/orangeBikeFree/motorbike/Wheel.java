package ru.vineg.orangeBikeFree.motorbike;

import box2d.PhysicsFactory;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import ru.vineg.orangeBikeFree.MotodroidGameWorld;
import ru.vineg.structure.layers.LayerObject2d;
import ru.vineg.orangeBikeFree.SceneObject;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 19.09.13
 * Time: 0:57
 * To change this template use File | Settings | File Templates.
 */
public class Wheel extends SceneObject {

    private final LayerObject2d wheelObject;
    private final SceneObject motoBody;
    private FrictionJointDef brakeFrictionDef;
    private static final FixtureDef WHEEL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(0.04f, 0, 80f, false, MotodroidGameWorld.CATEGORYBIT_WHEEL, MotodroidGameWorld.MASKBIT_WHEEL, (short) 0);
    private Joint brakeJoint;
    private final MotodroidGameWorld gameWorld;


    public Wheel(MotodroidGameWorld gameWorld, SceneObject motoBody, LayerObject2d wheelObject) {
        super(gameWorld, wheelObject, WHEEL_FIXTURE_DEF);
        this.gameWorld = gameWorld;
        this.wheelObject=wheelObject;
        this.motoBody=motoBody;
        //connectionBody = PhysicsFactory.createBoxBody(gameWorld.physicsWorld, centerPosition.x, centerPosition.y, 5, 5, BodyDef.BodyType.KinematicBody, CONNECTOR_FIXTURE_DEF);
    }

    public void onBrake() {
        brakeJoint = gameWorld.getPhysicsWorld().createJoint(brakeFrictionDef);
    }

    public void onBrakeStop() {
        gameWorld.getPhysicsWorld().destroyJoint(brakeJoint);
        //System.out.println("brake stopped");
    }

    @Override
    public void place() {
        super.place();
        getPhysicsConnector().setMaxAngularVelocity(1500f* MathUtils.degreesToRadians);
        Body body = getBody();

        MassData oldMassData = body.getMassData();
        float size = wheelObject.getWidth();
        float mass = 0.00032f * size * size;
        oldMassData.mass = mass;
        body.setMassData(oldMassData);

        brakeFrictionDef = new FrictionJointDef();
        brakeFrictionDef.initialize(motoBody.getBody(), body, body.getWorldCenter());
        brakeFrictionDef.maxTorque = 450 * mass;
    }
}
