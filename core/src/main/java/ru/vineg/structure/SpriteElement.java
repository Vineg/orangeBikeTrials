package ru.vineg.structure;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 25.01.14
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public class SpriteElement implements QuadtreeElement {
    public final Sprite graphics;
    public SpriteElement(Sprite graphics) {
        this.graphics=graphics;
    }

    @Override
    public int getX() {
        return (int) graphics.getX();
    }

    @Override
    public int getY() {
       return (int) graphics.getY();
    }

    @Override
    public boolean equals(Object obj) {
        return graphics==((SpriteElement)obj).graphics;
    }
}
