package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import ru.vineg.AccessDeniedException;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 27.10.13
 * Time: 20:05
 * To change this  template use File | Settings | File Templates.
 */
public class DirectoryStructure {
    private static String levelsDir = "levels/";
    private static String myLevelsDir = ".motodroid/myLevels/";

    public static FileHandle getLevelsDir() {
        return Gdx.files.internal(levelsDir);
    }

    public static FileHandle getMyLevelsDir(boolean forceLocal) throws AccessDeniedException {
        FileHandle levelsDir = null;
        if (!forceLocal&&Gdx.files.isExternalStorageAvailable() && Motodroid.isDebugging()) {
            levelsDir = Gdx.files.external(myLevelsDir);
        } else if (Gdx.files.isLocalStorageAvailable()) {
            levelsDir = Gdx.files.local(myLevelsDir);
        }
        if (levelsDir != null) {
            if(!levelsDir.exists()){
                levelsDir.mkdirs();
//                if(!forceLocal){
//                    return getMyLevelsDir(true);
//                }
                if(!levelsDir.exists()) {
                    throw new AccessDeniedException("failed create directory " + myLevelsDir);
                }
            }
        }
        if(levelsDir==null){
            throw new ru.vineg.AccessDeniedException("my levels dir unavailable");
        }
        return levelsDir;
    }

    public static FileHandle getMyLevelsDir() throws ru.vineg.AccessDeniedException {
        return getMyLevelsDir(false);
    }
}
