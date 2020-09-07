package ru.vineg.graphics;

import com.badlogic.gdx.UpdateHandlerList;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.StaticSpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.andengine.engine.handler.IUpdateHandler;
import ru.vineg.orangeBikeFree.IScene;
import ru.vineg.orangeBikeFree.SceneObject;
import ru.vineg.orangeBikeFree.StaticSceneObject;
import ru.vineg.structure.QuadTree;
import ru.vineg.structure.QuadtreeVectorElement;
import ru.vineg.structure.RectangleI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by vineg on 17.01.2015.
 */
public class GameGraphics implements IScene {

    public final SpriteBatch spriteBatch;
    public final StaticSpriteBatch staticSpriteBatch;
    private UpdateHandlerList mUpdateHandlers = new UpdateHandlerList(8);
    protected QuadTree spaceTree = new QuadTree(32);
    protected final ArrayList<ArrayList<Sprite>> staticSpriteList = new ArrayList<>();
    protected final RectangleI spriteBounds = new RectangleI();
    protected final ArrayList<Sprite> dynamicSpriteList = new ArrayList<>();
    protected DefaultCamera camera=new DefaultCamera();

    public GameGraphics(SpriteBatch batch) {
        this.spriteBatch=batch;
        staticSpriteBatch=new StaticSpriteBatch();
    }

    public void addStaticSceneObject(SceneObject graphics) {
        spaceTree.insert(new QuadtreeVectorElement(graphics));
    }

    @Override
    public void removeStaticSceneObject(StaticSceneObject graphics) {
        staticSpriteList.remove(graphics);
    }

    @Override
    public void addStaticSprite(Sprite sprite) {
        addStaticSprite(sprite,0);
    }

    @Override
    public void addStaticSprite(Sprite sprite, int layer) {
        while(layer>=staticSpriteList.size()){
            staticSpriteList.add(new ArrayList<Sprite>());
        }
        staticSpriteList.get(layer).add(sprite);
//        staticSorted=false;
    }

    @Override
    public void removeStaticSprite(Sprite sprite) {
        staticSpriteList.remove(sprite);
    }




    public void addSprite(Sprite graphics) {
        dynamicSpriteList.add(graphics);
//        sorted=false;
    }

    public void removeSprite(Sprite graphics) {
        dynamicSpriteList.remove(graphics);
    }

    private boolean staticSorted=false;
    public void drawStatic(){
        if(!staticSorted){
            for (int i = 0; i < staticSpriteList.size(); i++) {
                ArrayList<Sprite> list = staticSpriteList.get(i);
                Collections.sort(list, new Comparator<Sprite>() {
                    @Override
                    public int compare(Sprite o1, Sprite o2) {
                        int h1 = o1.getTexture().hashCode();
                        int h2 = o2.getTexture().hashCode();
                        return h2-h1;
                    }
                });
            }
            staticSorted=true;
        }

        spriteBatch.setProjectionMatrix(getCamera().combined);
        for (int i = 0; i < staticSpriteList.size(); i++) {
            ArrayList<Sprite> list = staticSpriteList.get(i);
            for (int j = 0; j < list.size(); j++) {
                Sprite sprite = list.get(j);
                sprite.draw(spriteBatch);
            }
        }

    }

//    private boolean sorted=false;
    public void draw() {
//        if(!sorted){
//            Collections.sort(dynamicSpriteList, new Comparator<Sprite>() {
//                @Override
//                public int compare(Sprite o1, Sprite o2) {
//                    int h1 = o1.getTexture().hashCode();
//                    int h2 = o2.getTexture().hashCode();
//                    return h2-h1;
//                }
//            });
//            sorted=true;
//        }
        spriteBatch.setProjectionMatrix(getCamera().combined);

        drawStatic();
        for (int i = 0; i < dynamicSpriteList.size(); i++) {
            Sprite sprite = dynamicSpriteList.get(i);
            sprite.draw(spriteBatch);
        }
    }

    @Override
    public void act(float pSecondsElapsed) {

        if (getCameraObject() == null) {
            return;
        }

        if (this.mUpdateHandlers != null) {
            this.mUpdateHandlers.onUpdate(pSecondsElapsed);
        }

    }


    RectangleI bounds = new RectangleI();
    public boolean isInCamera(float x, float y) {
        ICamera2d camera = getCameraObject();
        camera.copyBounds(bounds,0);
        return bounds.contains((int)x,(int)y);

    }

    @Override
    public void registerUpdateHandler(final IUpdateHandler pUpdateHandler) {
        this.mUpdateHandlers.add(pUpdateHandler);
    }

    @Override
    public void removeUpdateHandler(IUpdateHandler handler) {
        mUpdateHandlers.remove(handler);
    }

    @Override
    public void addActor(Actor actor) {

    }

    @Override
    public ICamera2d getCameraObject() {
        return camera;
    }


    @Override
    public void reset() {
        dynamicSpriteList.clear();
        spaceTree = new QuadTree(32);

//        polygonSprites.clear();
    }

    public Camera getCamera() {
        return camera;
    }
}
