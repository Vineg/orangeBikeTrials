package ru.vineg.orangeBikeFree.editor;

import com.badlogic.gdx.math.Vector2;
import ru.vineg.orangeBikeFree.Motodroid;
import ru.vineg.orangeBikeFree.SimpleInteractiveImage;
import ru.vineg.map.IVertex;
import ru.vineg.map.PolyLine;
import ru.vineg.map.VertexMap;
import ru.vineg.orangeBikeFree.screens.MapEditorScreen;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;

/**
 * Created by Vineg on 18.02.14.
 */
public class GraphicVertexMap extends VertexMap<GraphicVertex> {

    final MapEditorScreen stage;

    public GraphicVertexMap(MapEditorScreen graphics) {
        this.stage = graphics;
        addIsland(new PolyLine());

        addStart();
        addFinish();

    }



    private void addStart() {
        SimpleInteractiveImage start = new SimpleInteractiveImage(Motodroid.textureManager.fullBike, this.start);
        start.isRemovable=false;
        this.start=start;
        stage.addActor(start);
    }

    private void addFinish(){
        SimpleInteractiveImage finish = new SimpleInteractiveImage(Motodroid.textureManager.finish,new Vector2(80,80),this.finish);
        finish.isRemovable=false;
        this.finish=finish;
        stage.addActor(finish);
    }

    public void addVertex(IVertex vert) {
        PolyLine lastIsland = getLastIsland();
        ArrayList<GraphicVertex> vertices = lastIsland.getVertices();

        final GraphicVertex graphicVertex = create(lastIsland, vert);

        int verticesCnt = vertices.size();
        if (verticesCnt > 0) {
            GraphicVertex previous = vertices.get(verticesCnt - 1);
            link(previous, graphicVertex);
        }
        lastIsland.addVertex(graphicVertex);
    }


    public void addApple(IVertex pos){
        SimpleInteractiveImage apple = new SimpleInteractiveImage(Motodroid.textureManager.apple,new Vector2(40,40),pos){
            @Override
            public boolean remove() {
                apples.remove(this);
                return super.remove();
            }
        };
        stage.addActor(apple);
        apples.add(apple);
    }


    public void addKiller(IVertex pos){
        SimpleInteractiveImage killer = new SimpleInteractiveImage(Motodroid.textureManager.killer,new Vector2(30,30),pos){
            @Override
            public boolean remove() {
                killers.remove(this);
                return super.remove();
            }
        };
        stage.addActor(killer);
        killers.add(killer);
    }


    public GraphicVertex create(PolyLine collection, IVertex pos) {
        final GraphicVertex graphicVertex = new GraphicVertex(this, collection, pos);
        stage.addActor(graphicVertex);

        return graphicVertex;
    }

    void link(GraphicVertex previous, GraphicVertex next) {
        if (previous != null) {
            previous.setNext(next);
        }
        if (next != null) {
            next.setPrevious(previous);
        }
    }


}
