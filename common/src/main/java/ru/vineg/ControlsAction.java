package ru.vineg;


/**
 * Created by Vineg on 08.02.14.
 */
public class ControlsAction{
    public int keyCode;
    public boolean down;

    public ControlsAction(int keyCode, boolean down) {
        this.keyCode = keyCode;
        this.down = down;
    }

    public boolean equals(Object obj){
        ControlsAction controlsAction = (ControlsAction) obj;
        return controlsAction.down==down&&controlsAction.keyCode==keyCode;
    }

    @Override
    public String toString() {
        return keyCode+" "+(down?"down":"up");
    }
}
