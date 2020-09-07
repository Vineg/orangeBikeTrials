package ru.vineg.graphics;

import com.badlogic.gdx.Gdx;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 12.12.13
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
public class Graphics {
    //    public static float fitZoom = Math.max(0.60f*720f/Gdx.graphics.getHeight(),0.60f*920f/Gdx.graphics.getWidth());
    private static int screenWidth=Gdx.graphics.getWidth();
    private static int screenHeight=Gdx.graphics.getHeight();
    public static int minHeight=720;
    public static int minWidth=720;

    public static float defaultZoom = 0.72f;

    public static void refresh(){
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        screenWidth = Math.max(w,h);
        screenHeight = Math.min(w,h);

        screenWidth=w;
        screenHeight=h;
    }

    public static int getMinWidth(){
        return (int) (minWidth * defaultZoom);
    }

    public static int getMinHeight(){
        return (int) (minHeight * defaultZoom);
    }


    public static int getScreenWidth() {
        return screenWidth;
    }


    public static int getScreenHeight() {
        return screenHeight;
    }

//    private static float getFitZoom() {
//        return Math.max(minHeight /screenHeight,minWidth/screenWidth);
//    }

}
