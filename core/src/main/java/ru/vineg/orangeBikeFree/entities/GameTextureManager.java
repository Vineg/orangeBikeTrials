package ru.vineg.orangeBikeFree.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.vineg.orangeBikeFree.AssetManager;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 17.09.13
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
public class GameTextureManager {
    //private final TextureAtlas atlas;
    public Texture grass;
    public TextureAtlas.AtlasRegion mapVertex;

    public TextureAtlas.AtlasRegion apple;
    public TextureAtlas.AtlasRegion killer;
    public TextureAtlas.AtlasRegion finish;
    private AssetManager manager;
    public TextureRegion mapEdge1;
    public TextureRegion fullBike;
    public TextureAtlas.AtlasRegion breakLine;
    public TextureAtlas.AtlasRegion circle;
//    public Texture background;
    public TextureRegion dirtRegion;
    public TextureRegion backgroundRegion;
    public TextureRegion cloud;
    public TextureRegion pipe;
    public TextureRegion pipeEnd;
    public TextureRegion pipeConnector;

    public GameTextureManager(AssetManager manager) {
        this.manager = manager;

        //atlas = manager.get("data/game.pack", TextureAtlas.class);
    }

    public void createResources() {

        TextureAtlas gamePack = manager.get("data/game.atlas", TextureAtlas.class);


        this.mapVertex = gamePack.findRegion("circle");
        this.apple = gamePack.findRegion("coin");
        this.killer = gamePack.findRegion("killer");
        this.finish = gamePack.findRegion("finish");
        this.mapEdge1 = gamePack.findRegion("square");
        this.circle = gamePack.findRegion("circle1");
        this.fullBike = gamePack.findRegion("bike-full");
        this.breakLine = gamePack.findRegion("breakLine");
        this.cloud=gamePack.findRegion("cloud");
        this.pipe=gamePack.findRegion("pipe");
        this.pipeEnd=gamePack.findRegion("pipe-end");
        this.pipeConnector = gamePack.findRegion("pipe-connector");
//        this.grass = gamePack.findRegion("grass");

        Texture background = manager.getTexture("data/bg.png");
        Texture dirt = manager.getTexture("dirt.png");

        grass = manager.getTexture("data/grass.png");
        grass.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
//        pipe = manager.getTexture("data/pipe.png");
//        pipe.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
//        this.grass=new TextureRegion(grass);

        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        background.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        backgroundRegion=new TextureRegion(background);

        dirt.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        dirt.setFilter(Texture.TextureFilter.MipMapLinearLinear, Texture.TextureFilter.MipMapLinearLinear);
//        backgroundRegion=gamePack.findRegion("bg");
//        dirtRegion = gamePack.findRegion("dirt");
        dirtRegion=new TextureRegion(dirt);
        //this.onScreenControlBaseTexture = getTexture("onscreen_control_base.png", 128);
        //this.onScreenControlKnobTexture = getTexture("onscreen_control_knob.png", 128);
    }

//    private Texture getTexture(String path,int bodySize){
//        return getTexture(path, bodySize,bodySize);
//    }

    private Texture getTexture(String path) {
        return manager.getTexture(path);
    }

    public void updateQuality() {
        createResources();
    }

//    private Texture getGameTexture(String name){
//         return atlas.findRegion(name).getTexture();
//    }

}
