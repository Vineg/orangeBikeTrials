package ru.vineg.structure.graphs;

import java.util.*;

/**
 * Created by vineg on 07.02.2015.
 */
public class BestFirstSearch<T extends INode<T>> {
    private final HashMap<T, T> predessors = new HashMap<>();
    private final int iterationsLimit = 100;
    private final List<T> candidates=new ArrayList<>();
    private final ArrayList<T> checked=new ArrayList<>();
    private final ArrayList<T> path=new ArrayList<>();


    public ArrayList<T> getPath(final T a, final T b) {
        candidates.clear();
        predessors.clear();
        path.clear();
        checked.clear();
        candidates.add(a);
        int i = 0;
        while (true) {
            if (candidates.get(0) == b||i>=iterationsLimit) {
                break;
            }
            T current = candidates.get(0);
            int childrenCount = current.getChildrenCount();
            for (int j = 0; j < childrenCount; j++) {
                T neighbour = current.getChild(j);
                if(candidates.indexOf(neighbour)==-1&&checked.indexOf(neighbour)==-1) {
                    predessors.put(neighbour, current);
                    candidates.add(neighbour);
                }
            }
            checked.add(candidates.remove(0));
            if(candidates.size()==0){
                return null;
            }

            Collections.sort(candidates, new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    return (int) Math.signum(o1.dist(b)-o2.dist(b));
                }
            });

            i++;
        }
        T current = candidates.get(0);
        path.add(a);
        while (current != a) {
            path.add(current);
            current=predessors.get(current);
        }
        Collections.reverse(path);
        return path;
    }
}
