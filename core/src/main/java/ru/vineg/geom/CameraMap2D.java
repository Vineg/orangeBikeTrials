//package ru.vineg.geom;
//
//import com.badlogic.gdx.math.Vector2i;
//import ru.vineg.orangeBikeFree.map.IVertex;
//import ru.vineg.orangeBikeFree.structure.Rectangle;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//
///**
// * Created by Vineg on 30.04.2014.
// */
//public class CameraMap2D<T extends IVertex> {
//
//    private T[] ySortedPoints;
//    private T[] xSortedPoints;
//    private ArrayList<T> result=new ArrayList<T>();
//    private Rectangle bounds;
//    private int leftIndex;
//    private int bottomIndex;
//    private int upIndex;
//    private int rightIndex;
//
//    public CameraMap2D(T[] points) {
//        Arrays.sort(points, new Comparator<T>() {
//            @Override
//            public int compare(T o1, T o2) {
//                float d = o1.getPosition().x - o2.getPosition().x;
//                if (d > 0) {
//                    return 1;
//                } else if (d < 0) {
//                    return -1;
//                } else {
//                    return 0;
//                }
//            }
//        });
//
//        xSortedPoints = points.clone();
//
//        Arrays.sort(points, new Comparator<T>() {
//            @Override
//            public int compare(T o1, T o2) {
//                float d = o1.getPosition().y - o2.getPosition().y;
//                if (d > 0) {
//                    return 1;
//                } else if (d < 0) {
//                    return -1;
//                } else {
//                    return 0;
//                }
//            }
//        });
//
//        ySortedPoints = points.clone();
//    }
//
//    public ArrayList<T> updateObjectsInBounds(Rectangle newBounds){
//        if(!newBounds.intersects(bounds)){
//            return getObjectsInBounds(newBounds);
//        }else{
//            int d = (int) Math.signum(newBounds.getLeft()-bounds.getLeft());
//            if(d!=0) {
//                while ((xSortedPoints[leftIndex+1].getPosition().x-newBounds.getLeft())*d>0){
//                    if((newBounds.y-xSortedPoints[leftIndex].getPosition().y)*d>0&&
//                            (bounds.y-xSortedPoints[leftIndex].getPosition().y)*d<0){
//                        if(d>0){
//                            removeLeft();
//                        }
//                    }
//                }
//            }
//
//
//
//            bottom:
//            right:
//            top:
//
//
//        }
//    }
//
//    private void removeLeft() {
//        leftIndex++;
//
//    }
//
//    public ArrayList<T> getObjectsInBounds(Rectangle bounds) {
//        int step;
//
//        step = xSortedPoints.length - 1;
//        leftIndex = 0;
//        while (step > 0) {
//            int d = (int) (step * Math.signum(xSortedPoints[leftIndex].getPosition().x - bounds.getLeft()));
//            leftIndex += d != -1 ? d : 0;
//            step++;
//            step /= 2;
//        }
//
//        step = xSortedPoints.length - 1;
//        bottomIndex = 0;
//        while (step > 0) {
//            int d = (int) (step * Math.signum(xSortedPoints[bottomIndex].getPosition().y - bounds.getLeft()));
//            bottomIndex += d != -1 ? d : 0;
//            step++;
//            step /= 2;
//        }
//
//        step = xSortedPoints.length - 1;
//        rightIndex = 0;
//        while (step > 0) {
//            int d = (int) (step * Math.signum(xSortedPoints[rightIndex].getPosition().x - bounds.getRight()));
//            rightIndex += d != 1 ? d : 0;
//            step++;
//            step /= 2;
//        }
//
//
//        step = xSortedPoints.length - 1;
//        upIndex = 0;
//        while (step > 0) {
//            int d = (int) (step * Math.signum(xSortedPoints[upIndex].getPosition().y - bounds.getTop()));
//            upIndex += d != 1 ? d : 0;
//            step++;
//            step /= 2;
//        }
//
//
//
//        if (upIndex - bottomIndex < rightIndex - leftIndex) {
//            for (int i = bottomIndex; i < upIndex; i++) {
//                Vector2i point = ySortedPoints[i].getPosition();
//                if (point.x > bounds.getLeft() && point.x < bounds.getRight()) {
//                    result.add(ySortedPoints[i]);
//                }
//            }
//        }else{
//            for (int i = leftIndex; i < rightIndex; i++) {
//                Vector2i point = xSortedPoints[i].getPosition();
//                if (point.y > bounds.getBottom() && point.y < bounds.getTop()) {
//                    result.add(xSortedPoints[i]);
//                }
//            }
//        }
//
//        this.bounds = bounds;
//
//        return result;
//    }
//}
