package ru.vineg.orangeBikeFree.editor;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import ru.vineg.orangeBikeFree.Motodroid;
import ru.vineg.orangeBikeFree.SimpleImage;
import ru.vineg.orangeBikeFree.SimpleInteractiveImage;
import ru.vineg.map.Vertex;
import ru.vineg.orangeBikeFree.screens.GameClickListener;
import ru.vineg.geometry.Line;
import ru.vineg.map.IVertex;
import ru.vineg.map.PolyLine;
import ru.vineg.orangeBikeFree.screens.MapEditorScreen;

/**
 * Created by Vineg on 18.02.14.
 */
public class GraphicVertex extends SimpleInteractiveImage implements IVertex {
    private final SimpleImage edgeImage = new SimpleImage(Motodroid.textureManager.mapEdge1);


    public GraphicVertex previous;
    public GraphicVertex next;
    private final MapEditorScreen stage;
    private final GraphicVertexMap graphicMap;
    private final PolyLine collection;



    public GraphicVertex(final GraphicVertexMap graphicMap, PolyLine collection, IVertex position) {
        super(Motodroid.textureManager.circle,new Vector2(40,40), position);
        setPosition(getPosition().mulAdd(new Vector2(0,0),-1));
        this.stage = graphicMap.stage;
        this.graphicMap = graphicMap;
//        setOrigin(getWidth()/2,getHeight()/2);



        this.collection = collection;
    }


    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        updateEdge();
        if(next!=null){
            next.updateEdge();
        }
    }


    private void updateEdge() {
        if (previous != null) {
            Line line = new Line(previous.getPosition(),getPosition());
            edgeImage.setScale(line.len() / edgeImage.getWidth(), 2);
            edgeImage.setPosition(line.getCenter().x, line.getCenter().y);
            edgeImage.setRotation(line.getAngleDeg());
        }
    }



    public void setPrevious(GraphicVertex previous) {

        this.previous = previous;
        if(previous==null){
            edgeImage.remove();
            return;
        }
        stage.addActor(edgeImage);
        edgeImage.addListener(new GameClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Vector2 pos = new Vector2(x, y);
                System.out.println(pos);
                insert(edgeImage.localToStageCoordinates(pos));
                event.cancel();
                super.clicked(event, x, y);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                event.cancel();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        edgeImage.setZIndex(0);
        updateEdge();
    }

    private void insert(Vector2 pos) {
        GraphicVertex newVertex = this.graphicMap.create(collection,new Vertex(pos));
        graphicMap.link(previous,newVertex);
        graphicMap.link(newVertex,this);
        collection.addBefore(newVertex,this);
    }

    @Override
    public void act(float delta) {
        float zoom = Math.min(stage.getCameraObject().zoom, 100f);
        this.setScale(zoom);
        edgeImage.setScaleY(zoom * 3);
        super.act(delta);
    }

    @Override
    public boolean remove() {
        graphicMap.link(previous, next);
        collection.getVertices().remove(this);
        edgeImage.remove();
        return super.remove();
    }

    public void setNext(GraphicVertex next) {
        this.next = next;
    }


}
