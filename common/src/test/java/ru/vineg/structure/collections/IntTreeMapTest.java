package ru.vineg.structure.collections;

import junit.framework.TestCase;

public class IntTreeMapTest extends TestCase {

    public void testPut() throws Exception {
        IntTreeMap<Object> map = new IntTreeMap<>();
        map.put(0,null);
        map.put(-1,null);
        map.put(1,null);
        map.put(1,null);
        map.put(2,null);
        for (int i = 0; i < 100000; i++) {
            map.put((int)(Math.random()*10000)-5000,null);
        }

        for (int i = 0; i < map.keys.size() - 1; i++) {
            assert map.keys.get(i)<map.keys.get(i+1);
        }
    }
}