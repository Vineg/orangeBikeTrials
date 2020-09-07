package ru.vineg.orangeBikeFree.screens;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


/**
 * Created by vineg on 09.11.2014.
 */
public class TextButtonData {
    public String label;
    public EventListener clickListener;

    public TextButtonData(String s) {
        label=s;
    }

    public void addListener(EventListener gameClickListener) {
        clickListener=gameClickListener;
    }
}
