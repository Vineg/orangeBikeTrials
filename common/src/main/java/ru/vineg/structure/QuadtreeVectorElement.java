package ru.vineg.structure;

import ru.vineg.orangeBikeFree.IVector2;

/**
 * Created by vineg on 01.11.2014.
 */
public class QuadtreeVectorElement implements QuadtreeElement {
    public final IVector2 data;

    public QuadtreeVectorElement(IVector2 vector) {
        this.data=vector;
    }

    @Override
    public int getX() {
        return (int) data.getPosition().x;
    }

    @Override
    public int getY() {
        return (int) data.getPosition().y;
    }
}
