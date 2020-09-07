//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ru.vineg.molek;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.NumberUtils;
import java.io.Serializable;

public class Vector3d implements Serializable{
    private static final long serialVersionUID = 3840054589595372522L;
    public double x;
    public double y;
    public double z;
    public static final Vector3d X = new Vector3d(1.0F, 0.0F, 0.0F);
    public static final Vector3d Y = new Vector3d(0.0F, 1.0F, 0.0F);
    public static final Vector3d Z = new Vector3d(0.0F, 0.0F, 1.0F);
    public static final Vector3d Zero = new Vector3d(0.0F, 0.0F, 0.0F);
    private static final Matrix4 tmpMat = new Matrix4();

    public Vector3d() {
    }

    public Vector3d(double x, double y, double z) {
        this.set(x, y, z);
    }

    public Vector3d(Vector3d vector) {
        this.set((Vector3d)vector);
    }

    public Vector3d(double[] values) {
        this.set(values[0], values[1], values[2]);
    }

    public Vector3d(Vector2 vector, double z) {
        this.set(vector.x, vector.y, z);
    }

    public Vector3d set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3d set(Vector3d vector) {
        return this.set(vector.x, vector.y, vector.z);
    }

    public Vector3d set(double[] values) {
        return this.set(values[0], values[1], values[2]);
    }

    public Vector3d set(Vector2 vector, double z) {
        return this.set(vector.x, vector.y, z);
    }

    public Vector3d cpy() {
        return new Vector3d(this);
    }

    public Vector3d add(Vector3d vector) {
        return this.add(vector.x, vector.y, vector.z);
    }

    public Vector3d add(double x, double y, double z) {
        return this.set(this.x + x, this.y + y, this.z + z);
    }

    public Vector3d add(double values) {
        return this.set(this.x + values, this.y + values, this.z + values);
    }

    public Vector3d sub(Vector3d a_vec) {
        return this.sub(a_vec.x, a_vec.y, a_vec.z);
    }

    public Vector3d sub(double x, double y, double z) {
        return this.set(this.x - x, this.y - y, this.z - z);
    }

    public Vector3d sub(double value) {
        return this.set(this.x - value, this.y - value, this.z - value);
    }

