package ru.vineg.map;

import ru.vineg.structure.layers.IObject2d;
import ru.vineg.structure.layers.LayerObject;
import ru.vineg.structure.layers.LayerObject2d;

import java.util.List;

/**
 * Created by vineg on 10.01.2015.
 */
public class ListObject extends LayerObject {
    private final List<? extends IObject2d> children;

    public ListObject(List<? extends IObject2d> list) {
        this.children=list;
    }

    @Override
    public IObject2d getChild(int i) {
        return children.get(i);
    }

    @Override
    public void addChild(IObject2d child) {
        try {
            throw new NoSuchMethodException();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeChild(LayerObject2d layerObject2d) {
        try {
            throw new NoSuchMethodException();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
