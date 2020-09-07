package ru.vineg.structure;


/**
 * Created by Vineg on 01.05.2014.
 */
public class QuadTree {
    QuadTreeBranch root;
    private int leafSize;
    public int maxLeafDataSize = 1;

    public QuadTree(int leafSize) {
        this.leafSize=leafSize;
        root=new QuadTreeBranch(this,new Vector2i(0,0),new Vector2i(leafSize,leafSize));
    }


    public void query(RectangleI rect, QuadtreeCallback callback) {
        root.query(rect, callback);
//        int k_stackCapacity = 256;
//        QuadTreeBranch[] stack = new QuadTreeBranch[k_stackCapacity];
//        stack[0] = root;
//        int sp = 1;
//
//        while (sp > 0) {
//            QuadTreeBranch playerId = stack[--sp];
//
//            if (playerId.intersects(rect)) {
//                if (playerId.isLeaf()) {
//                    for (int i = 0; i < playerId.data.bodySize(); i++) {
//                        if (rect.contains(playerId.data.get(i).getX(), playerId.data.get(i).getY())) {
//                            callback.reportQuadtreeElement(playerId.data.get(i));
//                        }
//                    }
//                } else {
//                    stack[sp++] = playerId.left;
//                    stack[sp++] = playerId.right;
//                }
//            }
//        }
    }

    public void insert(QuadtreeElement elem) {
        if (root == null) {
            root = new QuadTreeBranch(this, leafSize, elem);
        }
        while (!root.insert(elem)) {
            extend(elem);
        }
    }

    private void extend(QuadtreeElement elem) {
        if (root.getWidth() < root.getHeight()) {
            if (root.getLeft() > elem.getX()) {
                extendLeft();
            } else {
                extendRight();
            }
        } else {
            if (root.getBottom() > elem.getY()) {
                extendDown();
            } else {
                extendUp();
            }
        }
    }

    private void extendUp() {
        QuadTreeBranch oldRoot = root;
        Vector2i size = oldRoot.getSize();
        size.y *= 2;
        root = new QuadTreeBranch(this, oldRoot.getPosition(), size);
        root.left = oldRoot;
        root.right = new QuadTreeBranch(this, oldRoot.getPosition().add(oldRoot.getSize().scl(0, 1)), oldRoot.getSize());
    }

    private void extendDown() {
        QuadTreeBranch oldRoot = root;
        Vector2i size = oldRoot.getSize();
        root = new QuadTreeBranch(this, oldRoot.getPosition().add(size.scl(0, -1)), oldRoot.getSize().scl(1, 2));
        root.right = oldRoot;
        root.left = new QuadTreeBranch(this, oldRoot.getPosition().add(oldRoot.getSize().scl(0, -1)), oldRoot.getSize());
    }

    private void extendRight() {
        QuadTreeBranch oldRoot = root;
        Vector2i size = oldRoot.getSize();
        size.x *= 2;
        root = new QuadTreeBranch(this, oldRoot.getPosition(), size);
        root.xDivided = true;
        root.left = oldRoot;
        root.right = new QuadTreeBranch(this, oldRoot.getPosition().add(oldRoot.getSize().scl(1, 0)), oldRoot.getSize());
    }

    private void extendLeft() {
        QuadTreeBranch oldRoot = root;
        Vector2i size = oldRoot.getSize();
        root = new QuadTreeBranch(this, oldRoot.getPosition().add(size.scl(-1, 0)), oldRoot.getSize().scl(2, 1));
        root.xDivided = true;
        root.right = oldRoot;
        root.left = new QuadTreeBranch(this, oldRoot.getPosition().add(oldRoot.getSize().scl(-1, 0)), oldRoot.getSize());
    }

    protected boolean isLeaf(QuadTreeBranch quadTreeBranch) {
        return quadTreeBranch.getWidth() <= leafSize && quadTreeBranch.getHeight() <= leafSize;
    }
}
