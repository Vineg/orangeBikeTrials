package ru.vineg.orangeBikeFree.client;

import ru.vineg.orangeBikeFree.GamePreferences;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 06.01.14
 * Time: 0:16
 * To change this template use File | Settings | File Templates.
 */
public class Preferences extends GamePreferences {
    public static boolean smoothMap = false;
    private boolean isTracing = false;
    private boolean isDebugging = true;

    public Preferences(boolean debug) {
        isDebugging=debug;
    }

    public boolean isTracing() {
        return isTracing;
    }

    public void startTracing() {
    }

    public boolean isDebugging() {
        return isDebugging;
    }

    public boolean isSmoothBike() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void stopTracing() {
    }
}

