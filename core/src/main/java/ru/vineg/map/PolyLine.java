package ru.vineg.map;

import box2d.PhysicsFactory;
import box2d.util.constants.PhysicsConstants;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import ru.vineg.orangeBikeFree.MotodroidGameWorld;
import ru.vineg.structure.layers.LayerObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vineg
 * Date: 15.09.13
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
public class PolyLine<T extends IVertex> extends LayerObject {
    public static final int WIDTH = 4;

    private List<T> vertices;
    private Vertex currentPoint = null;
    private static final FixtureDef WALL_FIXTURE_DEF = PhysicsFactory.createFixtureDef(1f, 0f, 40f, false, MotodroidGameWorld.CATEGORYBIT_WALL, MotodroidGameWorld.MASKBIT_WALL, (short) 0);

    private MotodroidGameWorld gameWorld;
//    public PolyLine(){
//        this.scene = scene;
//        this.mPhysicsWorld = mPhysicsWorld;
//        this.vertexBufferObjectManager = vertexBufferObjectManager;
//    }

    public PolyLine() {
        vertices = new ArrayList<T>();
    }

    public PolyLine(List<T> mapVertices) {
        vertices = mapVertices;
    }

    public void addVertex(T vertex) {
//        vertex.getPosition().y = -vertex.getPosition().y;
        vertices.add(vertex);
    }

    public void addPhysics(MotodroidGameWorld gameWorld) {
        int verticesCount = vertices.size();
        if (verticesCount < 2) {
            return;
        }
        this.gameWorld = gameWorld;


        ArrayList<Vector2> physicsVertices = new ArrayList<Vector2>();
        for (IVertex vertex : vertices) {
            physicsVertices.add(vertex.getPosition().cpy().scl(PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT));
        }

        Vector2[] physicsVerticesAr = new Vector2[0];
        if (vertices.get(0).getPosition().dst(vertices.get(verticesCount - 1).getPosition()) == 0) {
            physicsVerticesAr=physicsVertices.toArray(physicsVerticesAr);
        } else {
            physicsVerticesAr = getSolidLineVertices(physicsVertices, GameVars.lineWidth * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
        }

            final BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameWorld.getPhysicsWorld().createDynamicBody(boxBodyDef);


        ChainShape chain = new ChainShape();
        chain.createChain(physicsVerticesAr);
        WALL_FIXTURE_DEF.shape = chain;

        body.createFixture(WALL_FIXTURE_DEF);

        boxBodyDef.allowSleep = true;
        boxBodyDef.active = false;
        boxBodyDef.awake = false;



    }



    public Vector2[] getSolidLineVertices(ArrayList<Vector2> vertices, float width) {
        int totalVertices = vertices.size();
        if (totalVertices < 2) {
            return new Vector2[0];
        }
        ArrayList<Vector2> topVectors = new ArrayList<Vector2>(totalVertices * 3 + 10);
        ArrayList<Vector2> bottomVectors = new ArrayList<Vector2>(totalVertices * 3 + 10);

        float lastAngle = -1;
        for (int i = 0; i < totalVertices - 1; i++) {
            Vector2 curVert = vertices.get(i);
            Vector2 nextVert = vertices.get(i + 1);
            Vector2 vec = nextVert.cpy().sub(curVert).nor();
            Vector2 solidVec = vec.rotate(90);

            Vector2 bottomVec = curVert.cpy().add(solidVec.cpy().scl(-width / 2));
            Vector2 topVec = curVert.cpy().add(solidVec.cpy().scl(width / 2));
            if (i > 0) {
                if ((vec.angle() - lastAngle + 360) % 360 < 180) {
                    topVectors.get(topVectors.size() - 1).add(topVec).scl(0.5f);
                    if (bottomVec.dst2(bottomVectors.get(bottomVectors.size() - 1)) > 0.005f * 0.0051f) {
                        bottomVectors.add(bottomVec);
                    }
                } else {
                    bottomVectors.get(bottomVectors.size() - 1).add(bottomVec).scl(0.5f);
                    if (topVec.dst2(topVectors.get(topVectors.size() - 1)) > 0.005f * 0.0051f) {
                        topVectors.add(topVec);
                    }
                }
            } else {
                bottomVectors.add(bottomVec);
                topVectors.add(topVec);
            }

            topVectors.add(nextVert.cpy().add(solidVec.cpy().scl(width / 2)));
            bottomVectors.add(nextVert.cpy().add(solidVec.cpy().scl(-width / 2)));
            lastAngle = vec.angle();
        }
        Vector2[] vectors = new Vector2[topVectors.size()+bottomVectors.size()];
        for(int i=0;i<topVectors.size();i++){
            vectors[i]=topVectors.get(i);
        }
        for (int i = 0; i < bottomVectors.size(); i++) {
            vectors[i+topVectors.size()]=bottomVectors.get(bottomVectors.size()-1-i);
        }
        return vectors;
    }

    public ArrayList<T> getVertices() {
        return (ArrayList<T>) vertices;
    }

    public void addBefore(T newVertex, T vertex) {
        vertices.add(vertices.indexOf(vertex), newVertex);
    }

    @Override
    protected Object clone() {
        PolyLine<T> newLine = new PolyLine<T>();
        for (T vertex : vertices) {
            newLine.addVertex(vertex);
        }
        return newLine;
    }
}
