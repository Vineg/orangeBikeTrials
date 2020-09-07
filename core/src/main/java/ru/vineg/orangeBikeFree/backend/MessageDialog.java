package ru.vineg.orangeBikeFree.backend;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Vineg on 20.03.14.
 */
public class MessageDialog extends Dialog {
    public MessageDialog(String name, Skin skin, String type) {

        super(name, skin, type);
        setColor(1,1,1,0);
        defaults().pad(2);
        pad(16, 5, 5, 10);
    }
}