    public Vector3d scl(double scalar) {
        return this.set(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3d scl(Vector3d other) {
        return this.set(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public Vector3d scl(double vx, double vy, double vz) {
        return this.set(this.x * vx, this.y * vy, this.z * vz);
    }

    public Vector3d mulAdd(Vector3d vec, double scalar) {
        this.x += vec.x * scalar;
        this.y += vec.y * scalar;
        this.z += vec.z * scalar;
        return this;
    }

    public Vector3d mulAdd(Vector3d vec, Vector3d mulVec) {
        this.x += vec.x * mulVec.x;
        this.y += vec.y * mulVec.y;
        this.z += vec.z * mulVec.z;
        return this;
    }

    public static double len(double x, double y, double z) {
        return (double)Math.sqrt((double)(x * x + y * y + z * z));
    }

    public double len() {
        return (double)Math.sqrt((double)(this.x * this.x + this.y * this.y + this.z * this.z));
    }

    public static double len2(double x, double y, double z) {
        return x * x + y * y + z * z;
    }

    public double len2() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public boolean idt(Vector3d vector) {
        return this.x == vector.x && this.y == vector.y && this.z == vector.z;
    }

    public static double dst(double x1, double y1, double z1, double x2, double y2, double z2) {
        double a = x2 - x1;
        double b = y2 - y1;
        double c = z2 - z1;
        return (double)Math.sqrt((double)(a * a + b * b + c * c));
    }

    public double dst(Vector3d vector) {
        double a = vector.x - this.x;
        double b = vector.y - this.y;
        double c = vector.z - this.z;
        return (double)Math.sqrt((double)(a * a + b * b + c * c));
    }

    public double dst(double x, double y, double z) {
        double a = x - this.x;
        double b = y - this.y;
        double c = z - this.z;
        return (double)Math.sqrt((double)(a * a + b * b + c * c));
    }

    public static double dst2(double x1, double y1, double z1, double x2, double y2, double z2) {
        double a = x2 - x1;
        double b = y2 - y1;
        double c = z2 - z1;
        return a * a + b * b + c * c;
    }

    public double dst2(Vector3d point) {
        double a = point.x - this.x;
        double b = point.y - this.y;
        double c = point.z - this.z;
        return a * a + b * b + c * c;
    }

    public double dst2(double x, double y, double z) {
        double a = x - this.x;
        double b = y - this.y;
        double c = z - this.z;
        return a * a + b * b + c * c;
    }

    public Vector3d nor() {
        double len2 = this.len2();
        return len2 != 0.0F && len2 != 1.0F?this.scl(1.0F / (double)Math.sqrt((double)len2)):this;
    }

    public static double dot(double x1, double y1, double z1, double x2, double y2, double z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public double dot(Vector3d vector) {
        return this.x * vector.x + this.y * vector.y + this.z * vector.z;
    }

    public double dot(double x, double y, double z) {
        return this.x * x + this.y * y + this.z * z;
    }

    public Vector3d crs(Vector3d vector) {
        return this.set(this.y * vector.z - this.z * vector.y, this.z * vector.x - this.x * vector.z, this.x * vector.y - this.y * vector.x);
    }

    public Vector3d crs(double x, double y, double z) {
        return this.set(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
    }

    public Vector3d mul4x3(double[] matrix) {
        return this.set(this.x * matrix[0] + this.y * matrix[3] + this.z * matrix[6] + matrix[9], this.x * matrix[1] + this.y * matrix[4] + this.z * matrix[7] + matrix[10], this.x * matrix[2] + this.y * matrix[5] + this.z * matrix[8] + matrix[11]);
    }



    public boolean isUnit() {
        return this.isUnit(1.0E-9F);
    }

    public boolean isUnit(double margin) {
        return Math.abs(this.len2() - 1.0F) < margin;
    }

    public boolean isZero() {
        return this.x == 0.0F && this.y == 0.0F && this.z == 0.0F;
    }

    public boolean isZero(double margin) {
        return this.len2() < margin;
    }

    public boolean isOnLine(Vector3d other, double epsilon) {
        return len2(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z, this.x * other.y - this.y * other.x) <= epsilon;
    }

    public boolean isOnLine(Vector3d other) {
        return len2(this.y * other.z - this.z * other.y, this.z * other.x - this.x * other.z, this.x * other.y - this.y * other.x) <= 1.0E-6F;
    }

    public boolean isCollinear(Vector3d other, double epsilon) {
        return this.isOnLine((Vector3d)other, epsilon) && this.hasSameDirection((Vector3d)other);
    }

    public boolean isCollinear(Vector3d other) {
        return this.isOnLine((Vector3d)other) && this.hasSameDirection((Vector3d)other);
    }

    public boolean isCollinearOpposite(Vector3d other, double epsilon) {
        return this.isOnLine((Vector3d)other, epsilon) && this.hasOppositeDirection((Vector3d)other);
    }

    public boolean isCollinearOpposite(Vector3d other) {
        return this.isOnLine((Vector3d)other) && this.hasOppositeDirection((Vector3d)other);
    }



    public boolean hasSameDirection(Vector3d vector) {
        return this.dot((Vector3d)vector) > 0.0F;
    }

    public boolean hasOppositeDirection(Vector3d vector) {
        return this.dot((Vector3d)vector) < 0.0F;
    }

    public Vector3d lerp(Vector3d target, double alpha) {
        this.scl(1.0F - alpha);
        this.add(target.x * alpha, target.y * alpha, target.z * alpha);
        return this;
    }



    public Vector3d slerp(Vector3d target, double alpha) {
        double dot = this.dot((Vector3d)target);
        if((double)dot <= 0.9995D && (double)dot >= -0.9995D) {
            double theta0 = (double)Math.acos((double)dot);
            double theta = theta0 * alpha;
            double st = (double)Math.sin((double)theta);
            double tx = target.x - this.x * dot;
            double ty = target.y - this.y * dot;
            double tz = target.z - this.z * dot;
            double l2 = tx * tx + ty * ty + tz * tz;
            double dl = st * (l2 < 1.0E-4F?1.0F:1.0F / (double)Math.sqrt((double)l2));
            return this.scl((double)Math.cos((double)theta)).add(tx * dl, ty * dl, tz * dl).nor();
        } else {
            return this.lerp((Vector3d)target, alpha);
        }
    }

    public String toString() {
        return this.x + "," + this.y + "," + this.z;
    }

    public Vector3d limit(double limit) {
        if(this.len2() > limit * limit) {
            this.nor().scl(limit);
        }

        return this;
    }

    public Vector3d clamp(double min, double max) {
        double l2 = this.len2();
        return l2 == 0.0F?this:(l2 > max * max?this.nor().scl(max):(l2 < min * min?this.nor().scl(min):this));
    }


    public boolean epsilonEquals(Vector3d other, double epsilon) {
        return other == null?false:(Math.abs(other.x - this.x) > epsilon?false:(Math.abs(other.y - this.y) > epsilon?false:Math.abs(other.z - this.z) <= epsilon));
    }

    public boolean epsilonEquals(double x, double y, double z, double epsilon) {
        return Math.abs(x - this.x) > epsilon?false:(Math.abs(y - this.y) > epsilon?false:Math.abs(z - this.z) <= epsilon);
    }

    public Vector3d setZero() {
        this.x = 0.0F;
        this.y = 0.0F;
        this.z = 0.0F;
        return this;
    }
}
