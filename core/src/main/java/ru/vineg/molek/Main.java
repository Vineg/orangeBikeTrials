package ru.vineg.molek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by vineg on 31.01.2015.
 */
public class Main extends Game {
    private final ArrayList<Particle> particles = new ArrayList<>();
    Vector3d tmp = new Vector3d();
    Vector3d tmp1 = new Vector3d();
    Vector3d tmp2 = new Vector3d();
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private Particle p;
    private double scale=5;

    @Override
    public void create() {
        double z = 2;
        p=new Particle(z);
        p.position.set(0,0,0);
        particles.add(p);

        for (int i = 0; i <10 ; i++) {
            Particle e = new Particle(z);
            e.position.set(0+z*4*(i+1),Math.random()*10,Math.random());
            particles.add(e);
        }
        for (int i = 0; i < 11*z; i++) {
            Particle particle = new Particle(-1);
            particles.add(particle);
            particle.position.set(-2+4*i,Math.random()*10,Math.random());
        }
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int i) {
                return false;
            }

            @Override
            public boolean keyUp(int i) {
                return false;
            }

            @Override
            public boolean keyTyped(char c) {
                return false;
            }

            @Override
            public boolean touchDown(int i, int i1, int i2, int i3) {
                return false;
            }

            @Override
            public boolean touchUp(int i, int i1, int i2, int i3) {
                return false;
            }

            @Override
            public boolean touchDragged(int i, int i1, int i2) {
                return false;
            }

            @Override
            public boolean mouseMoved(int i, int i1) {
                return false;
            }

            @Override
            public boolean scrolled(int i) {
                if(i<0){
                    scale*=1.1;
                }else{
                    scale/=1.1;
                }
                return false;
            }
        });
    }

    @Override
    public void render() {
//        camera.position.set((float)(p.position.x*scale), (float)(p.position.y*scale), 0);
        camera.position.set(0,0,0);
        for (int i = 0; i < particles.size(); i++) {
            camera.position.add((float)particles.get(i).position.x,(float)particles.get(i).position.y,(float)particles.get(i).position.z);
        }
        camera.position.scl((float)scale/particles.size());
        camera.update();
//        super.render();
        Gdx.graphics.getGL20().glClearColor(1, 1, 1, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        for (int i = 0; i < 100; i++) {
            act();
        }


        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < particles.size(); i++) {
            Particle current = particles.get(i);
            shapeRenderer.setColor(current.q>0?Color.BLACK:Color.BLUE);
            draw(current);
        }
        shapeRenderer.end();
    }

    private void draw(Particle particle) {

        shapeRenderer.circle((float)(particle.position.x * scale), (float)(particle.position.y * scale), (float) (300/(100+(particle.position.z-p.position.z)*scale)));

    }

    private void act() {
        for (int i = 0; i < particles.size(); i++) {
            Particle current = particles.get(i);
            current.grad.set(0, 0, 0);
            for (int j = 0; j < particles.size(); j++) {
                if (j == i) {
                    continue;
                }
                Particle iterative = particles.get(j);
                tmp.set(dst(current, iterative));
                double len = tmp.len();
                current.grad.add(tmp.scl(iterative.q / len/len/len));
            }
            current.f.set(current.grad).scl(current.q);
        }

        for (int i = 0; i < particles.size(); i++) {
            Particle current = particles.get(i);
            for (int j = 0; j < particles.size(); j++) {
                if (j == i) {
                    continue;
                }
                Particle iterative = particles.get(j);
//                if(iterative.q>0){
//                    continue;
//                }
                Vector3d r = dst(current, iterative);
                Vector3d p = tmp.set(iterative.grad).scl(-Math.abs(iterative.q));
                double l = r.len();
//                if(l<0.005){continue;}

                tmp1.set(Vector3d.Zero).add(
                        tmp2.set(r).scl(p.dot(r) * 3).scl(1d / l / l / l / l/ l).add(p.scl(-1d/l/l/l))
                ).scl(current.q);

                current.f.add(tmp1);
                iterative.f.add(tmp1.scl(-1));
            }
        }
        for (int i = 0; i < particles.size(); i++) {
            Particle current=particles.get(i);
            current.v.add(tmp.set(current.f).scl(-0.0001d / current.m));
            current.v.scl(0.9999);
            current.position.add(current.v);
        }
    }

    Vector3d dst=new Vector3d();
    private Vector3d dst(Particle current, Particle iterative) {
        return dst.set(current.position).scl(-1).add(iterative.position);
    }

//    private Vector
}
