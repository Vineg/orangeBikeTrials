package ru.vineg.orangeBikeFree;

import box2d.PhysicsWorld;
import ru.vineg.game.GameWorld;
import ru.vineg.orangeBikeFree.entities.GameTextureManager;

/**
 * Created with IntelliJ IDEA.
 * User: kostik
 * Date: 9/15/13
 * Time: 11:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class MotodroidGameWorld extends GameWorld{

    public final MotodroidGameGraphics scene;

    public static final short CATEGORYBIT_WALL = 1;
    public static final short CATEGORYBIT_WHEEL = 2;
    public static final short CATEGORYBIT_VIRTUAL = 4;
    public static final short CATEGORYBIT_HEAD = 2;
    public static final short CATEGORYBIT_OBJECT = 8;

    public static final short MASKBIT_WALL = CATEGORYBIT_WHEEL;
    public static final short MASKBIT_WHEEL = CATEGORYBIT_WALL|CATEGORYBIT_OBJECT;
    public static final short MASKBIT_HEAD = CATEGORYBIT_WALL|CATEGORYBIT_OBJECT;
    public static final short MASKBIT_VIRTUAL = 0;
    public static final short MASKBIT_OBJECT = CATEGORYBIT_WHEEL|CATEGORYBIT_HEAD;

    public final GameTextureManager textureManager;

    public MotodroidGameWorld(PhysicsWorld physicsWorld, MotodroidGameGraphics scene, GameTextureManager textureManager) {
        super(physicsWorld, scene);
        this.scene=scene;
        this.textureManager=textureManager;
    }

    public MotodroidGameGraphics getScene() {
        return scene;
    }


//
//    public Sprite placeSprite(Vector2i position, ITextureRegion textureRegion, Vector2i bodySize){
//        //System.out.println("placeHeight: "+textureRegion.getHeight());
//        //Sprite sprite = new Sprite(position.x-bodySize.x/2, position.y-bodySize.y/2, textureRegion, vertexBufferObjectManager);
//        //sprite.setSize(bodySize.x,bodySize.y);
//        //scene.attachChild(sprite);
//        return null;
//    }
}
