package box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import org.andengine.engine.handler.IActHandler;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.ActHandlerList;
import org.andengine.engine.handler.runnable.RunnableHandler;
import ru.vineg.game.IPhysicsWorld;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 15:18:19 - 15.07.2010
 */
public class PhysicsWorld implements IUpdateHandler, IPhysicsWorld {
    // ===========================================================
    // Constants
    // ===========================================================


    public static final int VELOCITY_ITERATIONS_DEFAULT = 10;
    public static final int POSITION_ITERATIONS_DEFAULT = 1;

    // ===========================================================
    // Fields
    // ===========================================================

    protected final PhysicsConnectorManager mPhysicsConnectorManager = new PhysicsConnectorManager();
    protected final RunnableHandler mRunnableHandler = new RunnableHandler();
    protected final World mWorld;

    protected int mVelocityIterations = VELOCITY_ITERATIONS_DEFAULT;
    protected int mPositionIterations = POSITION_ITERATIONS_DEFAULT;

    public ActHandlerList mUpdateHandlers = new ActHandlerList();
    private ArrayList<Body> destroyedBodies = new ArrayList<Body>();
    private float time = 0;
    private boolean locked = false;
    protected ArrayList<Body> dynamicBodies = new ArrayList<>();

    // ===========================================================
    // Constructors
    // ===========================================================

    public PhysicsWorld(final Vector2 pGravity, final boolean pAllowSleep) {
        this(pGravity, pAllowSleep, VELOCITY_ITERATIONS_DEFAULT, POSITION_ITERATIONS_DEFAULT);
    }

