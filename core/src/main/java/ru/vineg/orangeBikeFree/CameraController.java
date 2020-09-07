package ru.vineg.orangeBikeFree;

import box2d.util.constants.PhysicsConstants;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.util.adt.list.SmartList;
import ru.vineg.graphics.ICamera2d;
import ru.vineg.map.GameVars;
import ru.vineg.orangeBikeFree.motorbike.Motorbike;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 20.09.13
 * Time: 19:46
 * To change this template use File | Settings | File Templates.
 */
public class CameraController implements IUpdateHandler {
    private final float startZoom=1.4f;
    public Motorbike motorbike;
    private boolean zooming;
    private float cameraVelocity = 0.013f;
    private float x;
    private SmartList<ICamera2d> cameraList=new SmartList<>();

    public CameraController(Motorbike motorbike, ICamera2d camera) {
        this.motorbike = motorbike;
        cameraList.add(camera);
        camera.setZoomFactor(startZoom);
        onUpdate(0.1f);
        x = getTargetX(camera);
        float motorbikeX = motorbike.getPosition().x;
        camera.setPosition(motorbikeX + getTargetX(camera), motorbike.getPosition().y);
        camera.update();

    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
//        pSecondsElapsed = Math.min(Gdx.graphics.getDeltaTime(), 0.1f);
        pSecondsElapsed=1/50f;
        float q = pSecondsElapsed * 60;
        int size = cameraList.size();
        for (int i = 0; i < size; i++) {
            ICamera2d camera = cameraList.get(i);
            float zoomSpeed = 0.013f * q;
            float zoomFactor = camera.getZoomFactor();
            float meterToPixelRatio = PhysicsConstants.METER_TO_PIXEL_RATIO_DEFAULT;
            float targetZoom = (startZoom + motorbike.getSpeed() * meterToPixelRatio / 700f);
            float delta = targetZoom / zoomFactor;

            double newZoom = zoomFactor * Math.pow(delta, zoomSpeed);
            if ((delta > 0.95 && delta < 1) || (delta < 1.05 && delta > 1)) {
                zooming = false;
            }

            if (delta < 0.8 || delta > 1.2 || zooming) {
                camera.setZoomFactor((float) newZoom);
                zooming = true;

            }

            float motorbikeX = motorbike.getPosition().x;
            float targetX = getTargetX(camera);
            x = x + (targetX - x) * cameraVelocity * q;
            if (GameVars.smoothCamera) {
                camera.setPosition(camera.getX() + (motorbikeX + x - camera.getX()) * pSecondsElapsed * 5f, camera.getY() + (motorbike.getPosition().y - camera.getY()) * pSecondsElapsed * 5f);
            }else{
                camera.setPosition(motorbikeX + x, motorbike.getPosition().y);
            }
            camera.update();
        }
    }

    @Override
    public void reset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private float getTargetX(ICamera2d camera) {
        return camera.getWidth() / 3 * motorbike.direction;
    }

//    public float getRelativePosition() {
//        return x/getTargetX(camera);
//    }
}
