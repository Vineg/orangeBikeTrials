package ru.vineg.map;

import com.badlogic.gdx.math.Vector2;
import ru.vineg.structure.layers.Object2d;

/**
 * Created by vineg on 10.01.2015.
 */
public class VertexObject extends Object2d {
    final IVertex position;

    public VertexObject(IVertex position){
        this.position=position;
    }

    @Override
    public float getX() {
        return position.getPosition().x;
    }

    @Override
    public float getY() {
        return position.getPosition().y;
    }

    @Override
    public void setPosition(Vector2 vec) {
        position.setPosition(vec);
    }
}
