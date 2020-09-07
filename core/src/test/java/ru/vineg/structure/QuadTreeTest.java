package ru.vineg.structure;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Vineg on 01.05.2014.
 */
public class QuadTreeTest {
    RectangleI rectangle = new RectangleI(0, 0, 500, 500);
    private int seen;

    @Test
    public void testQuery() throws Exception {
        final boolean[] c = new boolean[2];
        QuadTree tree = new QuadTree(1);
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            tree.insert(new QuadtreeElement() {
                @Override
                public int getX() {
                    return finalI;
                }

                @Override
                public int getY() {
                    return finalI + 1;
                }
            });
        }

        tree.query(new RectangleI(5, 6, 1, 1), new QuadtreeCallback() {
            @Override
            public void reportQuadtreeElement(QuadtreeElement element) {
                Assert.assertEquals(element.getX(), 5);
                Assert.assertEquals(element.getY(), 6);
                c[0] = true;
            }
        });

        TestCase.assertTrue(c[0]);

//        if(true)
//        return;

        tree = new QuadTree(50);
        System.out.println("building tree");
        for (int i = 0; i < 1000000; i++) {

            final double x = Math.random() * 100000;
            final double y = Math.random() * 100000;
            tree.insert(new QuadtreeElement() {
                @Override
                public int getX() {
                    return (int) x;
                }

                @Override
                public int getY() {
                    return (int) y;
                }
            });
        }

        System.out.println("ready, querying");
        for (int i = 0; i < 10000; i++) {
            rectangle.x = (int) (Math.random() * 8000);
            rectangle.y = (int) (Math.random() * 8000);
            tree.query(rectangle, new QuadtreeCallback() {
                @Override
                public void reportQuadtreeElement(QuadtreeElement element) {
                    c[1] = true;
                    seen++;
//                    Assert.assertEquals(element.getX(), 3);
//                    Assert.assertEquals(element.getY(), 4);
                }
            });
        }
        TestCase.assertTrue(c[1]);
        System.out.println("seen:"+seen);
    }


}
