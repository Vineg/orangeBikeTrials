package ru.vineg.structure;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 25.01.14
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public class PolygonRegionElement implements QuadtreeElement {
    public final PolygonRegion region;

    public PolygonRegionElement(PolygonRegion region) {
        this.region = region;
    }

    @Override
    public int getX() {
        float[] regionVertices = region.getVertices();
        return (int) regionVertices[0];
    }

    @Override
    public int getY() {
        float[] regionVertices = region.getVertices();
        return (int) regionVertices[1];
    }

    @Override
    public String toString() {
        return "x: "+getX()+", y: "+getY();
    }
}
