package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.net.HttpStatus;

/**
 * Created by Vineg on 19.04.2014.
 */
public class Debug {
    public static void println(String data) {
//        if(Motodroid.gamePreferences.isDebugOutputEnabled()){
            System.out.println(data);
//        }
    }

    public static void println(Object obj) {
        println(obj.toString());
    }
}
