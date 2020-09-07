package ru.vineg.map;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.ArrayList;

//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 15.09.13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */
public class VertexMapLoader<T extends IVertex> {
    private String MAP_NAME = "polyLines", START_NAME = "start", FINISH_NAME = "finish", APPLES_NAME = "apples", KILLERS_NAME = "killers";
    private VertexMap map;

    public VertexMapLoader() {
        map = new VertexMap();
    }

    public VertexMap<T> loadFromString(String jsonMap) {
        return loadFromString(jsonMap, new VertexMap());
    }

    public VertexMap loadFromString(String jsonMap, VertexMap map) {
        Json parser = new Json();
        ObjectMap objectMap = parser.fromJson(ObjectMap.class, jsonMap);
        this.map = map;
        VertexMap vertexMap = loadFromJsonElement(objectMap);
//        vertexMap.setScale(2);
        return vertexMap;
    }


    public VertexMap loadFromJsonElement(ObjectMap jsonObj) {
        Array jsonPolyLines = (Array) jsonObj.get(MAP_NAME);
        for (Object jsonPolyLine : jsonPolyLines) {
            Array points = (Array) jsonPolyLine;
            ArrayList<Vertex> mapVertices = json2vertices(points);
            map.addIsland(new PolyLine(mapVertices));
        }

        Vertex start = json2vector((JsonValue) jsonObj.get(START_NAME));
        Vertex finish = json2vector((JsonValue) jsonObj.get(FINISH_NAME));
        map.setStart(start);
        map.setFinish(finish);
        map.setApples(json2vertices((Array) jsonObj.get(APPLES_NAME)));
        map.setKillers(json2vertices((Array) jsonObj.get(KILLERS_NAME)));
        return map;
    }

    private Vertex json2vector(JsonValue jsonVec) {
        jsonVec = jsonVec.child;
        if (jsonVec == null) {
            return new Vertex(0, 0);
        }
        float x = jsonVec.asFloat();
        float y = 0;
        if (jsonVec.next != null) {
            y = jsonVec.next.asFloat();
        }
        return new Vertex(x, y);
    }

    private ArrayList<Vertex> json2vertices(Array verticesAr) {
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        for (int i = 0; i < verticesAr.size; i++) {
            JsonValue pointElement = (JsonValue) verticesAr.get(i);
            Vertex vertex = json2vector(pointElement);
            vertices.add(vertex);
        }
        return vertices;
    }
}
