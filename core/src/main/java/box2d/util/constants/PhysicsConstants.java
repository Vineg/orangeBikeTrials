package box2d.util.constants;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 13:01:50 - 28.07.2010
 */
public interface PhysicsConstants {
	// ===========================================================
	// Final Fields
	// ===========================================================
	
	public static final float PIXEL_TO_METER_RATIO_DEFAULT = 1/32f;
    public static final int METER_TO_PIXEL_RATIO_DEFAULT = 32;

	public static final short CATEGORYBIT_WALL = 1;
	public static final short CATEGORYBIT_VIRTUAL = 4;
	public static final short CATEGORYBIT_OBJECT = 8;

	public static final short MASKBIT_WALL = CATEGORYBIT_OBJECT;
	public static final short MASKBIT_VIRTUAL = 0;
	public static final short MASKBIT_OBJECT = CATEGORYBIT_WALL;
	// ===========================================================
	// Methods
	// ===========================================================
}
