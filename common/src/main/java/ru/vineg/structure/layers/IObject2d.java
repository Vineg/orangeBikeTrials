package ru.vineg.structure.layers;


import ru.vineg.map.IVertex;

/**
 * Created with IntelliJ IDEA.
 * User: vineg
 * Date: 10/3/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IObject2d extends IVertex {
    public float getWidth();
    public float getHeight();

    public void setSize(float width,float height);

    public void setPosition(float x, float y);

    /**
     *
     * @return origin point position
     */
    public float getX();
    public float getY();



    public void setRotation(float radians);


    /**
     *
     * @return angle in radians
     */
    public float getRotation();


    public float getOriginX();
    public float getOriginY();

    public void changeOriginToCenter();

    public void changeOrigin(float x, float y);


    public void setScale(float v);

    public abstract void setScale(float scaleX, float scaleY);

    public float getScaleX();

    public float getScaleY();
}
