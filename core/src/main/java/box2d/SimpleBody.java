package box2d;

import com.badlogic.gdx.physics.box2d.Body;
import ru.vineg.orangeBikeFree.IPhysicsObject;

/**
 * Created by vineg on 18.01.2015.
 */
public class SimpleBody implements IPhysicsObject {
    private final Body pBody;

    public SimpleBody(Body pBody) {
        this.pBody=pBody;
    }

    @Override
    public Body getBody() {
        return pBody;
    }

    @Override
    public void act(float pSecondsElapsed) {

    }
}
