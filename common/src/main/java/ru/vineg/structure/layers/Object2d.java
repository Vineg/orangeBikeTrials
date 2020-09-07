package ru.vineg.structure.layers;


import com.badlogic.gdx.math.Vector2;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created with IntelliJ IDEA.
 * User: vineg
 * Date: 10/3/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Object2d implements IObject2d {


    public float getWidth(){return 0;};
    public float getHeight(){return 0;};

    Vector2 setPosition=new Vector2();
    public void setPosition(float x, float y){
        setPosition(setPosition.set(x,y));
    };

    Vector2 getPosition=new Vector2();
    @Override
    public Vector2 getPosition() {
        return getPosition.set(getX(),getY());
    }


    protected void setObjectRotation(float v){};



    public float getRotation(){return 0;};


    /**
     *
     * @return local origin position
     */
    public float getOriginX(){return 0;};
    public float getOriginY(){return 0;};

    public void changeOriginToCenter(){};

    public void changeOrigin(float x, float y){};


    public void setScale(float v){setScale(v,v);}

    public void setScale(float scaleX, float scaleY){};

    public float getScaleX(){return 1;};

    public float getScaleY(){return 1;};

    @Override
    public void setSize(float width, float height) {
        throw new NotImplementedException();
    }

    @Override
    public void setRotation(float radians) {
        throw new NotImplementedException();
    }

    public void setDefaultRotation(float rotation){throw new NotImplementedException();};

}
