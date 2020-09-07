package box2d;


import box2d.util.constants.PhysicsConstants;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import org.andengine.util.Constants;
import ru.vineg.game.IPhysicsWorld;
import ru.vineg.orangeBikeFree.MotodroidGameWorld;
import ru.vineg.structure.layers.IObject2d;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 13:59:03 - 15.07.2010
 */
public class PhysicsFactory {
    private static final FixtureDef DUMMY_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1f, 0f, 100f, false, MotodroidGameWorld.CATEGORYBIT_VIRTUAL, MotodroidGameWorld.MASKBIT_VIRTUAL, (short) 0);


    // ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public static FixtureDef createFixtureDef(final float pDensity, final float pElasticity, final float pFriction) {
		return PhysicsFactory.createFixtureDef(pDensity, pElasticity, pFriction, false);
	}

	public static FixtureDef createFixtureDef(final float pDensity, final float pElasticity, final float pFriction, final boolean pSensor) {
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = pDensity;
		fixtureDef.restitution = pElasticity;
		fixtureDef.friction = pFriction;
		fixtureDef.isSensor = pSensor;
		return fixtureDef;
	}

	public static FixtureDef createFixtureDef(final float pDensity, final float pElasticity, final float pFriction, final boolean pSensor, final short pCategoryBits, final short pMaskBits, final short pGroupIndex) {
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = pDensity;
		fixtureDef.restitution = pElasticity;
		fixtureDef.friction = pFriction;
		fixtureDef.isSensor = pSensor;
		final Filter filter = fixtureDef.filter;
		filter.categoryBits = pCategoryBits;
		filter.maskBits = pMaskBits;
		filter.groupIndex = pGroupIndex;
		return fixtureDef;
	}
//
//	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final IObject2d pAreaShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
//		return PhysicsFactory.createBoxBody(pPhysicsWorld, pAreaShape, pBodyType, pFixtureDef, PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
//	}

