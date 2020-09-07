package ru.vineg.map;

import ru.vineg.structure.layers.IObject2d;
import ru.vineg.structure.layers.LayerObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vineg on 10.01.2015.
 */
public class VertexListObject extends LayerObject {
    private final List<IVertex> list;
    private final List<VertexObject> wrappers=new ArrayList<VertexObject>();

    public VertexListObject(List<IVertex> list) {
        this.list=list;
    }

    @Override
    public IObject2d getChild(int i) {
        return wrappers.get(i).position==list.get(i)?wrappers.get(i):updateWrapper(i);
    }

    private IObject2d updateWrapper(int i) {
        wrappers.set(i,new VertexObject(list.get(i)));
        return wrappers.get(i);
    }
}
