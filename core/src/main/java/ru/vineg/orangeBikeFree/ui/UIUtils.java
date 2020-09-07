package ru.vineg.orangeBikeFree.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.vineg.orangeBikeFree.Motodroid;
import ru.vineg.orangeBikeFree.screens.TextButtonData;

/**
 * Created by vineg on 09.11.2014.
 */
public class UIUtils {
    public static TextButton getTextButton(TextButtonData data){
        TextButton button = new TextButton(data.label, Motodroid.skin);
        button.addListener(data.clickListener);
        return button;
    }
}
