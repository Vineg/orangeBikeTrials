package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.graphics.Color;
import ru.vineg.structure.layers.LayerObject;
import ru.vineg.structure.layers.LayerObject2d;

/**
 * Created by vineg on 03.02.2015.
 */
public class AnimatedColorObject extends LayerObject {
    private boolean ended=false;

    public void setColor(Color color){}
    public void animate(){
        ended=true;
    };

    public boolean ended() {
        return ended;
    }

    @Override
    public void dispose() {

    }
}
