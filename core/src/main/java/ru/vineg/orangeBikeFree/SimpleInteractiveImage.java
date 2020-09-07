package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import ru.vineg.map.Vertex;
import ru.vineg.orangeBikeFree.input.Input;
import ru.vineg.map.IVertex;

/**
 * Created by Vineg on 25.02.14.
 */
public class SimpleInteractiveImage extends SimpleImage implements IVertex {

    private final IVertex position;
    public boolean isRemovable=true;

    public SimpleInteractiveImage(TextureRegion region, IVertex position) {
        super(region);
        this.position=position;
        setPosition(position);
    }

    private void setPosition(IVertex position) {
        this.setPosition(position.getPosition());
    }

    public SimpleInteractiveImage(TextureRegion textureRegion, Vector2 size, IVertex position) {
        super(textureRegion);
        this.position=position;
        setPosition(position);
        setSize(size.x, size.y);
        setOriginCenter();
    }

    void init() {
//        changeOrigin(getWidth()/2,getHeight()/2);
        addListener(new DragListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (Input.isShiftPressed()) {
                    if (isRemovable) {
                        remove();
                    }
                }
                super.touchDown(event, x, y, pointer, button);
                event.cancel();
                return true;
            }

            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                super.dragStart(event, x, y, pointer);
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                translate((x - getTouchDownX())*getScaleX() + getOriginX(), (y - getTouchDownY())*getScaleY() + getOriginY());
                event.stop();
                super.drag(event, x, y, pointer);
            }

        });
        super.init();
    }

    private Vector2 setPosition=new Vector2();
    @Override
    public void setPosition(float x, float y) {
        position.setPosition(setPosition.set(x,y));
        super.setPosition(x, y);
    }

    @Override
    public Vector2 getPosition() {
        return position.getPosition();
    }

    //@Override
    public void translate(float x, float y) {
        setPosition(getX() + x, getY() + y);
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }
}
