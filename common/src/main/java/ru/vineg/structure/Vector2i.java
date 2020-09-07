/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package ru.vineg.structure;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import ru.vineg.map.IVertex;

/** Encapsulates a 2D vector. Allows chaining methods by returning a reference to itself
 * @author badlogicgames@gmail.com */
public class Vector2i{

    public final static Vector2i X = new Vector2i(1, 0);
    public final static Vector2i Y = new Vector2i(0, 1);
    public final static Vector2i Zero = new Vector2i(0, 0);

    /** the x-component of this vector **/
    public int x;
    /** the y-component of this vector **/
    public int y;

    /** Constructs a new vector at (0,0) */
    public Vector2i() {
    }

    /** Constructs a vector with the given components
     * @param x The x-component
     * @param y The y-component */
    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Constructs a vector from the given vector
     * @param v The vector */
    public Vector2i(Vector2i v) {
        set(v);
    }

    public Vector2i(IVertex vert) {
        this.x= (int) vert.getPosition().x;
        this.y= (int) vert.getPosition().y;

    }

    public Vector2i cpy () {
        return new Vector2i(this);
    }

    
    public int len2 () {
        return x * x + y * y;
    }

    
    public Vector2i set (Vector2i v) {
        x = v.x;
        y = v.y;
        return this;
    }

    /** Sets the components of this vector
     * @param x The x-component
     * @param y The y-component
     * @return This vector for chaining */
    public Vector2i set (int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    
    public Vector2i sub (Vector2i v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    /** Substracts the other vector from this vector.
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @return This vector for chaining */
    public Vector2i sub (int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    
    public Vector2i add (Vector2i v) {
        x += v.x;
        y += v.y;
        return this;
    }

    /** Adds the given components to this vector
     * @param x The x-component
     * @param y The y-component
     * @return This vector for chaining */
    public Vector2i add (int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    
    public int dot (Vector2i v) {
        return x * v.x + y * v.y;
    }

    
    public Vector2i scl (int scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    /** Multiplies this vector by a scalar
     * @return This vector for chaining */
    public Vector2i scl (int x, int y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    
    public Vector2i scl (Vector2i v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    
    public Vector2i mulAdd(Vector2i vec, int scalar) {
        this.x += vec.x * scalar;
        this.y += vec.y * scalar;
        return this;
    }

    
    public Vector2i mulAdd(Vector2i vec, Vector2i mulVec) {
        this.x += vec.x * mulVec.x;
        this.y += vec.y * mulVec.y;
        return this;
    }

    
    public int dst2 (Vector2i v) {
        final int x_d = v.x - x;
        final int y_d = v.y - y;
        return x_d * x_d + y_d * y_d;
    }

    /** @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @return the squared distance between this and the other vector */
    public int dst2 (int x, int y) {
        final int x_d = x - this.x;
        final int y_d = y - this.y;
        return x_d * x_d + y_d * y_d;
    }



    public String toString () {
        return "[" + x + ":" + y + "]";
    }


    /** Calculates the 2D cross product between this and the given vector.
     * @param v the other vector
     * @return the cross product */
    public int crs (Vector2i v) {
        return this.x * v.y - this.y * v.x;
    }

    /** Calculates the 2D cross product between this and the given vector.
     * @param x the x-coordinate of the other vector
     * @param y the y-coordinate of the other vector
     * @return the cross product */
    public int crs (int x, int y) {
        return this.x * y - this.y * x;
    }


    /** Rotates the Vector2i by 90 degrees in the specified direction, where >= 0 is counter-clockwise and < 0 is clockwise. */
    public Vector2i rotate90 (int dir) {
        int x = this.x;
        if (dir >= 0) {
            this.x = -y;
            y = x;
        } else {
            this.x = y;
            y = -x;
        }
        return this;
    }



    
    public boolean isZero () {
        return x == 0 && y == 0;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        Vector2i vec = (Vector2i) obj;
        return vec.x==x&&vec.y==y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }


    /** Rotates the Vector2 by the given angle, counter-clockwise assuming the y-axis points up.
     * @param degrees the angle in degrees */
    public Vector2i rotate (float degrees) {
        return rotateRad(degrees * MathUtils.degreesToRadians);
    }

    /** Rotates the Vector2 by the given angle, counter-clockwise assuming the y-axis points up.
     * @param radians the angle in radians */
    public Vector2i rotateRad (float radians) {
        float cos = (float)Math.cos(radians);
        float sin = (float)Math.sin(radians);

        float newX = this.x * cos - this.y * sin;
        float newY = this.x * sin + this.y * cos;

        this.x = (int)newX;
        this.y = (int)newY;

        return this;
    }

    public Vector2i set(Vector2 position) {
        return set((int)position.x,(int)position.y);
    }

    public float len() {
        return (float) Math.sqrt(len2());
    }

    public float angleRad() {
        return MathUtils.atan2(y,x);
    }
}
