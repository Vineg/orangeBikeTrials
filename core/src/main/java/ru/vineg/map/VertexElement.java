package ru.vineg.map;

import ru.vineg.structure.QuadtreeElement;

/**
 * Created by Vineg on 01.05.2014.
 */
public class VertexElement implements QuadtreeElement {
    public final IVertex vertex;

    public VertexElement(IVertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public int getX() {
        return (int) vertex.getPosition().x;
    }

    @Override
    public int getY() {
        return (int) vertex.getPosition().y;
    }
}
