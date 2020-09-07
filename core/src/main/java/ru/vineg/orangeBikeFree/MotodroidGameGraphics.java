package ru.vineg.orangeBikeFree;

import box2d.ImageObject2dWrapper;
import com.badlogic.gdx.gleed.LevelRenderer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.vineg.geom.Rectangle;
import ru.vineg.graphics.DefaultCamera;
import ru.vineg.graphics.GameGraphics;
import ru.vineg.graphics.SimpleSprite;
import ru.vineg.geometry.Line;
import ru.vineg.map.*;
import ru.vineg.structure.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vineg
 * Date: 10/4/13
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class MotodroidGameGraphics extends GameGraphics implements IScene {


    private float dt = 0;
    int textureCount = 0;

    private SimpleSprite cloud = new SimpleSprite(Motodroid.textureManager.cloud);

    private ArrayList<StaticPolygonSprite> polygonSprites = new ArrayList<>();
    int cacheId=-1;
    public ArrayList<LevelRenderer> levelRenderers=new ArrayList<>(10);

    
    
    private QuadtreeCallback pCallback = new QuadtreeCallback() {
        @Override
        public void reportQuadtreeElement(QuadtreeElement userData) {
            StaticSceneObject sceneObject = (StaticSceneObject) (((QuadtreeVectorElement) userData).data);
            if(sceneObject.exists()) {
                Sprite sprite = ((ImageObject2dWrapper)sceneObject.getSceneObject()).object;
                addStaticSprite(sprite);
                sceneObject.onUpdate(dt);
            }
        }
    };





    public MotodroidGameGraphics(int width, int height, SpriteBatch batch) {
        super(batch);
    }

    public void preRender(){
        Motodroid.spriteCache.beginCache();
    }

    public void finalizePreRender(){
        cacheId = Motodroid.spriteCache.endCache();
    }



    public void draw() {

//        super.draw();
        if (getCameraObject() == null) {
            return;
        }


//        polyBatch.setProjectionMatrix(getCamera().combined);
//        polyBatch.begin();
//        synchronized (lock) {
//            updateSpriteList();
//        }
//        polyBatch.end();


        drawBackground(spriteBatch);

        super.draw();

        for (int i = 0; i < levelRenderers.size(); i++) {
            levelRenderers.get(i).render(spriteBatch,camera);
        }

        updateSpriteList();




    }

    private void updateSpriteList() {
        camera.copyBounds(spriteBounds, GameVars.maxLineLength);
        staticSpriteList.clear();
        spaceTree.query(spriteBounds, pCallback);
    }


//    @Override
//    public void reset() {
//        game.restart();
//    }



    public void addGrass(Line line) {
//        spaceTree.insert(line);
        if (cacheId != -1) {
            return;
        }
        Texture edgeTexture = Motodroid.textureManager.grass;
        int height = edgeTexture.getHeight();
        Motodroid.spriteCache.add(edgeTexture, line.p1.x + height / 2 * line.sin(), line.p1.y - height / 2 * line.cos(), 0, 0, line.len(), height, 1, 1, line.getAngleDeg(), 0, 0, (int) line.len(), height, false, false);
    }

    public void addTube(Line line) {
        if (cacheId != -1) {
            return;
        }
        TextureRegion pipeTexture = Motodroid.textureManager.pipe;
        int height = pipeTexture.getRegionHeight();
        Motodroid.spriteCache.add(pipeTexture, line.p1.x + height / 2 * line.sin(), line.p1.y - height / 2 * line.cos(), 0, 0, line.len(), height, 1, 1, line.getAngleDeg());
    }

    public void pipeEnd(Line line, boolean end) {
        if (cacheId != -1) {
            return;
        }
        TextureRegion pipeEndTexture = Motodroid.textureManager.pipeEnd;
        pipeEndTexture.flip(pipeEndTexture.isFlipX() != !end, false);
        new TextureRegionDrawable(pipeEndTexture);
        int height = pipeEndTexture.getRegionHeight();
        int width = pipeEndTexture.getRegionWidth();
        Vector2 point = !end ? line.p1 : line.p2;
        Motodroid.spriteCache.add(pipeEndTexture, point.x - width / 2, point.y - height / 2, width / 2, height / 2, width, height, 1, 1, line.getAngleDeg());
    }

    public void pipeConnector(Vector2 vertex, float angle) {
        if (cacheId != -1) {
            return;
        }
        TextureRegion texture = Motodroid.textureManager.pipeConnector;
        int width = texture.getRegionWidth();
        int height = texture.getRegionHeight();
        Motodroid.spriteCache.add(texture, vertex.x - width / 2, vertex.y - height / 2, width / 2, height / 2, width, height, 1, 1, angle);

    }


    public void addGrassConnector(VertexElement vertex) {
//        spaceTree.insert(vector2);
        if (cacheId != -1) {
            return;
        }
        TextureRegion texture = Motodroid.textureManager.mapVertex;
        int width = texture.getRegionWidth();
        Motodroid.spriteCache.add(texture, vertex.getX() - width / 2, vertex.getY() - width / 2, width, width);
    }

    public void addPolygon(List<IVertex> vertices) {
        float[] verticesAr = new float[vertices.size() * 2];
        for (int i = 0; i < vertices.size(); i++) {
            verticesAr[2 * i] = vertices.get(i).getPosition().x;
            verticesAr[2 * i + 1] = vertices.get(i).getPosition().y;
        }
        polygonSprites.add(new StaticPolygonSprite(new PolygonRegion(Motodroid.textureManager.dirtRegion, verticesAr, new EarClippingTriangulator().computeTriangles(verticesAr).toArray())));
    }

    public void drawMap() {
        Motodroid.polygonSpriteBatch.setProjectionMatrix(camera.combined);
        Motodroid.polygonSpriteBatch.begin();
        for (int i = 0; i < polygonSprites.size(); i++) {
            polygonSprites.get(i).draw(Motodroid.polygonSpriteBatch);
//            polygonSpriteBatch.flush();
        }
        Motodroid.polygonSpriteBatch.end();
    }


    private DefaultCamera backgroundCamera = new DefaultCamera();

    //    double[] rand=new double[100];
    Rectangle[] clouds = new Rectangle[4];

    private void drawBackground(Batch batch) {

        backgroundCamera.setPosition(camera.getPosition().scl(1f/10));
        backgroundCamera.update();
        int screenWidth = (int) backgroundCamera.viewportWidth;
        int screenHeight = (int) backgroundCamera.viewportHeight;
        int cloudWidth = cloud.getRegionWidth();
        int cloudHeight = cloud.getRegionHeight();
        batch.setProjectionMatrix(backgroundCamera.combined);
        int cloudDistanceX = cloudWidth;
        int cloudDistanceY = screenHeight + cloudHeight;
        Vector2 bgPosition = backgroundCamera.getPosition();
        float leftX = bgPosition.x - screenWidth / 2;
        float bottomY = bgPosition.y - screenHeight / 2;
        float rightX = leftX + screenWidth;

        if (clouds[0] == null) {
            for (int i = 0; i < clouds.length; i++) {
                clouds[i] = new Rectangle((int) ((leftX - cloudWidth + Math.random() * (screenWidth + cloudWidth))), (int) (bottomY - cloudHeight + Math.random() * (screenHeight + cloudHeight)), cloud.getRegionWidth(), cloud.getRegionHeight());
            }
        }

        for (int i = 0; i < clouds.length; i++) {
            Rectangle cloudPos = clouds[i];
            cloud.setPosition(cloudPos.x, cloudPos.y);
            if (i % 2 == 0) {
                cloud.flip(true, false);
            }
            cloud.draw(batch);
            if (!backgroundCamera.isInCamera(cloud)) {
                if (cloudPos.getLeft() > backgroundCamera.getRight()) {
                    cloudPos.x = (int) (backgroundCamera.getLeft() - cloudWidth);
                    cloudPos.y = (int) (bottomY - cloudHeight + Math.random() * (screenHeight + cloudHeight));
                }
                if (cloudPos.getBottom() > backgroundCamera.getTop()) {
                    cloudPos.y = (int) (backgroundCamera.getBottom() - cloudHeight);
                    cloudPos.x = (int) (leftX - cloudWidth + Math.random() * (screenWidth + cloudWidth));
                }

                if (cloudPos.getRight() < backgroundCamera.getLeft()) {
                    cloudPos.x = (int) (backgroundCamera.getRight());
                    cloudPos.y = (int) (bottomY - cloudHeight + Math.random() * (screenHeight + cloudHeight));
                }
                if (cloudPos.getTop() < backgroundCamera.getBottom()) {
                    cloudPos.y = (int) (backgroundCamera.getTop());
                    cloudPos.x = (int) (leftX - cloudWidth + Math.random() * (screenWidth + cloudWidth));
                }


            }
        }
    }

    public void draw(VertexMap vertexMap){
        for (Object island : vertexMap.map) {
            ArrayList vertices = ((PolyLine) island).getVertices();
            if(vertices.size()<2){continue;}
            drawGround(vertices);
        }
    }

    public void drawGround(ArrayList<IVertex> vertices) {
        int verticesCount = vertices.size();
        if (vertices.get(0).getPosition().dst(vertices.get(verticesCount - 1).getPosition()) == 0) {
            addPolygon((List<IVertex>) vertices);
            boolean drawingGrass = false;
            for (int i = 0; i < verticesCount; i++) {
                int nextVertex = (i + 1)%(verticesCount-1);
                Line line = new Line(vertices.get(i).getPosition(), vertices.get(nextVertex).getPosition());
                if (line.getAngleDeg() < 45 || line.getAngleDeg() > 315) {
                    addGrass(line);
                    addGrassConnector(new VertexElement(vertices.get(i)));
                    if (i == verticesCount - 1) {
                        addGrassConnector(new VertexElement(vertices.get(nextVertex)));
                    }
                    drawingGrass = true;
                } else {
                    if (drawingGrass) {
                        addGrassConnector(new VertexElement(vertices.get(i)));
                    }
                    drawingGrass = false;
                }
            }
        } else {
            Line previousLine = null;
            for (int i = 0; i < verticesCount - 1; i++) {
                Line line = new Line(vertices.get(i).getPosition(), vertices.get(i + 1).getPosition());
                addTube(line);
                if (i == 0) {
                    pipeEnd(line, false);
                } else {
                    float connectorAngle = (line.getAngleDeg() + previousLine.getAngleDeg() + 180) / 2;
                    pipeConnector(line.p1, (connectorAngle - line.getAngleDeg() + 360) % 360 < 180 ? connectorAngle : (connectorAngle - 180));
                }
                if (i == verticesCount - 2) {
                    pipeEnd(line, true);
                }
                previousLine = line;
            }
        }
    }

    @Override
    public void reset() {
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = null;
        }
        super.reset();
    }


    //    public void setDefaultCamera(DefaultCamera camera) {
//        super.setCamera(camera);    //To change body of overridden methods use File | Settings | File Templates.
//        cameraWrapper = camera;
//    }

}
