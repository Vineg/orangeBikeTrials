package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.files.FileHandle;
import ru.vineg.map.VertexMap;
import ru.vineg.orangeBikeFree.reflection.LevelEntry;

/**
 * Created by Vineg on 25.03.14.
 */
public class LevelSource {


    public boolean isDefault=true;

    public LevelSource(LevelEntry level) {
        this.level=level;
    }

    public LevelSource(VertexMap vertexMap) {
        this.level=new LevelEntry(vertexMap);
    }

    public LevelEntry getLevel() {
        return level;
    }

    private final LevelEntry level;

    public LevelSource(FileHandle data, int n) {
        level = new LevelEntry(data,n);
    }


    public String getName() {
        return level.getName();
    }

    public boolean isDefault() {
        return isDefault;
    }

    public int getNumber() {
        return level.getNumber();
    }
}
