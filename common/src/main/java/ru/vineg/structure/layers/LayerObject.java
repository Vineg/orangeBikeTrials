package ru.vineg.structure.layers;


import com.badlogic.gdx.math.Vector2;

/**
 * Created by vineg on 03.11.2014.
 */
public class LayerObject extends LayerObject2d {
    Vector2 position=new Vector2();
    Vector2 origin = new Vector2();
    private Vector2 scale=new Vector2(1f,1f);
    private float rotation;
    private float width=0;
    private float height=0;

    @Override
    protected void setObjectScale(float v) {
        scale.set(v,v);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public void setSize(float width,float height){
        this.width=width;
        this.height=height;
    }

    @Override
    public void dispose() {
    };


    @Override
    protected void setObjectPosition(float x, float y) {
        position.set(x,y);
    }

    @Override
    protected void setObjectScale(float scaleX, float scaleY) {
        scale.x=scaleX;
        scale.y=scaleY;
    }

    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public void setRotation(float radians) {
        rotation=radians;
        super.setRotation(radians);
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    @Override
    public float getOriginX() {
        return origin.x;
    }

    @Override
    public float getOriginY() {
        return origin.y;
    }


    public void setObjectOrigin(float x, float y){
        origin.set(x,y);
    }


    @Override
    public float getScaleX() {
        return scale.x;
    }

    @Override
    public float getScaleY() {
        return scale.y;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    protected void setObjectRotation(float radians) {
        this.rotation=radians;
    }
}
