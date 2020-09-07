package ru.vineg.structure.graphs;

/**
 * Created by vineg on 07.02.2015.
 */
public interface INode<T extends INode> {
    public int getChildrenCount();
    T getChild(int i);

    float dist(T b);
}
