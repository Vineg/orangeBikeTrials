package ru.vineg.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 31.01.14
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
public class SimpleSprite extends Sprite{
    public SimpleSprite(Texture texture) {
        super(texture);
    }

    public SimpleSprite(TextureRegion mapEdge) {
        super(mapEdge);
    }

    public void setCenter(float x,float y){
        super.setPosition(x-getWidth()/2f,y-getHeight()/2f);
    }

}
