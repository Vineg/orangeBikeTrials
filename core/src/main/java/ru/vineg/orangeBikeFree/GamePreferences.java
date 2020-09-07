package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 14.01.14
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
public abstract class GamePreferences {

    private static boolean updateLevelsList=false;
    private boolean debugOutputEnabled=true;

    public static boolean isUpdateLevelsList() {
        return updateLevelsList;
    }

    public boolean isTracing(){return false;};

    public void stopTracing(){};

    public void startTracing(){};

    public boolean isDebugging(){return false;};

    boolean isSmoothBike(){return true;};

    static Quality getQuality(){
        return Quality.values()[Motodroid.getPreferences().getInteger("quality")];
    };

    static void setQuality(Quality quality){
        Preferences preferences = Motodroid.getPreferences();
        preferences.putInteger("quality", quality.ordinal());
        preferences.flush();

    }

    public void trackLevelFinish(int level, int levelFails){};

    public void shareReplay(String link){
        Gdx.app.getClipboard().setContents(link);

        Motodroid.message("Link copied to clipboard");
    }

    public void shareLevel(String link){
        Gdx.app.getClipboard().setContents(link);

        Motodroid.message("Link copied to clipboard");
    }

    public boolean isAllUnlocked() {
        return isDebugging();
    }

    public boolean isDebugOutputEnabled() {
        return debugOutputEnabled;
    }

    public enum Quality{
        high(1),low(2);
        private final int value;
        Quality(int quality) {
            value=quality;
        }

        private int getValue() {
            return value;
        }
    }
}
