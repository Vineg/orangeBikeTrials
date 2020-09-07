//package ru.vineg.orangeBikeFree;
//
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
//import ru.vineg.map.Vertex;
//import ru.vineg.map.VertexMapLoader;
//
///**
// * Created by vineg on 10.01.2015.
// */
//public class ZoomMap extends Game{
//    public static void main(String[] args) {
//        new LwjglApplication(new ZoomMap());
//    }
//
//    @Override
//    public void create() {
//        new VertexMapLoader<Vertex>().loadFromString(Gdx.files.internal("levels/1line.json").readString());
//    }
//}
