package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import ru.vineg.math.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: vineg
 * Date: 10/30/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class LevelsSource {
    private int totalLevels;
    private int unlockedLevel;
    private final FileHandle[] levels;
    private boolean editable = false;

    public LevelsSource(FileHandle[] levels) {
        this(levels, false);
    }

    public LevelsSource(FileHandle[] levels, boolean editable) {
        this.editable = editable;
        totalLevels = levels.length;
        Arrays.sort(levels, new Comparator<FileHandle>() {
            @Override
            public int compare(FileHandle o1, FileHandle o2) {
                String name1 = o1.nameWithoutExtension();
                String name2 = o2.nameWithoutExtension();
                return compare(name1, name2);
            }

            private int compare(String name1, String name2) {
                return getWeight(name1) > getWeight(name2) ? 1 : -1;
            }

            private int getWeight(String name) {
                    return Numbers.getInt(name);  //To change body of created methods use File | Settings | File Templates.

            }


        });
        this.levels = levels;
    }

    public FileHandle[] getLevels() {
        return levels;
    }

    public LevelSource get(int i) {
        i = Math.min(i, levels.length - 1);
        return editable?new EditableLevel(levels[i],i):new LevelSource(levels[i], i);
    }

    public int size() {
        return levels.length;
    }

    public ArrayList<LevelSource> getLevelsList() {
        ArrayList<LevelSource> list = new ArrayList<LevelSource>();
        for (int i = 0; i < size(); i++) {
            list.add(get(i));
        }
        return list;
    }

    public void unlock(int level) {
        Preferences prefs = getPrefs();
        int maxLevel = getUnlockedLevel();
        if (level > maxLevel) {

            prefs.putInteger("unlocked_level", level);

            prefs.flush();
        }
    }

    public int getUnlockedLevel() {

//        getPrefs().clear();
//        getPrefs().flush();
        int unlockedLevel = getPrefs().getInteger("unlocked_level");
        return MathUtils.range(unlockedLevel, 0, totalLevels - 1);
        //return getTotalLevels();
    }

    public static Preferences getPrefs() {
        return Gdx.app.getPreferences("motodroid");
    }

    public int getTotalLevels() {
        return totalLevels;
    }

    public boolean isChanged() {
        int res = unlockedLevel;
        unlockedLevel=getUnlockedLevel();
        return res!=unlockedLevel;
    }

    public void levelFailed(int number) {
//        Preferences prefs = getPrefs();
//        int levelFails;
//        levelFails = getLevelFails(number);
//        prefs.putInteger("levelFails"+number, levelFails+1);
//        prefs.flush();
    }

    public int getLevelFails(int number) {
        Preferences prefs = getPrefs();
        return Math.max(0, prefs.getInteger("levelFails" + number));
    }
}
