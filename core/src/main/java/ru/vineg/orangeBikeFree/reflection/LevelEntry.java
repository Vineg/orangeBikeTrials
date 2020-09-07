package ru.vineg.orangeBikeFree.reflection;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import ru.vineg.map.IVertex;
import ru.vineg.map.PolyLine;
import ru.vineg.map.VertexMap;
import ru.vineg.map.VertexMapLoader;


/**
 * Created with IntelliJ IDEA.
 * User: vineg
 * Date: 10/30/13
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class LevelEntry implements Json.Serializable{
    private String name;
    private int number=0;
    private VertexMap<IVertex> map;
//    private LevelsScreen listStage;

    public LevelEntry(FileHandle level, int number) {
        String data = level.readString();
        this.name = removeIntegerAtBegin(level.nameWithoutExtension());
        this.number = number;

        VertexMapLoader vertexMapLoader = new VertexMapLoader();
        map=vertexMapLoader.loadFromString(data);
    }

    public LevelEntry(VertexMap<IVertex> vertexMap) {
        map=vertexMap;
    }

    public LevelEntry(){}


    private String removeIntegerAtBegin(String s) {
        String name = s.replaceAll( "^([\\d]+)", "" );
        if(name.length()==0){
            name=s;
        }
        return name;
    }

    public String getName() {
        return name;
    }

//    public String getData() {
//        return data;
//    }

    public int getNumber() {
        return number;
    }

    public VertexMap getMap() {
        return map;
    }




    public static LevelEntry fromString(String dataString) {
//        return new Level(new VertexMapLoader().loadFromString(dataString));
//        return new Json().fromJson(Level.class,dataString);
        return new Json().fromJson(LevelEntry.class,dataString);
    }

    @Override
    public void write(Json json) {
        json.writeArrayStart("polyLines");
        for (PolyLine<IVertex> polyLine : map.getIslands()) {
            json.writeArrayStart();
            for (IVertex vertex : polyLine.getVertices()) {
                json.writeValue(vertex.getPosition());
            }
            json.writeArrayEnd();
        }
        json.writeArrayEnd();

        json.writeArrayStart("apples");
        for (IVertex apple : map.getApples()) {
            json.writeValue(apple.getPosition());
        }
        json.writeArrayEnd();

        json.writeArrayStart("killers");
        for (IVertex killer : map.getKillers()) {
            json.writeValue(killer.getPosition());
        }
        json.writeArrayEnd();

        json.writeValue("start", map.getStart());
        json.writeValue("finish", map.getFinish());
//        json.writeValue("name",name);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        map=new VertexMapLoader().loadFromJsonElement(json.readValue(ObjectMap.class, jsonData));
    }

//    @Override
//    public void write(Json json) {
//
//    }
//
//    @Override
//    public void read(Json json, JsonValue jsonData) {
//
//    }
}
