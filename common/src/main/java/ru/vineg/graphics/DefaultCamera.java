package ru.vineg.graphics;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import ru.vineg.geom.Rectangle;
import ru.vineg.structure.RectangleI;


/**
 * Created with IntelliJ IDEA.
 * User: vineg
 * Date: 10/6/13
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCamera extends OrthographicCamera implements ICamera2d {

    private RectangleI bounds=new RectangleI();
    private Vector2 startPosition=Vector2.Zero.cpy();
    private float top;
    private float bottom;
    private float left;
    private float right;


    public DefaultCamera(){
        super(Graphics.getScreenWidth(),Graphics.getScreenHeight());
    }


    public float getZoomFactor() {
        return super.zoom;
    }

    /*
        less is bigger
     */
    public void setZoomFactor(float v) {
        super.zoom = v ;
    }

    @Override
    public void update() {
        top=getY()+getHeight()/2;
        bottom=getY()-getHeight()/2;
        left=getX()-getWidth()/2;
        right=getX()+getWidth()/2;
        super.update();
    }

    public void copyBounds(RectangleI bounds, int offset) {
        bounds.setBounds((int) (getX() - getWidth() / 2 - offset), (int) (getY() - getHeight() / 2 - offset), (int) getWidth() + 2 * offset, (int) getHeight() + 2 * offset);
    }

    public float getViewportWidth() {
        return (super.viewportWidth*getZoomFactor());
    }

    public float getViewportHeight() {
        return (super.viewportHeight*getZoomFactor());
    }


    public void setPosition(float x, float y) {
        super.position.set(x, y, 0);
    }


    public void setRotation(float radians) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public float getRotation() {
        return getRotation();  //To change body of implemented methods use File | Settings | File Templates.
    }


    public float getOriginX() {
        return getWidth()/2;
    }

    public float getOriginY() {
        return getWidth()/2;
    }

    public void changeOriginToCenter() {
        changeOrigin(getWidth()/2,getHeight()/2);
    }

    public void changeOrigin(float x, float y) {
        try {
            throw new NoSuchMethodException();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void setScale(float v) {
        setZoomFactor(v);
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        try {
            throw new NoSuchMethodException();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public float getScale() {
        return getZoomFactor();
    }

    public float getScaleX() {
        return getZoomFactor();
    }

    public float getScaleY() {
        return getZoomFactor();
    }

    public void setObjectOrigin(float x, float y) {
        try {
            throw new NoSuchMethodException();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public float getHeight() {
        return getViewportHeight();
    }

    @Override
    public void setSize(float width, float height) {

    }


    public float getWidth() {
        return getViewportWidth();
    }


    public float getX() {
        return super.position.x;
    }


    public float getY() {
        return super.position.y;
    }

    private Vector2 position=new Vector2();
    public Vector2 getPosition() {
        return position.set(super.position.x,super.position.y);
    }

    public void setPosition(Vector2 vec) {
        super.position.set(vec.x,vec.y,0);
    }


    public boolean isInCamera(SimpleSprite sprite) {
        RectangleI cameraBounds = getBounds();
        com.badlogic.gdx.math.Rectangle spriteBounds = sprite.getBoundingRectangle();
        return cameraBounds.intersects(spriteBounds);
    }

    private RectangleI getBounds() {
        bounds.setBounds((int)getLeft(),(int)getBottom(),(int)getWidth(),(int)getHeight());
        return bounds;
    }

    public float getTop() {
        return top;
    }

    public float getRight(){
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public float getLeft() {
        return left;
    }
}
