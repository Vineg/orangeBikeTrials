//package ru.vineg.ZoOmd;
//
//import junit.framework.TestCase;
//import ru.vineg.structure.RectangleI;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class RegionProviderTest extends TestCase {
//
//    public void testExcludeData() throws Exception {
//        RegionProvider<Integer> provider = new RegionProvider<Integer>(new IProvider2d<Integer>() {
//            @Override
//            public Integer get(int x, int y) {
//                return Integer.valueOf(x + 100 * y);
//            }
//        });
//        List<Integer> ar=new ArrayList<Integer>(Arrays.asList(new Integer[]{-101,-100,-99,-1,99}));
//        for (Integer i: provider.excludeData(new RectangleI(-1,-1,3,3),new RectangleI(0,0,3,3))) {
//            assertEquals(true,ar.remove(i));
//        }
//        assertEquals(0,ar.size());
//        ar=new ArrayList<Integer>(Arrays.asList(new Integer[]{-101,-100,-99,-1,99,-98}));
//        for (Integer i: provider.excludeData(new RectangleI(-1,-1,4,3),new RectangleI(0,0,3,3))) {
//            assertEquals(true,ar.remove(i));
//        }
//        assertEquals(0,ar.size());
//    }
//}