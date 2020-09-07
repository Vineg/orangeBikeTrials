package ru.vineg.structure.layers;

import com.badlogic.gdx.math.Vector2;
import junit.framework.TestCase;

public class LayerObject2dTest extends TestCase {

    public void testSetPosition() throws Exception {
        SimpleObject parent = new SimpleObject();
        SimpleObject child = new SimpleObject();
        child.changeParent(parent);
        parent.setPosition(1,0);
        assertEquals(1f,child.getPosition().x);
    }

    class SimpleObject extends LayerObject2d {
        public Vector2 position = new Vector2();


        @Override
        protected void setObjectPosition(float x, float y) {
            position.set(x,y);
        }


        @Override
        public float getX() {
            return position.x;
        }

        @Override
        public float getY() {
            return position.y;
        }
    }
}