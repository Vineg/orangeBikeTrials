package ru.vineg.orangeBikeFree.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ru.vineg.graphics.DefaultCamera;
import ru.vineg.orangeBikeFree.CameraInputController;
import ru.vineg.orangeBikeFree.DirectoryStructure;
import ru.vineg.orangeBikeFree.EditableLevel;
import ru.vineg.orangeBikeFree.LevelSource;
import ru.vineg.orangeBikeFree.editor.GraphicVertexMap;
import ru.vineg.map.Vertex;
import ru.vineg.map.VertexMapLoader;
import ru.vineg.orangeBikeFree.*;
import ru.vineg.orangeBikeFree.backend.MotodroidBackendApi;
import ru.vineg.orangeBikeFree.ui.ImageButton;

/**
 * Created by Vineg on 18.02.14.
 */
public class MapEditorScreen extends SimpleStage implements Screen {

    private GraphicVertexMap vertexMap;
    private final CameraInputController cameraInputController = new CameraInputController(getCameraObject()) {
        @Override
        protected boolean process(float deltaX, float deltaY, int button) {
            clickStart = false;
            return super.process(deltaX*2, deltaY*2, button);
        }
    };
    private final UIStage uiStage;
    private final Motodroid game;
    private InputProcessor inputMultiplexer;
    private final Skin skin;
    public VertexType vertexType = VertexType.vertex;
    private final Screen currentScreen = this;
    private FileHandle levelSource;
    private boolean clickStart;
    private boolean dragging;

    public MapEditorScreen(Skin skin, Motodroid game) {
        super(false);
        this.game = game;
        this.skin = skin;
        vertexMap = new GraphicVertexMap(this);
        uiStage = new UIStage();
        uiStage.updateViewport(true);


        cameraInputController.translateButton = Input.Buttons.LEFT;
        cameraInputController.rotateButton = -1;
        cameraInputController.translateUnits = getCameraObject().zoom;
        addListener(new GameClickListener() {

//            public Vector3 pickPoint = new Vector3();


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickStart = true;
                dragging = true;
//                pickPoint.set(x, y, 0);
                super.touchDown(event, x, y, pointer, button);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dragging = false;
                if (!clickStart) {
                    return;
                }

                Vector2 pos = new Vector2(x, y);
//                getCamera().unproject(pos);
                addVertex(pos);
                super.touchUp(event, x, y, pointer, button);
//                return false;c
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                mouseMoved(null, x, y);
//                return false;
            }

        });

        inputMultiplexer = new InputMultiplexer(uiStage, this, cameraInputController);

//        addListener(new DragListener() {
//            @Override
//            public void drag(InputEvent event, float x, float y, int pointer) {
//                if(event.isStopped()){return;}
//                getCamera().position.add(-x + getTouchDownX(), -y + getTouchDownY(), 0);
//                super.drag(event, x, y, pointer);
//            }
//        });

        createUI();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        if (dragging) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        super.touchDragged(screenX, screenY, pointer);
        if (dragging) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        return false;
    }

    public MapEditorScreen(EditableLevel level, Skin skin, Motodroid motodroid) {
        this(skin, motodroid);
        loadLevel(level);
    }

    private void loadLevel(EditableLevel level) {
        VertexMapLoader loader = new VertexMapLoader();
        levelSource = level.getSource();
        loader.loadFromString(levelSource.readString(), vertexMap);
    }

    private void createUI() {
        Table container = new Table();
//        container.debug();
        container.setWidth(uiStage.getWidthScaled());
        container.setHeight(uiStage.getHeightScaled());

        Table rightTop = new Table();
        Table leftTop = new Table();

        Button toMenu = new TextButton("menu", skin);
        Button reset = new TextButton("new level", skin);

        Button endLineButton = ImageButton.fromTexture(Motodroid.textureManager.breakLine);
        final TextButton deleteButton = new TextButton(" remove ", skin, "toggle");

        final Button runButton = new TextButton("run", skin);
        TextButton saveButton = null;
        try {
            DirectoryStructure.getMyLevelsDir();
            saveButton = new TextButton("save", skin);
        } catch (Exception e) {

        }
        final TextButton shareButton = new TextButton("share", skin);
        final Button vertexButton = ImageButton.fromTexture(Motodroid.textureManager.mapVertex);
        final Button appleButton = ImageButton.fromTexture(Motodroid.textureManager.apple);
        final Button killerButton = ImageButton.fromTexture(Motodroid.textureManager.killer);
        final Button startButton = ImageButton.fromTexture(Motodroid.textureManager.fullBike);
        final Button finishButton = ImageButton.fromTexture(Motodroid.textureManager.finish);


        final TextButtonData backToEditor = new TextButtonData("  to editor  ");

        ButtonGroup vertexType = new ButtonGroup(vertexButton, appleButton, killerButton);

//        container.debug();


        container.defaults().pad(5).right().top().expandX();

        leftTop.defaults().pad(5);
        leftTop.add(toMenu);
        leftTop.row();
        leftTop.add(reset);

        container.add(leftTop).left().top();


        rightTop.defaults().pad(7).right().top().expandX().height(32).minWidth(40);

        rightTop.add(shareButton).row();



        if (saveButton != null) {

            rightTop.add(saveButton).row();

            saveButton.addListener(new GameClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    FileHandle outputDir = null;
                    try {
                        outputDir = DirectoryStructure.getMyLevelsDir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FileHandle[] files = outputDir.list();
                    int lastIndex;
                    if (files.length > 0) {
                        lastIndex = Numbers.getInt(files[files.length - 1].nameWithoutExtension());
                    } else {
                        lastIndex = 0;
                    }
                    lastIndex++;
                    if (levelSource == null) {
                        levelSource = outputDir.child(lastIndex + ".json");
                    }
                    levelSource.writeString(vertexMap.toJson(), false);
                    super.clicked(event, x, y);
                }
            });
        }

        rightTop.add(runButton).row();

        rightTop.defaults().width(40);

        rightTop.add(vertexButton).row();
        rightTop.add(appleButton).row();
        rightTop.add(killerButton).row();
