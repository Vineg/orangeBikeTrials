package box2d;

import com.badlogic.gdx.math.Vector2;

/**
 * A subclass of {@link PhysicsWorld} that tries to achieve a specific amount of steps per second.
 * When the time since the last step is bigger long the steplength, additional steps are executed.
 * <p/>
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 12:39:42 - 25.07.2010
 */
public class FixedStepPhysicsWorld extends PhysicsWorld {
    // ===========================================================
    // Constants
    // ===========================================================

    public static final int STEPSPERSECOND_DEFAULT = 45;

    // ===========================================================
    // Fields
    // ===========================================================

    private final float mTimeStep;
    private final int mMaximumStepsPerUpdate;
    private float mSecondsElapsedAccumulator;

    // ===========================================================
    // Constructors
    // ===========================================================

    public FixedStepPhysicsWorld(final int pStepsPerSecond, final Vector2 pGravity, final boolean pAllowSleep) {
        this(pStepsPerSecond, Integer.MAX_VALUE, pGravity, pAllowSleep);
    }

    public FixedStepPhysicsWorld(final int pStepsPerSecond, final int pMaximumStepsPerUpdate, final Vector2 pGravity, final boolean pAllowSleep) {
        super(pGravity, pAllowSleep);
        this.mTimeStep = 1.0f / pStepsPerSecond;
        this.mMaximumStepsPerUpdate = pMaximumStepsPerUpdate;
    }

    public FixedStepPhysicsWorld(final int pStepsPerSecond, final Vector2 pGravity, final boolean pAllowSleep, final int pVelocityIterations, final int pPositionIterations) {
        this(pStepsPerSecond, Integer.MAX_VALUE, pGravity, pAllowSleep, pVelocityIterations, pPositionIterations);
    }

    public FixedStepPhysicsWorld(final int pStepsPerSecond, final int pMaximumStepsPerUpdate, final Vector2 pGravity, final boolean pAllowSleep, final int pVelocityIterations, final int pPositionIterations) {
        super(pGravity, pAllowSleep, pVelocityIterations, pPositionIterations);
        this.mTimeStep = 1.0f / pStepsPerSecond;
        this.mMaximumStepsPerUpdate = pMaximumStepsPerUpdate;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public void onUpdate(final float pSecondsElapsed) {

        final float stepLength = this.mTimeStep;
        super.onUpdate(stepLength);
//        for (int i = 0; i < mPositionIterations; i++) {
//            this.mRunnableHandler.onUpdate(stepLength);
//            this.mUpdateHandlers.onUpdate(stepLength);
//
//            this.mWorld.step(stepLength/mPositionIterations, 1, 1);
//        }
//
//        this.mPhysicsConnectorManager.onUpdate(stepLength);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
