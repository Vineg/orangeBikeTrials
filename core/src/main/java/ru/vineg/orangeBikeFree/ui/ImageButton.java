package ru.vineg.orangeBikeFree.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.vineg.orangeBikeFree.Motodroid;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 18.10.13
 * Time: 23:42
 * To change this template use File | Settings | File Templates.
 */
public class ImageButton extends com.badlogic.gdx.scenes.scene2d.ui.ImageButton {
    boolean pressed=false;
    public ImageButton(TextureAtlas.AtlasRegion button, TextureAtlas.AtlasRegion pressed){
        super(new TextureRegionDrawable(button), new TextureRegionDrawable(pressed));
    }

    public ImageButton(ImageButtonStyle imageButtonStyle) {
        super(imageButtonStyle);
    }



    public static ImageButton fromTexture(Texture texture){
        return fromTexture(new TextureRegion(texture));
    }

    public static ImageButton fromTexture(TextureRegion region) {
        TextureRegion textureRegion = region;
        Skin skin = Motodroid.skin;
        ButtonStyle buttonStyle = skin.get(ButtonStyle.class);
        ImageButtonStyle imageButtonStyle = new ImageButtonStyle(buttonStyle);
        imageButtonStyle.imageUp=new TextureRegionDrawable(textureRegion);
        ImageButton imageButton = new ImageButton(imageButtonStyle);
//        imageButton.getImageCell().fill();
        return imageButton;
    }

//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        if(isPressed()&&!pressed){
//            InputEvent event = new InputEvent();
//            event.setType(InputEvent.Type.touchDown);
//            event.setTarget(this);
//            super.notify(event, false);
//            pressed=true;
//        }else if(!isPressed()&&pressed){
//            InputEvent event = new InputEvent();
//            event.setType(InputEvent.Type.touchUp);
//            event.setTarget(this);
//            super.notify(event, false);
//            pressed=false;
//        }
//        super.draw(batch, parentAlpha);
//    }

    @Override
    public boolean notify(Event event, boolean capture) {
        return super.notify(event, capture);
//        return true;
    }
}
