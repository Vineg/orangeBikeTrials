package box2d;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 07.01.14
 * Time: 2:43
 * To change this template use File | Settings | File Templates.
 */
public class MathUtils {
    public static float radToDeg(float rad) {
        return (float)(rad*180/Math.PI);
    }

    public static float range(float i, float min, float max) {
        return Math.max(min,Math.min(max,i));
    }

    public static float degToRad(float deg) {
        return (float) (deg/180*Math.PI);
    }
}
