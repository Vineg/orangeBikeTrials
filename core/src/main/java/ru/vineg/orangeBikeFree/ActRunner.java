package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.UpdateHandlerList;
import com.badlogic.gdx.physics.box2d.Body;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.ActHandlerList;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 13.12.13
 * Time: 20:01
 * To change this template use File | Settings | File Templates.
 */
public abstract class ActRunner implements IUpdateHandler {

    public UpdateHandlerList mUpdateHandlers= new UpdateHandlerList();
    protected Body body;

    public void registerUpdateHandler(final IUpdateHandler pUpdateHandler) {
        this.mUpdateHandlers.add(pUpdateHandler);
    }

    public void removeUpdateHandler(IUpdateHandler handler) {
        mUpdateHandlers.remove(handler);
    }

    public Body getBody() {
        return body;
    }



    @Override
    public void onUpdate(float pSecondsElapsed) {
        mUpdateHandlers.onUpdate(pSecondsElapsed);
    }

    @Override
    public void reset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
