package ru.vineg.geometry.orangeBikeFree;

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
    private final Vector2 dir=new Vector2();
    private float len;


    public Line(){}

    public Line(Vector2 p1, Vector2 p2){
        set(p1,p2);
    }

    public Line set(Vector2 p1, Vector2 p2) {
        this.p1.set(p1);
        this.p2.set(p2);
        len = p1.dst(p2);
        update();
        return this;
    }

    protected void update() {
        dir.set(p2).mulAdd(p1,-1).nor();
        center.set(p1).add(p2).scl(1/2f);
    }

    public float len(){
        return len;
    }

    public float getAngleDeg(){
        return dir.angle();
    }

    public float getAngleRad(){
        return dir.angleRad();
    }

    public Vector2 getCenter(){
        return center;
    }

    public float sin(){
        return dir.y;
    }

    public float cos(){
        return dir.x;
    }

    public Vector2 directionVec(){
        return dir;
    }

    @Override
    public int getX() {
        return (int) p1.x;
    }

    @Override
    public int getY() {
        return (int) p1.y;
    }

    public void set(Line line) {
        set(line.p1,line.p2);
    }


    private Vector2 point =new Vector2();
    public Vector2 intersection(Line line) {
        float x3=line.p1.x;
        float x4=line.p2.x;
        float y3=line.p1.y;
        float y4=line.p2.y;
        float d = (p1.x-p2.x)*(y3-y4) - (p1.y-p2.y)*(x3-x4);
        if (d == 0){
            rotate(0.0001f);
            return intersection(line);
        };

        float xi = ((x3-x4)*(p1.x*p2.y-p1.y*p2.x)-(p1.x-p2.x)*(x3*y4-y3*x4))/d;
        float yi = ((y3-y4)*(p1.x*p2.y-p1.y*p2.x)-(p1.y-p2.y)*(x3*y4-y3*x4))/d;


        if(xi>Math.max(p1.x,p2.x)||xi>Math.max(x3,x4)){return null;};
        if(xi<Math.min(p1.x, p2.x)||xi<Math.min(x3, x4)){return null;};
        if(yi>Math.max(p1.y,p2.y)||yi>Math.max(y3,y4)){return null;};
        if(yi<Math.min(p1.y, p2.y)||yi<Math.min(y3, y4)){return null;};
        point.set(xi,yi);
        return point;
    }

    Vector2 tmp=new Vector2();
    private void rotate(float v) {
        tmp.set(center);
        p1.add(tmp.scl(-1));
        p2.add(tmp);
        p1.rotateRad(v);
        p2.rotateRad(v);
        p1.add(tmp.scl(-1));
        p2.add(tmp);
    }
}
