package ru.vineg.structure;


import java.util.ArrayList;

/**
 * Created by Vineg on 01.05.2014.
 */
public class QuadTreeBranch extends RectangleI implements IQuadTreeBranch {
    private final QuadTree params;
    QuadTreeBranch left;
    QuadTreeBranch right;
    ArrayList<QuadtreeElement> data;
    boolean xDivided;


    public QuadTreeBranch(QuadTree params, int size, QuadtreeElement elem) {
        super(elem.getX(), elem.getY(), size, size);
        this.params = params;
        data = new ArrayList<QuadtreeElement>(params.maxLeafDataSize);
        insert(elem);
    }

    public QuadTreeBranch(QuadTree params, Vector2i position, Vector2i size) {
        super(position.x, position.y, size.x, size.y);
        this.params = params;
        data = new ArrayList<QuadtreeElement>(params.maxLeafDataSize);
    }

    @Override
    public boolean contains(QuadtreeElement elem) {
        return super.contains(elem.getX(), elem.getY());
    }

    @Override
    public boolean insert(QuadtreeElement elem) {
        if (!contains(elem)) {
            return false;
        }
        if (isLastLeaf()) {
            addData(elem);
        } else {
            if (left != null) {
                if (left.contains(elem)) {
                    left.insert(elem);
                } else {
                    right.insert(elem);
                }
            } else {
                if (!addData(elem)) {
                    subdivide();
                    insert(elem);
                }
            }
        }
        return true;
    }

    private void subdivide() {
        if (getWidth() > getHeight()) {
            subdivideX();
        } else {
            subdivideY();
        }
    }

    private void subdivideY() {
        Vector2i size = getSize();
        size.y /= 2;
        left = new QuadTreeBranch(params, getPosition(), size);
        right = new QuadTreeBranch(params, getPosition().add(0, size.y), size);
        moveData();
    }

    private void subdivideX() {
        xDivided = true;
        Vector2i size = getSize();
        size.x /= 2;
        left = new QuadTreeBranch(params, getPosition(), size);
        right = new QuadTreeBranch(params, getPosition().add(size.x, 0), size);
        moveData();
    }

    private void moveData() {
        for (int i = 0; i < data.size(); i++) {
            if (!left.insert(data.get(i))) {
                right.insert(data.get(i));
            }
        }
        data = null;
    }


    private boolean addData(QuadtreeElement elem) {

        if (!isLastLeaf() && data.size() >= params.maxLeafDataSize) {
            return false;
        } else {
            data.add(elem);
            return true;
        }
    }

    @Override
    public Vector2i getPosition() {
        return new Vector2i(x, y);
    }

    @Override
    public void query(RectangleI rect, QuadtreeCallback callback) {

        if (isLeaf()) {
            if (isLastLeaf()) {
                for (int i = 0; i < data.size(); i++) {
                    callback.reportQuadtreeElement(data.get(i));
                }
            } else {
                for (int i = 0; i < data.size(); i++) {
                    if (rect.contains(data.get(i).getX(), data.get(i).getY())) {
                        callback.reportQuadtreeElement(data.get(i));
                    }
                }
            }
        } else {
            if (xDivided) {
                if (rect.getLeft() < left.getRight()) {
                    left.query(rect, callback);
                    if (rect.getRight() > right.getLeft()) {
                        right.query(rect, callback);
                    }
                } else {
                    right.query(rect, callback);
                }
            } else {
                if (rect.getBottom() < left.getTop()) {
                    left.query(rect, callback);
                    if (rect.getTop() > right.getBottom()) {
                        right.query(rect, callback);
                    }
                } else {
                    right.query(rect, callback);
                }
            }
        }
    }


    boolean isLeaf() {
        return left == null;
    }


    private boolean isLastLeaf() {
        return params.isLeaf(this);
    }


}
