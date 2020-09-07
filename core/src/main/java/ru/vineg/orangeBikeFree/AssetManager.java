package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.vineg.graphics.Animator;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 20.01.14
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public class AssetManager extends com.badlogic.gdx.assets.AssetManager{
    public Texture getTexture(String path) {
        Texture texture = get(path, Texture.class);
        Texture.TextureFilter filter = null;
        if (GamePreferences.getQuality() == GamePreferences.Quality.high) {
            filter = Texture.TextureFilter.Linear;

        }else{
            filter = Texture.TextureFilter.Nearest;
        }
        texture.setFilter(filter, filter);
        return texture;
    }

    public Actor getLoadingAnimation() {
        Actor animation = Animator.animateActor(getTexture("loading.png"), 8, 1);
        animation.setSize(20,20);
        return animation;
    }
}
