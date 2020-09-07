package ru.vineg.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import ru.vineg.orangeBikeFree.MotodroidGameWorld;
import ru.vineg.orangeBikeFree.motorbike.Apple;
import ru.vineg.orangeBikeFree.motorbike.Killer;
import ru.vineg.structure.layers.IObject2d;
import ru.vineg.structure.layers.LayerObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 15.09.13
 * Time: 17:28
 * To change this template use File | Settings | File Templates.
 */
public class VertexMap<T extends IVertex> extends LayerObject implements Json.Serializable {
    public final ArrayList<PolyLine<T>> map;

    protected IVertex start = new Vertex(0, 0);
    protected IVertex finish = new Vertex(0, 0);

    protected final List<IVertex> killers = new ArrayList<IVertex>();
    protected final List<IVertex> apples = new ArrayList<IVertex>();
    private final List<Apple> appleObjects = new ArrayList<Apple>();
    private final List<Killer> killerObjects = new ArrayList<Killer>();

    @Override
    public Object clone() {
        VertexMap<T> res = new VertexMap<T>();
        res.setStart(start);
        res.setFinish(finish);
        res.setApples(apples);
        res.setKillers(killers);
        ArrayList<PolyLine<T>> newMap = new ArrayList<PolyLine<T>>();
        for(PolyLine<T> p :map){
            newMap.add((PolyLine<T>)p.clone());
        }
        res.setMap(newMap);
        return res;
    }

    public VertexMap(ArrayList<PolyLine<T>> islands) {
        this.map = islands;
    }

    public VertexMap() {
        this(new ArrayList<PolyLine<T>>());
        addChild(start);
        addChild(finish);
        addChild(map);
        addVertexChild(apples);
        addVertexChild(killers);
    }

    private void addChild(IVertex start) {
        super.addChild(new VertexObject(start));
    }

    private void addVertexChild(List<IVertex> list) {
        addChild(new VertexListObject(list));
    }

    private void addChild(List<? extends IObject2d> list) {
        addChild(new ListObject(list));
    }


    public void placeOnScene(MotodroidGameWorld gameWorld) {
        for (PolyLine island : map) {
            island.addPhysics(gameWorld);
        }

        for (IVertex apple : apples) {
            appleObjects.add(Apple.createApple(gameWorld, apple.getPosition()));
        }

        for (IVertex killer : killers) {
            killerObjects.add(Killer.createKiller(gameWorld, killer.getPosition()));
        }
    }


    public void addIsland(PolyLine<IVertex> island) {
        endLine();
        checkVertices(island.getVertices());
        for (IVertex vertex : island.getVertices()) {
            addVertex(vertex);
        }

    }

    public void addVertex(IVertex vertex) {
        getLastIsland().addVertex(vertex);
    }

    private void checkVertices(ArrayList<IVertex> vertices) {
        int verticesCnt = vertices.size();
        if (verticesCnt == 0) {
            return;
        }
        for (int i = 0; i < verticesCnt - 1; i++) {
            IVertex nextVertex = vertices.get(i + 1);
            IVertex currentVertex = vertices.get(i);
            float dst = currentVertex.getPosition().dst(nextVertex.getPosition());
            if (dst < 0.1) {
                vertices.remove(i);
                checkVertices(vertices);
                return;
            } else if (dst > GameVars.maxLineLength) {
                Vertex vertex = new Vertex();
                vertex.getPosition().set(currentVertex.getPosition()).add(nextVertex.getPosition()).scl(1 / 2f);
                vertices.add(i + 1, vertex);
                verticesCnt++;
                i--;
            }
        }
        Vector2 firstPos = vertices.get(0).getPosition();
        Vector2 lastPos = vertices.get(verticesCnt - 1).getPosition();
        if (firstPos.dst(lastPos) < GameVars.minDistCloseChain) {
            lastPos.set(firstPos);
            if(!areVerticesClockwise(vertices)){
                for(int i = 0; i < vertices.size()/2; i++)
                {
                    IVertex temp = vertices.get(i);
                    vertices.set(i,vertices.get(vertices.size() - i - 1));
                    vertices.set(vertices.size() - i - 1, temp);
                }
            }
        }else{
            if(firstPos.x-firstPos.y> lastPos.x-lastPos.y){
                for(int i = 0; i < vertices.size()/2; i++)
                {
                    IVertex temp = vertices.get(i);
                    vertices.set(i,vertices.get(vertices.size() - i - 1));
                    vertices.set(vertices.size() - i - 1, temp);
                }
            }
        }


    }

