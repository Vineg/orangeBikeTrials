package ru.vineg.structure;

/**
 * Created by Vineg on 01.05.2014.
 */
public interface IQuadTreeBranch {
    boolean contains(QuadtreeElement elem);

    int getHeight();

    int getWidth();

    int getLeft();

    int getRight();

    int getBottom();

    int getTop();

    boolean insert(QuadtreeElement elem);

    Vector2i getSize();

    Vector2i getPosition();

    void query(RectangleI rect, QuadtreeCallback callback);
}
