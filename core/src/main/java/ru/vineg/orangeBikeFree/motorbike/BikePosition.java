package ru.vineg.orangeBikeFree.motorbike;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Vineg on 08.02.14.
 */
public class BikePosition {
    public final Vector2 wheel1Pos, wheel1Vel, wheel2Pos, wheel2Vel, headPos, headVel, bodyPos;

    public BikePosition(Vector2 wheel1Pos, Vector2 wheel1Vel, Vector2 wheel2Pos, Vector2 wheel2Vel, Vector2 headPos, Vector2 headVel, Vector2 bodyPos) {
        this.wheel1Pos = wheel1Pos;
        this.wheel1Vel = wheel1Vel;
        this.wheel2Pos = wheel2Pos;
        this.wheel2Vel = wheel2Vel;
        this.headPos = headPos;
        this.headVel = headVel;
        this.bodyPos = bodyPos;
    }
}
