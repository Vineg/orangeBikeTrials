package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by Vineg on 22.02.14.
 */
public class SimpleImage extends Image {
    public SimpleImage(TextureRegion region) {
        super(region);
        init();
    }

    public SimpleImage(Texture mapVertex) {
        super(mapVertex);
        init();
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x - getOriginX(), y - getOriginY());
    }

    void init() {
        setOriginCenter();
    }

    public void setOriginCenter() {
        setOrigin(getWidth() / 2, getHeight() / 2);
    }

//    @Override
//    public void setWidth(float width) {
//        if (getWidth() > 0) {
//            setOriginX(getOriginX() * width / getWidth());
//        }
//        super.setWidth(width);
//    }
//
//
//    @Override
//    public void setHeight(float height) {
//        if (getHeight() > 0) {
//            setOriginX(getOriginY() * height / getHeight());
//        }
//        super.setWidth(height);
//    }
    //    @Override
//     public void setScaleX(float scaleX) {
//        setOriginX(getOriginX()*scaleX/getScaleX());
//        super.setScaleX(scaleX);
//    }
//
//    @Override
//    public void setScaleY(float scaleY) {
//        setOriginX(getOriginY()*scaleY/getScaleY());
//        super.setScaleY(scaleY);
//    }
//
}
