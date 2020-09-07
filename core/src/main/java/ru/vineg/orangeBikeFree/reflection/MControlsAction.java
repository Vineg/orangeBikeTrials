package ru.vineg.orangeBikeFree.reflection;

import ru.vineg.ControlsAction;

/**
 * Created by vineg on 21.01.2015.
 */
public class MControlsAction extends ControlsAction {
    public long tick;

    public MControlsAction(int keyCode, boolean down) {
        super(keyCode, down);
    }
}