//	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final IObject2d pAreaShape, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
//		final float[] sceneCenterCoordinates = pAreaShape.getSceneCenterCoordinates();
//		final float centerX = sceneCenterCoordinates[Constants.VERTEX_INDEX_X];
//		final float centerY = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y];
//		return PhysicsFactory.createBoxBody(pPhysicsWorld, centerX, centerY, pAreaShape.getWidthScaled(), pAreaShape.getHeightScaled(), pAreaShape.getRotation(), pBodyType, pFixtureDef, pPixelToMeterRatio);
//	}

	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final float pCenterX, final float pCenterY, final float pWidth, final float pHeight, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createBoxBody(pPhysicsWorld, pCenterX, pCenterY, pWidth, pHeight, 0, pBodyType, pFixtureDef, PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
	}

	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final float pCenterX, final float pCenterY, final float pWidth, final float pHeight, final float pRotation, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createBoxBody(pPhysicsWorld, pCenterX, pCenterY, pWidth, pHeight, pRotation, pBodyType, pFixtureDef, PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
	}

	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final float pCenterX, final float pCenterY, final float pWidth, final float pHeight, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		return PhysicsFactory.createBoxBody(pPhysicsWorld, pCenterX, pCenterY, pWidth, pHeight, 0, pBodyType, pFixtureDef, pPixelToMeterRatio);
	}

	public static Body createBoxBody(final PhysicsWorld pPhysicsWorld, final float pCenterX, final float pCenterY, final float pWidth, final float pHeight, final float pRotation, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = pBodyType;

		boxBodyDef.position.x = pCenterX;
		boxBodyDef.position.y = pCenterY;

		final Body boxBody = pPhysicsWorld.createDynamicBody(boxBodyDef);

		final PolygonShape boxPoly = new PolygonShape();

		final float halfWidth = pWidth * 0.5f;
		final float halfHeight = pHeight * 0.5f;

		boxPoly.setAsBox(halfWidth, halfHeight);
		pFixtureDef.shape = boxPoly;

		boxBody.createFixture(pFixtureDef);

		boxPoly.dispose();

		boxBody.setTransform(boxBody.getWorldCenter(), MathUtils.degToRad(pRotation));

		return boxBody;
	}

	public static Body createCircleBody(final IPhysicsWorld pPhysicsWorld, final IObject2d pAreaShape, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return PhysicsFactory.createCircleBody(pPhysicsWorld, pAreaShape, pBodyType, pFixtureDef, 1);
	}

	public static Body createCircleBody(final IPhysicsWorld pPhysicsWorld, final IObject2d pAreaShape, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
        final float[] sceneCenterCoordinates = new float[]{pAreaShape.getX(),pAreaShape.getY()};
		final float centerX = sceneCenterCoordinates[Constants.VERTEX_INDEX_X];
		final float centerY = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y];
		return PhysicsFactory.createCircleBody(pPhysicsWorld, centerX, centerY, pAreaShape.getWidth() * 0.5f, pAreaShape.getRotation(), pBodyType, pFixtureDef, pPixelToMeterRatio);
	}

	public static Body createCircleBody(final IPhysicsWorld pPhysicsWorld, final float pCenterX, final float pCenterY, final float pRadius, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return createCircleBody(pPhysicsWorld, pCenterX, pCenterY, pRadius, 0, pBodyType, pFixtureDef, PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
	}

	public static Body createCircleBody(final IPhysicsWorld pPhysicsWorld, final float pCenterX, final float pCenterY, final float pRadius, final float pRotation, final BodyType pBodyType, final FixtureDef pFixtureDef) {
		return createCircleBody(pPhysicsWorld, pCenterX, pCenterY, pRadius, pRotation, pBodyType, pFixtureDef, PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
	}

	public static Body createCircleBody(final IPhysicsWorld pPhysicsWorld, final float pCenterX, final float pCenterY, final float pRadius, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		return createCircleBody(pPhysicsWorld, pCenterX, pCenterY, pRadius, 0, pBodyType, pFixtureDef, pPixelToMeterRatio);
	}

	public static Body createCircleBody(final IPhysicsWorld pPhysicsWorld, final float pCenterX, final float pCenterY, final float pRadius, final float pRotation, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
		final BodyDef circleBodyDef = new BodyDef();
		circleBodyDef.type = pBodyType;

		circleBodyDef.position.x = pCenterX*pPixelToMeterRatio;
		circleBodyDef.position.y = pCenterY*pPixelToMeterRatio;

		circleBodyDef.angle = pRotation;

		final Body circleBody = pPhysicsWorld.createDynamicBody(circleBodyDef);

		final CircleShape circlePoly = new CircleShape();
		pFixtureDef.shape = circlePoly;

		final float radius = pRadius*pPixelToMeterRatio;
		circlePoly.setRadius(radius);

		circleBody.createFixture(pFixtureDef);

		circlePoly.dispose();

		return circleBody;
	}


//	/**
//	 * @param pPhysicsWorld
//	 * @param pShape
//	 * @param pVertices are to be defined relative to the center of the pShape and have the {@link box2d.util.constants.PhysicsConstants#PIXEL_TO_METER_RATIO_DEFAULT} applied.
//	 * @param pBodyType
//	 * @param pFixtureDef
//	 * @return
//	 */
//	public static Body createPolygonBody(final PhysicsWorld pPhysicsWorld, final IObject2d pShape, final Vector2i[] pVertices, final BodyType pBodyType, final FixtureDef pFixtureDef) {
//		return PhysicsFactory.createPolygonBody(pPhysicsWorld, pShape, pVertices, pBodyType, pFixtureDef, PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
//	}

//	/**
//	 * @param pPhysicsWorld
//	 * @param pShape
//	 * @param pVertices are to be defined relative to the center of the pShape.
//	 * @param pBodyType
//	 * @param pFixtureDef
//	 * @return
//	 */
//	public static Body createPolygonBody(final PhysicsWorld pPhysicsWorld, final IObject2d pShape, final Vector2i[] pVertices, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
//		final BodyDef boxBodyDef = new BodyDef();
//		boxBodyDef.type = pBodyType;
//
//		final float[] sceneCenterCoordinates = pShape.getSceneCenterCoordinates();
//		boxBodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] * pPixelToMeterRatio;
//		boxBodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] * pPixelToMeterRatio;
//
//		final Body boxBody = pPhysicsWorld.createBody(boxBodyDef);
//
//		final PolygonShape boxPoly = new PolygonShape();
//
//		boxPoly.set(pVertices);
//		pFixtureDef.shape = boxPoly;
//
//		boxBody.createFixture(pFixtureDef);
//
//		boxPoly.dispose();
//
//		return boxBody;
//	}


//	/**
//	 * @param pPhysicsWorld
//	 * @param pShape
//	 * @param pTriangleVertices are to be defined relative to the center of the pShape and have the {@link box2d.util.constants.PhysicsConstants#PIXEL_TO_METER_RATIO_DEFAULT} applied.
//	 * @param pBodyType
//	 * @param pFixtureDef
//	 * @return
//	 */
//	public static Body createTrianglulatedBody(final PhysicsWorld pPhysicsWorld, final IObject2d pShape, final List<Vector2i> pTriangleVertices, final BodyType pBodyType, final FixtureDef pFixtureDef) {
//		return PhysicsFactory.createTrianglulatedBody(pPhysicsWorld, pShape, pTriangleVertices, pBodyType, pFixtureDef, PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
//	}

//	/**
//	 * @param pPhysicsWorld
//	 * @param pShape
//	 * @param pTriangleVertices are to be defined relative to the center of the pShape and have the {@link box2d.util.constants.PhysicsConstants#PIXEL_TO_METER_RATIO_DEFAULT} applied.
//	 * 					The vertices will be triangulated and for each triangle a {@link com.badlogic.gdx.physics.box2d.Fixture} will be created.
//	 * @param pBodyType
//	 * @param pFixtureDef
//	 * @return
//	 */
//	public static Body createTrianglulatedBody(final PhysicsWorld pPhysicsWorld, final IObject2d pShape, final List<Vector2i> pTriangleVertices, final BodyType pBodyType, final FixtureDef pFixtureDef, final float pPixelToMeterRatio) {
//		final Vector2i[] TMP_TRIANGLE = new Vector2i[3];
//
//		final BodyDef boxBodyDef = new BodyDef();
//		boxBodyDef.type = pBodyType;
//
//		final float[] sceneCenterCoordinates = pShape.getSceneCenterCoordinates();
//		boxBodyDef.position.x = sceneCenterCoordinates[Constants.VERTEX_INDEX_X] * pPixelToMeterRatio;
//		boxBodyDef.position.y = sceneCenterCoordinates[Constants.VERTEX_INDEX_Y] * pPixelToMeterRatio;
//
//		final Body boxBody = pPhysicsWorld.createBody(boxBodyDef);
//
//		final int vertexCount = pTriangleVertices.size();
//		for(int i = 0; i < vertexCount; /* */) {
//			final PolygonShape boxPoly = new PolygonShape();
//
//			TMP_TRIANGLE[2] = pTriangleVertices.get(i++);
//			TMP_TRIANGLE[1] = pTriangleVertices.get(i++);
//			TMP_TRIANGLE[0] = pTriangleVertices.get(i++);
//
//			boxPoly.set(TMP_TRIANGLE);
//			pFixtureDef.shape = boxPoly;
//
//			boxBody.createFixture(pFixtureDef);
//
//			boxPoly.dispose();
//		}
//
//		return boxBody;
//	}

    public static Body createDummyBody(PhysicsWorld physicsWorld,float x, float y) {
        DUMMY_FIXTURE_DEF.isSensor=true;
        return createBoxBody(physicsWorld, x, y, 0.0001f, 0.0001f, BodyDef.BodyType.StaticBody, DUMMY_FIXTURE_DEF);
    }

    // ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
