package ru.vineg.orangeBikeFree;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 14.01.14
 * Time: 20:41
 * To change this template use File | Settings | File Templates.
 */
public class Preferences extends GamePreferences {
    public static boolean smoothMap=false;
    private boolean isSmoothBike=true;
    private boolean isDebugging=true;

    @Override
    public boolean isTracing() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void stopTracing() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void startTracing() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isDebugging() {
        return isDebugging;
    }

    @Override
    public boolean isSmoothBike() {
        return isSmoothBike;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void trackLevelFinish(int level, int levelFails) {
        System.out.println(levelFails);
        super.trackLevelFinish(level, levelFails);
    }
}
