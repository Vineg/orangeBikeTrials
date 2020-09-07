package ru.vineg.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.vineg.orangeBikeFree.AnimatedColorObject;

/**
 * Created with IntelliJ IDEA.
 * User: vineg
 * Date: 10/4/13
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageObject2dWrapper extends AnimatedColorObject {
    public final Sprite object;
    public float rotation;
    Vector2 position = new Vector2();

    public ImageObject2dWrapper(TextureRegion texture) {
        this(new Sprite(texture));
    }


    public ImageObject2dWrapper(Sprite sprite) {
        object = sprite;
    }

    @Override
    public float getWidth() {
        return object.getWidth();
    }

    @Override
    public float getHeight() {
        return object.getHeight();
    }

    @Override
    public void setSize(float width, float height) {
        object.setSize(width, height);
    }

    @Override
    protected void setObjectPosition(float x, float y) {
        object.setPosition(x - getOriginX(), y - getOriginY());
    }

    @Override
    public Vector2 getPosition() {
        return position.set(getX(), getY());
    }

    @Override
    protected void setObjectScale(float scaleX, float scaleY) {
        object.setScale(scaleX, scaleY);
    }


    @Override
    public float getX() {
        return object.getX() + getOriginX();
    }

    @Override
    public float getY() {
        return object.getY() + getOriginY();
    }

    @Override
    public float getRotation() {
        return object.getRotation() * MathUtils.degreesToRadians;
    }

    @Override
    protected void setObjectRotation(float radians) {
        object.setRotation(radians * MathUtils.radiansToDegrees);
    }

    @Override
    public float getOriginX() {
        return object.getOriginX();
    }

    @Override
    public float getOriginY() {
        return object.getOriginY();
    }


    @Override
    public void setObjectOrigin(float x, float y) {
        object.setOrigin(x, y);
    }



    @Override
    protected void setObjectScale(float v) {
        object.setScale(v);
    }

    @Override
    public float getScaleX() {
        return object.getScaleX();
    }

    @Override
    public float getScaleY() {
        return object.getScaleY();
    }

    @Override
    public void setColor(Color color) {
        object.setColor(color);
    }


}
