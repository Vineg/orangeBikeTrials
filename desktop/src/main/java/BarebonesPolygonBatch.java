import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.vineg.EarClippingTriangulator;

import java.util.ArrayList;

public class BarebonesPolygonBatch extends ApplicationAdapter {
    TextureRegion textureRegion;
    PolygonSpriteBatch batch;
    ArrayList<PolygonSprite> polygonSprites=new ArrayList<PolygonSprite>();

    public void create () {
        batch = new PolygonSpriteBatch();
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("data/badlogic.jpg")));
        for(int i=0;i<10;i++){
            float[] vertices = new float[]{i*50,0,i*50,50,i*50+50,50};
            polygonSprites.add(new PolygonSprite(new PolygonRegion(textureRegion,vertices,new EarClippingTriangulator().computeTriangles(vertices).toArray())));
        }
    }

    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for(int i=0;i<polygonSprites.size();i++){
            polygonSprites.get(i).draw(batch);
            //batch.flush();//uncomment for expected result
        }
        batch.end();
    }

    public static void main (String[] args) throws Exception {
        new LwjglApplication(new BarebonesPolygonBatch());
    }
}