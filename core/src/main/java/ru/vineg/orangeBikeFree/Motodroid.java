package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.gleed.Level;
import com.badlogic.gdx.gleed.LevelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.matsemann.libgdxloadingscreen.screen.LoadingScreen;
import ru.vineg.graphics.Graphics;
import ru.vineg.orangeBikeFree.entities.GameTextureManager;
import ru.vineg.orangeBikeFree.backend.MessageDialog;
import ru.vineg.orangeBikeFree.backend.MotodroidBackendApi;
import ru.vineg.orangeBikeFree.reflection.LevelEntry;
import ru.vineg.orangeBikeFree.reflection.LevelReplay;
import ru.vineg.orangeBikeFree.screens.*;

import ru.vineg.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;


public class Motodroid extends Game {
    public static GameTextureManager textureManager;
    public static Skin skin;
    public static AssetManager manager;
    public static GamePreferences gamePreferences;
    public static Stage popupStage;
    public static SpriteBatch batch;
    public static SpriteCache spriteCache;
    public static Motodroid game;
    public static StaticPolygonSpriteBatch polygonSpriteBatch;
    public static MainMenu mainMenuScreen;
    public static Kryo kryo;
    public static byte[] bikeData;
    private static boolean debug;
    private static LevelSource delayedLevel;
    private static LevelReplay delayedReplay;
    private static int engineVersion = 1;
    private static MessageDialog dialog;
    private static Screen currentScreen;
    public LevelsSource levels;
    private LevelsSource levelsSource;
    private Runnable onReady;
    private MapEditorScreen editorScreen;
    private boolean texturesLoaded = false;
    private ArrayList<Texture> textures = new ArrayList<Texture>();
    private boolean ready;

    public Motodroid(GamePreferences preferences) {
        gamePreferences = preferences;
        this.debug = preferences.isDebugging();
        manager = new AssetManager();
    }


    public Motodroid() {
        this(new GamePreferences() {
        });
    }

    public static int getEngineVersion() {
        return engineVersion;
    }

    public static void setEditorScreen(EditableLevel level) {
        game.setScreen(new MapEditorScreen(level, game.skin, game));
    }

    public static LevelScreen setLevel(LevelSource level) {
        return setLevel(level, mainMenuScreen.getMyLevelsStage());
    }

    public static LevelScreen setLevel(LevelSource level, LevelsScreen levelsScreen) {
        delayedLevel = null;
        LevelScreen levelScreen = new LevelScreen(Motodroid.game, skin, level, levelsScreen);
        Motodroid.game.setScreen(levelScreen);
//        System.gc();
        return levelScreen;
    }

    public static boolean isDebugging() {
        return debug;  //To change body of created methods use File | Settings | File Templates.
    }

    public static Preferences getPreferences() {
        return Gdx.app.getPreferences("motodroid");
    }

    public static Dialog message(String s) {
        removeDialog();
        dialog = new MessageDialog("", skin, "dialog");
        dialog.setY(popupStage.getHeight() / 3f);
        dialog.add(" " + s + " ");
        dialog.show(popupStage);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (dialog != null) {
                    dialog.hide();
                    dialog.remove();
                }
                dialog = null;
            }
        }, 2);
        return dialog;
    }

    public static void setLevelSync(LevelEntry level) {
        delayedLevel = new LevelSource(level);
    }

    public static void setReplaySync(LevelReplay replay) {
        delayedReplay = replay;
    }

    public static Dialog loadingDialog(String message) {
        removeDialog();
        dialog = new MessageDialog("", Motodroid.skin, "dialog-dim");

        dialog.add(Motodroid.manager.getLoadingAnimation()).size(20);
        dialog.add(message);
        dialog.show(Motodroid.popupStage);

        return dialog;
//        removeDialog();
//        return Motodroid.message(message);
    }

    public static void removeDialog() {
        if (dialog != null) {
            dialog.remove();
            dialog = null;
        }
    }

    public void setEditorScreen() {
        if (editorScreen == null) {
            editorScreen = new MapEditorScreen(skin, this);
        }
        setScreen(editorScreen);
    }

    @Override
    public void create() {
        kryo = new Kryo();
        registerSerializers();
        Motodroid.game = this;
        Gdx.graphics.setVSync(true);
        Graphics.refresh();
        Texture.setAssetManager(manager);
        loadTextures();
        Gdx.gl.glEnable(GL20.GL_CULL_FACE);
        Gdx.gl.glCullFace(GL20.GL_FRONT);

//        MotodroidBackendApi.submitReplay("test");


    }

    private void loadTextures() {
        if (texturesLoaded) {
            return;
        }
        texturesLoaded = true;
        getLevelsSource();
        manager.load("loading.png", Texture.class);
        manager.load("packed/buttons.atlas", TextureAtlas.class);

//        manager.load("sound/bike_sound1.ogg", Sound.class);

        manager.load("data1/uiskin.json", Skin.class);

        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
//        param.genMipMaps = true;
        param.magFilter = Texture.TextureFilter.Linear;
        param.minFilter = Texture.TextureFilter.Linear;
        manager.load("data/bg.png", Texture.class, param);
        manager.load("data/small-bg.png", Texture.class, param);
        manager.load("data/game.atlas", TextureAtlas.class);


        loadPixmap("data/grass.png", true);
        loadPixmap("data/pipe.png", true);
        loadPixmap("dirt.png", false);

        // Set loader in the Asset manager
        manager.setLoader(Level.class, new LevelLoader(new InternalFileHandleResolver()));


        // Tell the manager to load the level
        manager.load("data/bike2.xml", Level.class);

//        if (gamePreferences.isDebugging()) {
//            manager.finishLoading();
//            onReady();
//        } else {
        finishLoad();
//        }


    }

