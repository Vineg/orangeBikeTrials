package ru.vineg.orangeBikeFree.motorbike;

import com.badlogic.gdx.gleed.TextureElement;
import ru.vineg.structure.layers.LayerObject2d;

/**
 * Created by vineg on 03.11.2014.
 */
public class LevelTextureObject extends LayerObject2d {
    public final TextureElement object;

    public LevelTextureObject(TextureElement object) {
        this.object=object;
        this.setStartPosition(object.getPosition());
    }


    @Override
    public float getWidth() {
        return object.getRegion().getRegionWidth();
    }

    @Override
    public float getHeight() {
        return object.getRegion().getRegionHeight();
    }

    @Override
    protected void setObjectPosition(float x, float y) {
        object.getPosition().set(x,y);
    }

    @Override
    protected void setObjectScale(float scaleX, float scaleY) {
        object.setScaleX(scaleX);
        object.setScaleY(scaleY);
    }

    @Override
    public float getX() {
        return object.getPosition().x;
    }

    @Override
    public float getY() {
        return object.getPosition().y;
    }


    @Override
    public void setRotation(float radians) {
        object.setRotation(radians);
    }

    @Override
    public float getRotation() {
        return object.getRotation();
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
    public void changeOriginToCenter() {
            changeOrigin(getWidth() / 2, getHeight() / 2);
    }

    @Override
    public void setObjectOrigin(float x, float y) {
        object.setOriginX(x);
        object.setOriginY(y);
    }

    @Override
    public void dispose() {
    }


    @Override
    protected void setObjectScale(float v) {
        setObjectScale(v,v);
    }

    @Override
    public float getScaleX() {
        return object.getScaleX();
    }

    @Override
    public float getScaleY() {
        return object.getScaleY();
    }



}
