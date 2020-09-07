package box2d;

import box2d.util.constants.PhysicsConstants;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import org.andengine.engine.handler.IActHandler;
import org.andengine.engine.handler.IUpdateHandler;
import ru.vineg.game.GameWorld;
import ru.vineg.game.IGameWorld;
import ru.vineg.orangeBikeFree.IPhysicsObject;
import ru.vineg.structure.layers.IObject2d;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 18:51:22 - 05.07.2010
 */
public class PhysicsConnector implements IUpdateHandler, PhysicsConstants, IActHandler {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    public IObject2d mShape;
    protected final IPhysicsObject mObject;

    protected boolean mUpdatePosition;
    protected boolean mUpdateRotation;
    protected final float mPixelToMeterRatio;
    protected float maxAngularVelocity;

    private IGameWorld gameWorld;
    private boolean removed;

    private Vector2 lastPosition;
    private int offsetY;

    // ===========================================================
    // Constructors
    // ===========================================================

    public PhysicsConnector(final IObject2d pAreaShape, final Body pBody) {
        this(pAreaShape, pBody, true, true);
    }

    public PhysicsConnector(final IObject2d pAreaShape, final Body pBody, final float pPixelToMeterRatio) {
        this(pAreaShape, pBody, true, true, pPixelToMeterRatio);
    }

    public PhysicsConnector(final IObject2d pAreaShape, final Body pBody, final boolean pUdatePosition, final boolean pUpdateRotation) {
        this(pAreaShape, pBody, pUdatePosition, pUpdateRotation, PIXEL_TO_METER_RATIO_DEFAULT);
    }


    public PhysicsConnector(IObject2d pAreaShape, Body pBody, boolean pUdatePosition, boolean pUpdateRotation, float pixelToMeterRatioDefault) {
        this(pAreaShape, pBody, pUdatePosition, pUpdateRotation, pixelToMeterRatioDefault, null);
    }

    public PhysicsConnector(final IObject2d pAreaShape, final Body pBody, final boolean pUdatePosition, final boolean pUpdateRotation, final float pPixelToMeterRatio, GameWorld gameWorld) {
        this(pAreaShape, new SimpleBody(pBody), pUdatePosition, pUpdateRotation, pPixelToMeterRatio, gameWorld);
    }

    public PhysicsConnector(IObject2d object, IPhysicsObject sceneObject, boolean pUdatePosition, boolean pUpdateRotation, float pPixelToMeterRatio, IGameWorld gameWorld) {
        this.mShape = object;
        this.mObject = sceneObject;
//        this.lastPosition = pBody.getgetPosition();

        this.mUpdatePosition = pUdatePosition;
        this.mUpdateRotation = pUpdateRotation;
        this.mPixelToMeterRatio = pPixelToMeterRatio;

        this.gameWorld = gameWorld;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public IObject2d getShape() {
        return this.mShape;
    }

    public Body getBody() {
        return this.mObject.getBody();
    }

    public boolean isUpdatePosition() {
        return this.mUpdatePosition;
    }

    public boolean isUpdateRotation() {
        return this.mUpdateRotation;
    }

    public void setUpdatePosition(final boolean pUpdatePosition) {
        this.mUpdatePosition = pUpdatePosition;
    }

    public void setUpdateRotation(final boolean pUpdateRotation) {
        this.mUpdateRotation = pUpdateRotation;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    Vector2 offset=new Vector2();
    @Override
    public void onUpdate(final float pSecondsElapsed) {
        if (removed) {
            return;
        }
        final IObject2d shape = this.mShape;
        Body body = mObject.getBody();
        if(body==null){return;}
        final Vector2 position = body.getPosition();
//        if (GameVars.smoothPosition) {
//            position.add((lastPosition.scl(-1f).add(position).scl(Gdx.graphics.getDeltaTime() * 30)));
//        }


//        lastPosition.set(body.getPosition());

        final float meterToPixelRatio = PhysicsConstants.METER_TO_PIXEL_RATIO_DEFAULT;


        if (this.mUpdatePosition) {
            position.scl(meterToPixelRatio);
            shape.setPosition(position.add(offset.set(0,-2*offsetY).rotateRad(body.getAngle())));
        }

        if (this.mUpdateRotation) {
            if (maxAngularVelocity > 0) {
                shape.setRotation(shape.getRotation() + pSecondsElapsed * MathUtils.range(body.getAngularVelocity(), -maxAngularVelocity, maxAngularVelocity));
            } else {
                final float angle = body.getAngle();
                shape.setRotation(angle);
            }
        }

    }

    @Override
    public void reset() {
        removed = true;
    }

    public void setMaxAngularVelocity(float maxAngularVelocity) {
        this.maxAngularVelocity = maxAngularVelocity;
    }

    @Override
    public void act(float pSecondsElapsed) {
        onUpdate(pSecondsElapsed);
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
