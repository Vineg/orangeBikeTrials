package ru.vineg.geom;

import com.badlogic.gdx.math.Vector2;

/**
 * Created with floatelliJ IDEA.
 * User: Vineg
 * Date: 25.01.14
 * Time: 23:51
 * To change this template use File | Settings | File Templates.
 */
public class Rectangle extends com.badlogic.gdx.math.Rectangle{
    public float x;
    public float y;


    public float width;
    public float height;

    public Rectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle() {
    }

    public boolean contains(float x, float y) {
        return (x >= this.x && x < this.x + width) && (y >= this.y && y < this.y + height);
    }

    public boolean floatersects(Rectangle r) {
            return !(getLeft() > r.getRight() ||
                    getRight() < r.getLeft() ||
                    getTop() < r.getBottom() ||
                    getBottom() > r.getTop());
    }

    public void setBounds(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void scl(float k) {
        x *= k;
        y *= k;
        width *= k;
        height *= k;
    }

    public float getLeft() {
        return x;
    }

    public float getRight() {
        return x + width;
    }

    public float getBottom() {
        return y;
    }

    public float getTop() {
        return y + height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public boolean contains(Vector2 pofloat){
        return contains(pofloat.x,pofloat.y);
    }

    public Vector2 getSize(){
        return new Vector2(width,height);
    }

}
