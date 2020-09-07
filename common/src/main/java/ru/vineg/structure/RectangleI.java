package ru.vineg.structure;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 25.01.14
 * Time: 23:51
 * To change this template use File | Settings | File Templates.
 */
public class RectangleI {
    public int x;
    public int y;


    public int width;
    public int height;

    public RectangleI(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public RectangleI() {
    }

    public boolean contains(int x, int y) {
        return (x >= this.x && x < this.x + width) && (y >= this.y && y < this.y + height);
    }

    public boolean intersects(com.badlogic.gdx.math.Rectangle r) {
            return !(getLeft() > r.x+r.width ||
                    getRight() < r.x ||
                    getTop() < r.y ||
                    getBottom() > r.y+r.height);
    }

    public boolean intersects(RectangleI r) {
        return !(getLeft() > r.x+r.width ||
                getRight() < r.x ||
                getTop() < r.y ||
                getBottom() > r.y+r.height);
    }

    public RectangleI setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public void scl(float k) {
        x *= k;
        y *= k;
        width *= k;
        height *= k;
    }

    public int getLeft() {
        return x;
    }

    public int getRight() {
        return x + width;
    }

    public int getBottom() {
        return y;
    }

    public int getTop() {
        return y + height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(Vector2i point){
        return contains(point.x,point.y);
    }

    public Vector2i getSize(){
        return new Vector2i(width,height);
    }

    @Override
    public String toString() {
        return "("+x+","+y+")"+"("+width+"x"+height+")";
    }

    public void set(RectangleI region) {
        x=region.x;
        y=region.y;
        width=region.width;
        height=region.height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RectangleI rectangle = (RectangleI) o;

        if (height != rectangle.height) return false;
        if (width != rectangle.width) return false;
        if (x != rectangle.x) return false;
        if (y != rectangle.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }
}