//        rightTop.add(startButton).row();
//        rightTop.add(finishButton).row();

        container.add(rightTop).expand().row();

        container.defaults().left().bottom().height(35);

        container.add(endLineButton).expandX().width(40);
        container.row();
        container.add(deleteButton);

        reset.addListener(new GameClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MapEditorScreen.this.getRoot().clearChildren();
                vertexMap=new GraphicVertexMap(MapEditorScreen.this);
                levelSource=null;
                super.clicked(event, x, y);
            }
        });

        toMenu.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setMenuScreen();
                super.clicked(event, x, y);
            }
        });



        shareButton.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                MotodroidBackendApi.submitLevel(vertexMap.toJson());
            }
        });

        backToEditor.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(currentScreen);
                super.clicked(event, x, y);
            }
        });


        runButton.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelScreen levelScreen = game.setLevel(new LevelSource(((ru.vineg.map.VertexMap) vertexMap.clone()).prepare()));
                levelScreen.setTesting(true);
                levelScreen.addPauseButton(backToEditor);
                super.clicked(event, x, y);
            }
        });

        vertexButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (vertexButton.isChecked()) {
                    setVertexType(VertexType.vertex);
                }
            }
        });

        killerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (killerButton.isChecked()) {
                    setVertexType(VertexType.killer);
                }
            }
        });

        appleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (appleButton.isChecked()) {
                    setVertexType(VertexType.apple);
                }
            }
        });

        endLineButton.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                vertexMap.endLine();
                super.clicked(event, x, y);
            }
        });

        deleteButton.addListener(new GameClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (deleteButton.isChecked()) {
                    ru.vineg.orangeBikeFree.input.Input.shiftPressed = true;
                } else {
                    ru.vineg.orangeBikeFree.input.Input.shiftPressed = false;
                }
                event.handle();
                super.clicked(event, x, y);
            }
        });

        finishButton.addListener(new GameClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DefaultCamera cameraObject = getCameraObject();
                vertexMap.setFinish(new Vertex(cameraObject.getX(),cameraObject.getY()));
                super.clicked(event, x, y);
            }
        });

        startButton.addListener(new GameClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DefaultCamera cameraObject = getCameraObject();
                vertexMap.setStart(new Vertex(cameraObject.getX(),cameraObject.getY()));
                super.clicked(event, x, y);
            }
        });

//        container.setPosition(-container.getWidth()/2,-container.getHeight()/2);
        uiStage.addActor(container);
    }


    @Override
    public void render(float delta) {
        act();
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cameraInputController.update();
        draw();
        uiStage.draw();
//        Table.drawDebug(popupStage);
    }


    private void addVertex(Vector2 pos) {
        switch (vertexType) {
            case apple:
                vertexMap.addApple(new Vertex(pos));
                break;
            case killer:
                vertexMap.addKiller(new Vertex(pos));
                break;
            case vertex:
                vertexMap.addVertex(new Vertex(pos));
                break;
        }
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public void setVertexType(VertexType vertexType) {
        this.vertexType = vertexType;
    }

    public void toJson() {

    }
}
