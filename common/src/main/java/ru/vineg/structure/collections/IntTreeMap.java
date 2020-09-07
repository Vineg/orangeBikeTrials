package ru.vineg.structure.collections;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
 * Created by vineg on 09.03.15.
 */
public class IntTreeMap<T> {
    public ArrayList<Integer> keys = new ArrayList<>();
    public ArrayList<T> values = new ArrayList<>();

    public T get(Integer key) {
        if (keys.size()==0) {
            return null;
        }
        int idx = getIndex(key);
        if(idx>=keys.size()){
            return null;
        }
        if (keys.get(idx).equals(key)) {
            return values.get(idx);
        } else {
            return null;
        }
    }

    private int getIndex(Integer key) {
        if (keys.size() == 0) {
            return 0;
        }
        if (keys.get(0) > key) {
            return 0;
        } else if (keys.get(keys.size() - 1) < key) {
            return keys.size();
        }
        int left=0;
        int right=keys.size();
        int mid;
        do{
            mid=(left+right)/2;
            Integer midKey = keys.get(mid);
            if(midKey<key){
                left=mid+1;
            }else{
                right=mid;
            }
        }while(left!=right);

        return left;

    }

    public void put(Integer key, T value) {
        int idx = getIndex(key);
        if (idx >= 0&&keys.size()>0&&idx<keys.size()) {
            if (keys.get(idx).equals(key)) {
                values.set(idx,value);
                return;
            }
        }
        keys.add(idx,key);
        values.add(idx,value);
    }

    public void removeIdx(int i) {
        values.remove(i);
        keys.remove(i);
    }
}
