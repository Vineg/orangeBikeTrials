package ru.vineg.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Vineg on 19.03.14.
 */
public class Animator {
    public static Actor animateActor(Texture texture, int frameCols, int frameRows) {

        Animation animation = animate(texture, frameCols, frameRows);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        return new AnimatedActor(new AnimationDrawable(animation));
    }

    public static Animation animate(Texture texture, int frameCols, int frameRows) {
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() /
                frameCols, texture.getHeight() / frameRows);
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return new Animation(0.125f, frames);
    }


}
