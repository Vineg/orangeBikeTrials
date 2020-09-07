package ru.vineg.molek;


/**
 * Created by vineg on 31.01.2015.
 */
public class Particle {
    public final Vector3d position = new Vector3d(Math.random()*222,Math.random()*222,Math.random());
    public final Vector3d grad = new Vector3d();
    public final double q;
    public final Vector3d f =new Vector3d();
//    public final Vector3d v = new Vector3d(Math.random()*0.0001,Math.random()*0.0001,0);
    public final Vector3d v = new Vector3d();
    public double m=1f;

    public Particle(double q) {
        this.q = q;
    }

    public Particle(double q, float m) {
        this(q);
        this.m=m;
    }
}
