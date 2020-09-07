package ru.vineg.graphics;

import ru.vineg.structure.layers.IObject2d;
import ru.vineg.structure.RectangleI;

/**
 * Created with IntelliJ IDEA.
 * User: vineg
 * Date: 10/6/13
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICamera2d extends IObject2d{
    public float getZoomFactor();

    public void setZoomFactor(float v);

    public void update();

    public void copyBounds(RectangleI bounds, int offset);

}
