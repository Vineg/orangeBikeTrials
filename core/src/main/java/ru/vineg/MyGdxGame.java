package ru.vineg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.gleed.Level;
import com.badlogic.gdx.gleed.LevelLoader;
import com.badlogic.gdx.gleed.LevelRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import ru.vineg.orangeBikeFree.AssetManager;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    private AssetManager manager;
    private Level level;
    private LevelRenderer levelRenderer;
    private OrthographicCamera camera;

    @Override
	public void create () {
        manager = new AssetManager();
		batch = new SpriteBatch();
		img = new Texture("finish.png");
        // Set loader in the Asset manager
        manager.setLoader(Level.class, new LevelLoader(new InternalFileHandleResolver()));


        // Tell the manager to load the level
        manager.load("data/bike.xml", Level.class);
        manager.finishLoading();
        level=manager.get("data/bike.xml",Level.class);
        levelRenderer = new LevelRenderer(level);
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.position.set(0,0,0);
        camera.update();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
        levelRenderer.render(batch,camera);
		batch.end();
        level.getLayer(0).getTexture("front_wheel").getPosition().x+=1;
	}
}
