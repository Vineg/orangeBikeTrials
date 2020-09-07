package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.Gdx;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 14.01.14
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class MyFPSLogger{
    float timePassed=0;
    float maxDt = 0;
    int framesPassed=0;

    public void logMinFps(){
        float dt=Gdx.graphics.getDeltaTime();
        timePassed+=dt;
        maxDt =Math.max(maxDt,dt);
        if(timePassed>1){

            Debug.println("maxDt: "+ maxDt+", avg:" + timePassed/framesPassed);
            maxDt = 0;
            timePassed = 0;
            framesPassed=0;
        }
        framesPassed++;

    }

    public String getFps(){
        float dt=Gdx.graphics.getDeltaTime();
        return String.valueOf(1f/dt);
    }
}
