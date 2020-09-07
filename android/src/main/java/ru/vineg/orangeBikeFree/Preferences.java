package ru.vineg.orangeBikeFree;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
//import com.google.analytics.tracking.android.EasyTracker;
//import com.google.analytics.tracking.android.Fields;
//import com.google.analytics.tracking.android.MapBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 06.01.14
 * Time: 0:16
 * To change this template use File | Settings | File Templates.
 */
public class Preferences extends GamePreferences {
    public static boolean smoothMap = false;
//    private final EasyTracker tracker;
    private boolean isTracing = false;
    private boolean isDebugging = true;

    public Preferences(Context defaultContext){
//        tracker = EasyTracker.getInstance(defaultContext);
    };

    public void trackLevelFinish(int number, int levelFails){

        // MapBuilder.createEvent().build() returns a Map of event fields and values
        // that are set and sent with the hit.
//        tracker.set(Fields.customDimension(1), String.valueOf(number));
//        tracker.set(Fields.customDimension(2), String.valueOf(levelFails));
//        tracker.send(MapBuilder.createEvent("level_finish","level_finish",null,null).build());
    }

    public boolean isTracing() {
        return isTracing;
    }

    public void startTracing() {
        isTracing = true;
        Debug.startMethodTracing("motodroid");
    }

    @Override
    public boolean isDebugging() {
        return isDebugging;
    }

    @Override
    public boolean isSmoothBike() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void stopTracing() {
        isTracing = false;
        Debug.stopMethodTracing();
    }

    @Override
    public void shareReplay(String link) {
        try
        { Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Orange Bike");
            String sAux = "\nMy level replay\n\n";
            sAux = sAux + link+" \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            MainActivity.instance.startActivity(Intent.createChooser(i, "choose one"));
        }
        catch(Exception e)
        { //e.toString();
        }
    }

    @Override
    public void shareLevel(String link) {
        try
        { Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Orange Bike");
            String sAux = "\nMy level\n\n";
            sAux = sAux + link+" \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            MainActivity.instance.startActivity(Intent.createChooser(i, "choose one"));
        }
        catch(Exception e)
        { //e.toString();
        }
    }
}

