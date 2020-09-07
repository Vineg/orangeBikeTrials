package ru.vineg.orangeBikeFree;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Interpolation;
import ru.vineg.orangeBikeFree.motorbike.Motorbike;

/**
 * Created by Vineg on 09.03.14.
 */
public class BikeSoundController {
    private final Motorbike bike;
    private final Sound sound;
    private final CameraController cameraController;
    private long soundId;
    private float pitch = 0f;
    private float volume = 0f;

    public BikeSoundController(Sound sound, Motorbike motorbike, CameraController cameraController) {
        bike = motorbike;
        this.cameraController = cameraController;
        soundId = sound.loop();
        this.sound = sound;
        setPitch(0f);
    }

    public void update(float dt) {
        if(!bike.hasSound){
            sound.pause();
            return;
        }
        sound.resume();
        float wheelSpeed = bike.getWheelSpeed() / bike.getMaxWheelSpeed();
        wheelSpeed = bike.getControl() > 0 ? wheelSpeed : -0.3f;

        float pitch = 1 + wheelSpeed * 0.5f;
        setSmoothPitch(pitch, dt);
        if (bike.getControl() > 0) {
            setVolume(1f - (pitch - 1) * 0.7f, dt);
        }else{
            setVolume(0.7f,dt);
        }
        bike.hasSound=false;
    }

    private void setSmoothPitch(float v, float dt) {
        pitch = Interpolation.linear.apply(pitch, v, dt * 10);
        setPitch(pitch);
    }

    private void setPitch(float pitch) {
        sound.setPitch(soundId, pitch);
    }

    private void setVolume(float v, float dt) {
        volume = Interpolation.linear.apply(volume, v, dt * 10);
        sound.setVolume(soundId, volume);
    }

    public void stop() {
        sound.stop();
    }

    public void pause() {
        sound.pause();
    }

    public void resume() {
        sound.resume();
    }
}