    public PhysicsWorld(final Vector2 pGravity, final boolean pAllowSleep, final int pVelocityIterations, final int pPositionIterations) {
        this.mWorld = new World(pGravity, pAllowSleep);
        this.mVelocityIterations = pVelocityIterations;
        this.mPositionIterations = pPositionIterations;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    //	public World getWorld() {
    //		return this.mWorld;
    //	}

    public int getPositionIterations() {
        return this.mPositionIterations;
    }

    public void setPositionIterations(final int pPositionIterations) {
        this.mPositionIterations = pPositionIterations;
    }

    public int getVelocityIterations() {
        return this.mVelocityIterations;
    }

    public void setVelocityIterations(final int pVelocityIterations) {
        this.mVelocityIterations = pVelocityIterations;
    }

    public PhysicsConnectorManager getPhysicsConnectorManager() {
        return this.mPhysicsConnectorManager;
    }

    public void clearPhysicsConnectors() {
        this.mPhysicsConnectorManager.clear();
    }

    @Override
    public void registerUpdateHandler(IActHandler updateHandler) {
        mUpdateHandlers.add(updateHandler);
    }

    @Override
    public void removeUpdateHandler(IActHandler updateHandler) {
        mUpdateHandlers.remove(updateHandler);
    }

    public void registerPhysicsConnector(final PhysicsConnector pPhysicsConnector) {
        this.mPhysicsConnectorManager.add(pPhysicsConnector);
    }

    public void unregisterPhysicsConnector(final PhysicsConnector pPhysicsConnector) {
        this.mPhysicsConnectorManager.remove(pPhysicsConnector);
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public void onUpdate(float pSecondsElapsed) {
        pSecondsElapsed /= 1.5f;
//        pSecondsElapsed = MathUtils.range(pSecondsElapsed, 1f / maxFps, 1f / minFps)*0.7f;
        //System.out.println(pSecondsElapsed);
        destroyBodies();
        pSecondsElapsed /= this.mPositionIterations;
        for (int i = 0; i < this.mPositionIterations; i++) {
            this.mWorld.step(pSecondsElapsed, 5, 5);
            this.mPhysicsConnectorManager.onUpdate(pSecondsElapsed);
            this.mUpdateHandlers.onUpdate(pSecondsElapsed);
            this.mRunnableHandler.onUpdate(pSecondsElapsed);


            time += pSecondsElapsed;
        }
    }

    private void destroyBodies() {
        for (int i = 0; i < destroyedBodies.size(); i++) {
            destroyBody(destroyedBodies.get(i));
        }
        destroyedBodies.clear();
    }

    @Override
    public void reset() {
        // TODO Reset all native physics objects !?!??!
        this.mPhysicsConnectorManager.reset();
        this.mRunnableHandler.reset();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public void postRunnable(final Runnable pRunnable) {
        this.mRunnableHandler.postRunnable(pRunnable);
    }

    public void clearForces() {
        this.mWorld.clearForces();
    }

    public Body createDynamicBody(final BodyDef pDef) {
        Body body = this.mWorld.createBody(pDef);
        if (pDef.type == BodyDef.BodyType.DynamicBody) {
            dynamicBodies.add(body);
        }
        return body;
    }


    public Joint createJoint(final JointDef pDef) {
        return this.mWorld.createJoint(pDef);
    }

    public void destroyBody(final Body pBody) {
        if (pBody.getType() == BodyDef.BodyType.DynamicBody) {
            dynamicBodies.remove(pBody);
        }
        this.mWorld.destroyBody(pBody);
    }

    public void destroyJoint(final Joint pJoint) {
        this.mWorld.destroyJoint(pJoint);
    }

    public void dispose() {
        locked = true;
        this.mWorld.dispose();
    }

    public boolean getAutoClearForces() {
        return this.mWorld.getAutoClearForces();
    }

//	public Iterator<Body> getBodies() {
//		return this.mWorld.getBodies();
//	}

    public int getBodyCount() {
        return this.mWorld.getBodyCount();
    }

    public int getContactCount() {
        return this.mWorld.getContactCount();
    }

//	public List<Contact> getContactList() {
//		return this.mWorld.getContactList();
//	}

    public Vector2 getGravity() {
        return this.mWorld.getGravity();
    }

    public Iterator<Joint> getJoints() {
        Array<Joint> joints = new Array<Joint>();
        this.mWorld.getJoints(joints);
        return joints.iterator();
    }

    public int getJointCount() {
        return this.mWorld.getJointCount();
    }

    public int getProxyCount() {
        return this.mWorld.getProxyCount();
    }

    public boolean isLocked() {
        return this.mWorld.isLocked() || locked;
    }

    public void QueryAABB(final QueryCallback pCallback, final float pLowerX, final float pLowerY, final float pUpperX, final float pUpperY) {
        this.mWorld.QueryAABB(pCallback, pLowerX, pLowerY, pUpperX, pUpperY);
    }

    public void setAutoClearForces(final boolean pFlag) {
        this.mWorld.setAutoClearForces(pFlag);
    }

    public void setContactFilter(final ContactFilter pFilter) {
        this.mWorld.setContactFilter(pFilter);
    }

    public void setContactListener(final ContactListener pListener) {
        this.mWorld.setContactListener(pListener);
    }

    public void setContinuousPhysics(final boolean pFlag) {
        this.mWorld.setContinuousPhysics(pFlag);
    }

    public void setDestructionListener(final DestructionListener pListener) {
        this.mWorld.setDestructionListener(pListener);
    }

    public void setGravity(final Vector2 pGravity) {
        this.mWorld.setGravity(pGravity);
    }

    public void setWarmStarting(final boolean pFlag) {
        this.mWorld.setWarmStarting(pFlag);
    }

    public void rayCast(final RayCastCallback pRayCastCallback, final Vector2 pPoint1, final Vector2 pPoint2) {
        this.mWorld.rayCast(pRayCastCallback, pPoint1, pPoint2);
    }

    public World getWorld() {
        return mWorld;
    }

    /**
     * @param bodies an Array in which to place all bodies currently in the simulation
     */
    public void getBodies(Array<Body> bodies) {
        mWorld.getBodies(bodies);
    }

    public void safeDestroy(Body body) {
        destroyedBodies.add(body);
    }

    public float getTime() {
        return time;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
