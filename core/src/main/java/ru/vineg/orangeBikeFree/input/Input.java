package ru.vineg.orangeBikeFree.input;

import com.badlogic.gdx.Gdx;

/**
 * Created by Vineg on 25.02.14.
 */
public class Input{
    public static boolean shiftPressed=false;
    public static boolean isShiftPressed(){
        return Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.SHIFT_LEFT)||shiftPressed;
    }
}
