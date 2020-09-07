package ru.vineg.math;

/**
 * Created by Vineg on 22.04.2014.
 */
public class MathUtils {
    public static int range(int i, int min, int max) {
        return Math.max(Math.min(i,max),min);
    }

    public static float modInt(float v, int mod) {
        return (((v%mod)+mod)%mod);
    }

    public static int divInt(float v, int divisor) {
        return (int) Math.floor(v/divisor);
    }

    public static float sig(float v) {
        return v%1;
    }

    public static float rand(float min, float max) {
        return (float) (Math.random()*(max-min)+min);
    }

    public static float angle(float v) {
        v+=Math.PI*2;
        while(v>Math.PI){
            v-=Math.PI*2;
        }
        return v;
    }
}
