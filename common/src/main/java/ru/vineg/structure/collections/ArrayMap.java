package ru.vineg.structure.collections;


import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
* Created by vineg on 09.03.15.
*/
public class ArrayMap<T>{
    private final IntTreeMap<ArrayList<T>> data=new IntTreeMap<>();

    public void put(Integer key, T object) {
        getBucket(key).add(object);
    }

    private ArrayList<T> getBucket(Integer key) {
        ArrayList<T> bucket = data.get(key);
        if(bucket==null){
            bucket=new ArrayList<>();
            data.put(key, bucket);
        }
        return bucket;
    }

    public void remove(T object) {
        for (int i = 0; i < data.values.size(); i++) {
            ArrayList<T> bucket = data.values.get(i);
            if(bucket.remove(object)){
                if(bucket.size()==0){
                    data.removeIdx(i);
                }
            }
        }
    }

    public ArrayList<ArrayList<T>> values() {
        return data.values;
    }

    public void clear() {
        data.values.clear();
        data.keys.clear();
    }
}