//    public void restart() {
//        getScreen().dispose();
//        levelScreen=new LevelScreen(this, level);
//        setScreen(levelScreen);
//    }

    private void finishLoad() {
        if (!manager.update()) {
            setScreen(new LoadingScreen(this));
        }
    }

    private void loadPixmap(String path, boolean smooth) {
        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
        if (smooth) {
            param.minFilter = Texture.TextureFilter.Linear;
            param.magFilter = Texture.TextureFilter.Linear;
        } else {
            param.minFilter = Texture.TextureFilter.Nearest;
            param.magFilter = Texture.TextureFilter.Nearest;
        }
        param.genMipMaps = true;
        manager.load(path, Texture.class, param);
    }

    private void loadPixmap(String path) {
        loadPixmap(path, false);
    }

    @Override
    public void render() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        if (isReady() && (Math.max(width, height) != Graphics.getScreenWidth() || Math.min(width, height) != Graphics.getScreenHeight())) {
            start();
        }

        if (delayedLevel != null) {
            setLevel(delayedLevel);
        } else if (delayedReplay != null) {
            setReplay(delayedReplay);
        }

        //stage with notifications


        super.render();    //To change body of overridden methods use File | Settings | File Templates.


        if (popupStage != null && dialog != null) {
            popupStage.draw();
            popupStage.act();
        }
    }

    private void setReplay(LevelReplay replay) {
        levelReplay(replay);
    }

    public void onReady() {
        super.pause();//invalidate caches
        spriteCache = new SpriteCache(10000, false);
        batch = new SpriteBatch(2000);
        polygonSpriteBatch = new StaticPolygonSpriteBatch();
        polygonSpriteBatch.disableBlending();
        Motodroid.textureManager = new GameTextureManager(manager);
        textureManager.createResources();


        this.levels = getLevelsSource();
        skin = manager.get("data1/uiskin.json", Skin.class);
//        Gdx.graphics.setContinuousRendering(false);
//        Gdx.graphics.requestRendering();

        Output out = new Output(1000, 100000);
        kryo.writeObject(out, manager.get("data/bike2.xml", Level.class));
        bikeData = out.getBuffer();
        ready = true;
        start();




//        setEditorScreen();
    }

    private void start() {
        popupStage = new UIStage();
        Graphics.refresh();
        if (currentScreen == null || currentScreen instanceof MainMenu) {
            mainMenuScreen = new MainMenu(this, skin);
            setMenuScreen();
        } else {
            setScreen(currentScreen);
        }
        if(onReady!=null){
            onReady.run();
        }
        super.resume();

//        MotodroidBackendApi.setLevelByUri("http://192.168.0.5/api/level/72");
//        MotodroidBackendApi.setReplayByUri("http://localhost/api/replay/23");
    }

    public LevelsSource getLevelsSource() {
        if (GamePreferences.isUpdateLevelsList() || levelsSource == null) {
            FileHandle[] levelsList = new FileHandle[0];
            if (!(Gdx.app.getType() == Application.ApplicationType.WebGL || Gdx.app.getType() == Application.ApplicationType.Applet)) {
                FileHandle levelsDir = DirectoryStructure.getLevelsDir();
                levelsList = levelsDir.list();
            }

            if (levelsList.length == 0) {
                Json json = new Json();
                FileHandle handle = Gdx.files.internal("levelNames.json");
                String[] names = json.fromJson(String[].class, handle.reader());
                levelsList = new FileHandle[names.length];
                for (int i = 0; i < names.length; i++) {
                    levelsList[i] = DirectoryStructure.getLevelsDir().child(names[i]);
                }
            }

            levelsSource = new LevelsSource(levelsList);
        }
        return levelsSource;
    }

    public LevelsSource getMyLevelsSource() throws AccessDeniedException {
        FileHandle levelsDir;
        levelsDir = DirectoryStructure.getMyLevelsDir();
        FileHandle[] levelsList = levelsDir.list();
        LevelsSource myLevelsSource = new LevelsSource(levelsList, true);
        return myLevelsSource;
    }

    public void level(int level) {
        setLevel(levels.get(level));
    }

    public void setMenuScreen() {

//        mainMenuScreen.updateList();
//        mainMenuScreen.mainMenu();
        setScreen(mainMenuScreen);
    }

    public void setLevelFinishScreen(LevelsScreen levels, LevelReplay recordedControls) {
        setLevelFinishScreen(levels, recordedControls, new ArrayList<TextButtonData>());
    }

    public void setLevelFinishScreen(LevelsScreen levels, LevelReplay recordedControls, List<TextButtonData> additionalPauseButtons) {
        mainMenuScreen.levelFinished(levels, recordedControls, additionalPauseButtons);
        setScreen(mainMenuScreen);
    }

    public void levelReplay(LevelReplay replay) {
        delayedReplay = null;
        setScreen(new LevelReplayScreen(this, skin, replay));
    }

    @Override
    public void pause() {
        texturesLoaded = false;
        if (mainMenuScreen != null) {
            mainMenuScreen.pause();
        }
        if (currentScreen != null) {
            currentScreen.pause();
        }
        if (editorScreen != null) {
            editorScreen.pause();
        }
        super.pause();
    }

    @Override
    public void resume() {
        //textures loaded when create method called
        if (!manager.update()) {
            finishLoad();
        } else {
            if (currentScreen != null) {
                currentScreen.resume();
            }
        }
//        super.resume();
    }

    private void reloadTextures() {
        super.setScreen(new LoadingScreen(this));
    }

    @Override
    public void setScreen(Screen screen) {
        if (!(screen instanceof LoadingScreen)) {
            currentScreen = screen;
        }
        super.setScreen(screen);    //To change body of overridden methods use File | Settings | File Templates.
//        screen.resume();
    }

    @Override
    public void resize(int width, int height) {
//        Graphics.refresh();
//        if (isReady() && (Math.max(width, height) != Graphics.getScreenWidth() || Math.min(width, height) != Graphics.getScreenHeight())) {
//            start();
//        }
//        super.resize(width, height);
    }

    public void flipFileYs() {
//        for (FileHandle file : files.list()) {
//            VertexMap<Vertex> map = mapLoader.loadFromString(file.readString());
//            for(PolyLine<Vertex> island : map.getIslands()){
//                for(Vertex vertex:island.getVertices()){
//                    vertex.getPosition().y*=-1;
//                }
//            }
//
//            for (IVertex vertex : map.getApples()) {
//                vertex.getPosition().y *= -1;
//            }
//            for (IVertex vertex : map.getKillers()) {
//                vertex.getPosition().y *= -1;
//            }
//
//            map.getStart().y*=-1;
//            map.getFinish().y*=-1;
//
//            file.writeString(map.toJson(), false);
//        }
    }

    public void setOnReady(Runnable runnable) {
        if (isReady()) {
            runnable.run();
        } else {
            onReady = runnable;
        }
    }

    private boolean isReady() {
        return ready && manager.update();
    }

    public void setUrl(String baseUrl) {
        int pos = baseUrl.indexOf('?');
        if (pos >= 0) {
            baseUrl = baseUrl.substring(0, pos);
        }
        String[] parts = baseUrl.split("/");
        UrlType folder;
        try {
            folder = UrlType.valueOf(parts[parts.length - 2]);
        } catch (IllegalArgumentException e) {
            return;
        }
        final String finalBaseUrl = baseUrl;
        switch (folder) {
            case replay:
                setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        MotodroidBackendApi.setReplayByUri(finalBaseUrl + ".json");
                    }
                });
                break;
            case level:
                setOnReady(new Runnable() {
                    @Override
                    public void run() {
                        MotodroidBackendApi.setLevelByUri(finalBaseUrl + ".json");
                    }
                });
                break;

        }
        System.out.println(baseUrl);
    }

    public void backButtonHandle() {
        Screen screen = getScreen();
        if (screen instanceof GameScreen) {
            if (((GameScreen) screen).paused) {
                ((GameScreen) screen).resumeGame();
            } else {
                ((GameScreen) screen).pauseGame();
            }
        }
    }

    private void registerSerializers() {
        kryo.register(Array.class, new Serializer<Array>() {
            private Class genericType;

            {
                setAcceptsNull(true);
            }

            public void setGenerics(Kryo kryo, Class[] generics) {
                if (kryo.isFinal(generics[0])) genericType = generics[0];
            }

            public void write(Kryo kryo, Output output, Array array) {
                int length = array.size;
                output.writeInt(length, true);
                if (length == 0) return;
                if (genericType != null) {
                    Serializer serializer = kryo.getSerializer(genericType);
                    genericType = null;
                    for (Object element : array)
                        kryo.writeObjectOrNull(output, element, serializer);
                } else {
                    for (Object element : array)
                        kryo.writeClassAndObject(output, element);
                }
            }

            public Array read(Kryo kryo, Input input, Class<Array> type) {
                Array array = new Array();
                kryo.reference(array);
                int length = input.readInt(true);
                array.ensureCapacity(length);
                if (genericType != null) {
                    Class elementClass = genericType;
                    Serializer serializer = kryo.getSerializer(genericType);
                    genericType = null;
                    for (int i = 0; i < length; i++)
                        array.add(kryo.readObjectOrNull(input, elementClass, serializer));
                } else {
                    for (int i = 0; i < length; i++)
                        array.add(kryo.readClassAndObject(input));
                }
                return array;
            }


        });

        kryo.register(IntArray.class, new Serializer<IntArray>() {
            public void write(Kryo kryo, Output output, IntArray array) {
                int length = array.size;
                output.writeInt(length, true);
                if (length == 0) return;
                for (int i = 0, n = array.size; i < n; i++)
                    output.writeInt(array.get(i), true);
            }

            {
                setAcceptsNull(true);
            }

            public IntArray read(Kryo kryo, Input input, Class<IntArray> type) {
                IntArray array = new IntArray();
                kryo.reference(array);
                int length = input.readInt(true);
                array.ensureCapacity(length);
                for (int i = 0; i < length; i++)
                    array.add(input.readInt(true));
                return array;
            }


        });

        kryo.register(FloatArray.class, new Serializer<FloatArray>() {
            public void write(Kryo kryo, Output output, FloatArray array) {
                int length = array.size;
                output.writeInt(length, true);
                if (length == 0) return;
                for (int i = 0, n = array.size; i < n; i++)
                    output.writeFloat(array.get(i));
            }

            {
                setAcceptsNull(true);
            }

            public FloatArray read(Kryo kryo, Input input, Class<FloatArray> type) {
                FloatArray array = new FloatArray();
                kryo.reference(array);
                int length = input.readInt(true);
                array.ensureCapacity(length);
                for (int i = 0; i < length; i++)
                    array.add(input.readFloat());
                return array;
            }


        });

        kryo.register(Color.class, new Serializer<Color>() {
            public Color read(Kryo kryo, Input input, Class<Color> type) {
                Color color = new Color();
                Color.rgba8888ToColor(color, input.readInt());
                return color;
            }

            public void write(Kryo kryo, Output output, Color color) {
                output.writeInt(Color.rgba8888(color));
            }
        });

        kryo.register(Texture.class, new Serializer<Texture>() {

            @Override
            public void write(Kryo kryo, Output output, Texture object) {
                int index = textures.indexOf(object);
                if (index < 0) {
                    textures.add(object);
                    index = textures.size() - 1;
                }
                output.writeInt(index);
            }

            @Override
            public Texture read(Kryo kryo, Input input, Class<Texture> type) {
                return textures.get(input.readInt());
            }
        });
    }


    private enum UrlType {
        replay, level;
    }
}
