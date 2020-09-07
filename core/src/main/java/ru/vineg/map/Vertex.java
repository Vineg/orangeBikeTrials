package ru.vineg.map;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Vineg on 20.02.14.
 */
public class Vertex implements IVertex {
    public Vertex(float x, float y) {
        position = new Vector2(x, y);
    }

    public Vertex() {
        this(0, 0);
    }

    private Vector2 position;
    public Vertex(Vector2 position) {
        this.position=position;
    }

    public Vertex(float[] vec) {
        position=new Vector2(vec[0],vec[1]);
    }

    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector2 vec) {
        this.position=vec;
    }

}