    public List<PolyLine<T>> getIslands() {
        return map;
    }


    public Vector2 getStart() {
        return start.getPosition().cpy();
    }

    public void setStart(IVertex start) {
        this.start.setPosition(start.getPosition());
    }

    public Vector2 getFinish() {
        return finish.getPosition();
    }

    public void setFinish(IVertex finish) {
        this.finish.setPosition(finish.getPosition());
    }

    public List<IVertex> getKillers() {
        return killers;
    }

    public void setKillers(List<IVertex> killers) {
        for (IVertex killer : killers) {
            addKiller(killer);
        }
    }


    public List<IVertex> getApples() {
        return apples;
    }

    public void setApples(List<IVertex> apples) {
        for (IVertex apple : apples) {
            addApple(apple);
        }
    }

    public void addKiller(IVertex killer) {
        killers.add(killer);
    }

    public void addApple(IVertex apple) {
        apples.add(apple);
    }

    public void dispose() {
        for (Killer killer : killerObjects) {
            killer.dispose();
        }

        for (Apple apple : appleObjects) {
            apple.dispose();
        }
    }

    public void removeApple(Apple apple) {
        apples.remove(apple);
    }


    @Override
    public void write(Json json) {

        json.writeArrayStart("polyLines");
        for (PolyLine<T> polyLine : map) {
            json.writeArrayStart();
            for (IVertex vertex : polyLine.getVertices()) {
                json.writeValue(vertex.getPosition());
            }
            json.writeArrayEnd();
        }
        json.writeArrayEnd();

        json.writeArrayStart("apples");
        for (IVertex apple : apples) {
            json.writeValue(apple.getPosition());
        }
        json.writeArrayEnd();

        json.writeArrayStart("killers");
        for (IVertex killer : killers) {
            json.writeValue(killer.getPosition());
        }
        json.writeArrayEnd();

        json.writeValue("start", start.getPosition());
        json.writeValue("finish", finish.getPosition());

    }

    @Override
    public void read(Json json, JsonValue jsonData) {

    }

    public void endLine() {
        if (getLastIsland() == null || getLastIsland().getVertices().size() > 0) {
            map.add(new PolyLine<T>());
        }
    }

    public PolyLine getLastIsland() {
        List<PolyLine<T>> islands = getIslands();
        if (islands.size() == 0) {
            return null;
        }
        return islands.get(islands.size() - 1);
    }

    public String toJson() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);

        return json.toJson(this);
    }

    public VertexMap prepare() {

        for (int i = 0; i < map.size(); i++) {
            checkVertices((ArrayList<IVertex>) map.get(i).getVertices());
        }
        return this;
    }

    static private boolean areVerticesClockwise (List<IVertex> vertices) {
        int numPoints = vertices.size();
        float area = 0;         // Accumulates area in the loop
        int j = numPoints - 1;  // The last vertex is the 'previous' one to the first

        for (int i=0; i<numPoints; i++)
        { area = area +  (vertices.get(j).getPosition().x+vertices.get(i).getPosition().x) *
                (vertices.get(j).getPosition().y-vertices.get(i).getPosition().y);
            j = i;  //j is previous vertex to i
        }
        return area/2>0;
    }

    public void setMap(ArrayList<PolyLine<T>> map) {
        for(PolyLine polyLine:map){
            this.map.add(polyLine);
        }
    }
}
