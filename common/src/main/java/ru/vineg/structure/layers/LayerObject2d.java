package ru.vineg.structure.layers;

import com.badlogic.gdx.math.Vector2;
import ru.vineg.geometry.Line;

import java.util.ArrayList;

/**
 * Created by vineg on 03.11.2014.
 */
public abstract class LayerObject2d extends Object2d {
    private ArrayList<IObject2d> children = new ArrayList<>(10);
    private LayerObject2d parent;
    //startPosition related to parent
    private ArrayList<Vector2> points=new ArrayList<>();
    private Vector2 startPosition=new Vector2();
    private Vector2 scale=new Vector2(1,1);
    public boolean updateChildren=true;
    private float originRotation;


    public LayerObject2d(){
    }


    public IObject2d getChild(int i) {
        return children.get(i);
    }

    public int getTotalChildren(){
        return children.size();
    }


    public IObject2d getParent() {
        return parent;
    }


    public Vector2 getStartPosition() {
        return startPosition;
    }



    public LayerObject2d changeParent(LayerObject2d newParent) {
        return changeParent(newParent,false);
    }

    public LayerObject2d changeParent(LayerObject2d newParent,boolean updateStartPosition) {
        if (parent != null) {
            parent.removeChild(this);
        }
        if(updateStartPosition) {
            newParent.globalToLocal(getStartPosition().set(getX(), getY()));
        }
        this.parent = newParent;
        newParent.addChild(this);
        return this;
    }

    public void removeChild(LayerObject2d layerObject2d) {
        children.remove(layerObject2d);
    }

    public void addChild(IObject2d child) {
        children.add(child);
    }


    public Vector2 globalToLocal(Vector2 vec) {
        float a = getRotation();
        return vec.mulAdd(getPosition(),-1).rotateRad(-a).scl(1 / getScaleX(), 1 / getScaleY()).add(getOriginX(), getOriginY());
    }

    public Vector2 globalToLocalOrig(Vector2 vec) {
        float a = getRotation();
        return vec.add(-getX(), -getY()).rotateRad(-a).scl(1 / getScaleX(), 1 / getScaleY());
    }


    /**
     *
     * @param vec
     * @return vec set to global relative to corner
     */
    public Vector2 localToGlobal(Vector2 vec) {
        float a = getRotation();
        return vec.add(-getOriginX(),-getOriginY()).scl(getScaleX(), getScaleY()).rotateRad(a).add(getX(), getY());
    }

    /**
     *
     * @param vec
     * @return vec set to global relative to origin
     */
    public Vector2 localOrigToGlobal(Vector2 vec) {
        float a = getRotation();
        return vec.scl(getScaleX(), getScaleY()).rotateRad(a).add(getX(), getY());
    }





    @Override
    public final void setScale(float v) {
        setScale(v, v);
    }

    protected void setObjectScale(float v){};


    @Override
    public final void changeOrigin(float x, float y) {
        setObjectOrigin(x, y);
    }


    @Override
    public void setPosition(Vector2 position) {
        setPosition(position.x,position.y,updateChildren);
    }

    public void setPosition(float x, float y, boolean updateChildren) {
        if(updateChildren){moveChildren(x - getX(), y - getY());}
        setObjectPosition(x, y);
    }

    @Override
    public void setRotation(float radians) {
        if(updateChildren){rotateChildren(radians - getRotation());}
        setObjectRotation(radians-originRotation);
    }

    protected void setObjectRotation(float radians){};

    private void rotateChildren(float rad) {
        for (IObject2d child : children) {
            child.setRotation(child.getRotation()+rad);
        }
    }

    protected void setObjectPosition(float x, float y){};

    private void moveChildren(float x, float y) {
        for (IObject2d child : children) {
            child.setPosition(tmp.set(child.getPosition()).add(x,y));
        }
    }

    public void setScale(float scaleX, float scaleY) {
        scaleChildren(scaleX / getScaleX(), scaleY / getScaleY());
        scale.set(scaleX,scaleY);
        setObjectScale(scaleX, scaleY);
    }

    protected void setObjectScale(float scaleX, float scaleY){};

    private void scaleChildren(float scaleX, float scaleY) {
        for (IObject2d child : children) {
            child.setScale(child.getScaleX() * scaleX, child.getScaleY() * scaleY);
//            child.changeOrigin(child.getOriginX()*scaleX,child.getOriginY()*scaleY);

            Vector2 position = child.getPosition();
            localOrigToGlobal(globalToLocalOrig(position).scl(scaleX, scaleY));
            child.setPosition(position);
        }
    }

    Vector2 position = new Vector2();


    public void reset() {
        parent.localToGlobal(position.set(getStartPosition()));
        setPosition(position);
        setRotation(parent.getRotation());
    }

    public Vector2 getPhysicsStartPosition(Vector2 tmp) {
        return parent.localToGlobal(tmp.set(getStartPosition()));
    }

    @Override
    public void changeOriginToCenter() {
            changeOrigin(getWidth() / 2, getHeight() / 2);
    }

    public void addPoint(Vector2 point) {
        points.add(point);
    }

    public void addGlobalPoint(Vector2 point){
        addPoint(globalToLocal(point));
    }

    public Vector2 getGlobalPoint(Vector2 tmp,int index){
        return localToGlobal(tmp.set(points.get(index)));
    }


    Line tmpLine=new Line();
    Vector2 tmp=new Vector2(),tmp1=new Vector2();
    public void transform(Line initialLine, Line finalLine) {
        changeOrigin(initialLine.p1.x, initialLine.p1.y);
        if(scale.x<0){
            tmp.x=initialLine.p1.x;
            tmp.y=getHeight()-initialLine.p1.y;
            tmp1.x=initialLine.p2.x;
            tmp1.y=getHeight()-initialLine.p2.y;
            initialLine=tmpLine.set(tmp,tmp1);
        }
        setObjectScale(finalLine.len()/initialLine.len()*Math.signum(scale.x), scale.y);
        setPosition(finalLine.p1);
        setRotation((float) ((scale.x<0?Math.PI:0)+finalLine.getAngleRad()-/*initialLine.getAngleRad()*/-Math.signum(scale.x)*Math.PI/360));
//        setRotation((float) (finalLine.getAngleRad()));
    }

//    public void transform(Line finalLine) {
//        setObjectScale(finalLine.len()/getWidth()*Math.signum(scale.x), scale.y);
//        setPosition(finalLine.p1);
//        setRotation((float) ((scale.x<0?Math.PI:0)+finalLine.getAngleRad()-initialLine.getAngleRad()-Math.signum(scale.x)*Math.PI/360));
//
//    }


    @Override
    public float getScaleX() { return 1f; }

    @Override
    public float getScaleY() {
        return 1f;
    }


    public void setObjectOrigin(float x, float y){};

    public void setStartPosition(Vector2 startPosition) {
        this.getStartPosition().set(startPosition);
    }

    @Override
    public void setSize(float width, float height) {

    }

    public void setDefaultRotation(float radians){
        originRotation = -radians;
    };

    public void dispose(){};


}
