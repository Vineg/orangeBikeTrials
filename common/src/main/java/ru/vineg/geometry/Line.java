package ru.vineg.geometry;

import com.badlogic.gdx.math.Vector2;
import ru.vineg.structure.QuadtreeElement;

/**
 * Created with IntelliJ IDEA.
 * User: vineg
 * Date: 9/26/13
 * Time: 9:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class Line implements QuadtreeElement {
    public final Vector2 p1=new Vector2(),p2=new Vector2();
    protected final Vector2 center=new Vector2();

    public Line(Vector2 p1, Vector2 p2){
        this.set(p1,p2);
    }

    Vector2 sin=new Vector2();
    public float sin(){
        return sin.set(p2).mulAdd(p1,-1).nor().y;
    }
    public float cos() {
        return sin.set(p2).mulAdd(p1,-1).nor().x;
    }

    public Line(){}

    public Line set(Vector2 p1, Vector2 p2) {
        this.p1.set(p1);
        this.p2.set(p2);
        return this;
    }


    public float len(){
        return p1.dst(p2);
    }

    public float getAngleDeg(){
        return dir.set(p2).mulAdd(p1,-1).angle();
    }

    public float getAngleRad(){
        this.directionVec();
        return dir.angleRad();
    }

    public Vector2 getCenter(){
        return center.set(p1).add(p2).scl(1/2f);
    }


    public Line rotate(float degrees){
        Vector2 center = getCenter();
        p1.mulAdd(center,-1).rotate(degrees).add(center);
        p2.mulAdd(center,-1).rotate(degrees).add(center);
        return this;
    }


    private final Vector2 dir=new Vector2();
    public Vector2 directionVec(){
        return dir.set(p2).mulAdd(p1,-1).nor();
    }

    @Override
    public int getX() {
        return (int) p1.x;
    }

    @Override
    public int getY() {
        return (int) p1.y;
    }

    public Line set(Line line) {
        set(line.p1,line.p2);
        return this;
    }


    private static Vector2 point =new Vector2();
    private static Line tmp=new Line();
    public static Vector2 intersection(Line line1, Line line2) {
        float x1 = line2.p1.x;
        float x2 = line2.p2.x;
        float y1 = line2.p1.y;
        float y2 = line2.p2.y;

        float x3=line1.p1.x;
        float x4=line1.p2.x;
        float y3=line1.p1.y;
        float y4=line1.p2.y;

        float d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
//        if (d == 0) return intersection(tmp.set(line1).rotate(0.1f),line2);
        if (d == 0) return null;

        float xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
        float yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;

        point.set(xi,yi);
        if(xi>(Math.max(x1, x2)+FloatUtils.eps)||xi>(Math.max(x3, x4)+FloatUtils.eps)){return null;}
        if(xi<(Math.min(x1, x2)-FloatUtils.eps)||xi<(Math.min(x3, x4)-FloatUtils.eps)){return null;}
        if(yi>(Math.max(y1, y2)+FloatUtils.eps)||yi>(Math.max(y3, y4)+FloatUtils.eps)){return null;}
        if(yi<(Math.min(y1, y2)-FloatUtils.eps)||yi<(Math.min(y3, y4)-FloatUtils.eps)){return null;}
        return point;
    }

    Vector2 tmp1=new Vector2();
    Vector2 tmp2=new Vector2();
    public float crs(Line line2) {
        return tmp1.set(line2.p2).mulAdd(line2.p1,-1).crs(tmp2.set(p2).mulAdd(p1,-1));
    }

    public float dot(Vector2 vector2) {
        return tmp1.set(p2).mulAdd(p1, -1).dot(tmp2.set(vector2).mulAdd(p1, -1));
    }

    public float crs(Vector2 vector2) {
        return tmp1.set(p2).mulAdd(p1, -1).crs(tmp2.set(vector2).mulAdd(p1, -1));
    }


    public float dst(Vector2 vector2) {
        return tmp1.set(p2).mulAdd(p1, -1).nor().crs(tmp2.set(vector2).mulAdd(p1, -1));
    }

    private Vector2 vec=new Vector2();
    public Vector2 vec() {
        return vec.set(p2).mulAdd(p1,-1);
    }

    public boolean contains(Vector2 point) {
        return (point.x<Math.max(p1.x,p2.x)+FloatUtils.eps&&point.x>Math.min(p1.x,p2.x)-FloatUtils.eps)||
                (point.y<Math.max(p1.y,p2.y)+FloatUtils.eps&&point.y>Math.min(p1.y,p2.y)-FloatUtils.eps);
    }

    @Override
    public String toString() {
        return p1.toString()+"|"+p2.toString();
    }
}
