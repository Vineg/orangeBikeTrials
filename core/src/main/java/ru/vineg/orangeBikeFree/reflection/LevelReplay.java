package ru.vineg.orangeBikeFree.reflection;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import ru.vineg.ControlsAction;
import ru.vineg.orangeBikeFree.Motodroid;

/**
 * Created by Vineg on 09.02.14.
 */
public class LevelReplay {
    public LevelEntry level;
    public final int version;
    private Array<MControlsAction> data=new Array<>();
    public float finishTime;

    public LevelReplay(){
        this.version = Motodroid.getEngineVersion();
        this.level=null;
    }

    public LevelReplay(LevelEntry level) {
        this();
        this.level = level;
    }

    public void add(MControlsAction action){
        data.add(action);
    }

    public MControlsAction peek() {
        if(data.size<1){return null;}
        return data.get(0);
    }

    public ControlsAction poll() {
        if(data.size<1){return null;}
        return data.removeIndex(0);
    }

    public Object clone(){
        LevelReplay replay = new LevelReplay(level);
        for(Object action:data){
            replay.add((MControlsAction) action);
        }
        return replay;
    }

    public void clear() {
        data.clear();
    }

    public ControlsAction get(int i) {
        return (ControlsAction) data.get(i);
    }

    public static LevelReplay fromString(String data) {
        return new Json().fromJson(LevelReplay.class,data);
    }

    public void setData(Array<MControlsAction> data){
        this.data=data;
    }


}
