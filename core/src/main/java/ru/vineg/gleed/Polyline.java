//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package ru.vineg.gleed;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;

public class Polyline implements Shape2D {
    private final float[] localVertices;
    private float[] worldVertices;
    private float x;
    private float y;
    private float originX;
    private float originY;
    private float rotation;
    private float scaleX = 1.0F;
    private float scaleY = 1.0F;
    private float length;
    private float scaledLength;
    private boolean calculateScaledLength = true;
    private boolean calculateLength = true;
    private boolean dirty = true;

    public Polyline() {
        this.localVertices = new float[0];
    }

    public Polyline(float[] vertices) {
        if(vertices.length < 2) {
            throw new IllegalArgumentException("polylines must contain at least 1 points.");
        } else {
            this.localVertices = vertices;
        }
    }

    public float[] getVertices() {
        return this.localVertices;
    }

    public float[] getTransformedVertices() {
        if(!this.dirty) {
            return this.worldVertices;
        } else {
            this.dirty = false;
            float[] localVertices = this.localVertices;
            if(this.worldVertices == null || this.worldVertices.length < localVertices.length) {
                this.worldVertices = new float[localVertices.length];
            }

            float[] worldVertices = this.worldVertices;
            float positionX = this.x;
            float positionY = this.y;
            float originX = this.originX;
            float originY = this.originY;
            float scaleX = this.scaleX;
            float scaleY = this.scaleY;
            boolean scale = scaleX != 1.0F || scaleY != 1.0F;
            float rotation = this.rotation;
            float cos = MathUtils.cosDeg(rotation);
            float sin = MathUtils.sinDeg(rotation);
            int i = 0;

            for(int n = localVertices.length; i < n; i += 2) {
                float x = localVertices[i] - originX;
                float y = localVertices[i + 1] - originY;
                if(scale) {
                    x *= scaleX;
                    y *= scaleY;
                }

                if(rotation != 0.0F) {
                    float oldX = x;
                    x = cos * x - sin * y;
                    y = sin * oldX + cos * y;
                }

                worldVertices[i] = positionX + x + originX;
                worldVertices[i + 1] = positionY + y + originY;
            }

            return worldVertices;
        }
    }

    public float getLength() {
        if(!this.calculateLength) {
            return this.length;
        } else {
            this.calculateLength = false;
            this.length = 0.0F;
            int i = 0;

            for(int n = this.localVertices.length - 2; i < n; i += 2) {
                float x = this.localVertices[i + 2] - this.localVertices[i];
                float y = this.localVertices[i + 1] - this.localVertices[i + 3];
                this.length += (float)Math.sqrt((double)(x * x + y * y));
            }

            return this.length;
        }
    }

    public float getScaledLength() {
        if(!this.calculateScaledLength) {
            return this.scaledLength;
        } else {
            this.calculateScaledLength = false;
            this.scaledLength = 0.0F;
            int i = 0;

            for(int n = this.localVertices.length - 2; i < n; i += 2) {
                float x = this.localVertices[i + 2] * this.scaleX - this.localVertices[i] * this.scaleX;
                float y = this.localVertices[i + 1] * this.scaleY - this.localVertices[i + 3] * this.scaleY;
                this.scaledLength += (float)Math.sqrt((double)(x * x + y * y));
            }

            return this.scaledLength;
        }
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getOriginX() {
        return this.originX;
    }

    public float getOriginY() {
        return this.originY;
    }

    public float getRotation() {
        return this.rotation;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
        this.dirty = true;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.dirty = true;
    }

    public void setRotation(float degrees) {
        this.rotation = degrees;
        this.dirty = true;
    }

    public void rotate(float degrees) {
        this.rotation += degrees;
        this.dirty = true;
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.dirty = true;
        this.calculateScaledLength = true;
    }

    public void scale(float amount) {
        this.scaleX += amount;
        this.scaleY += amount;
        this.dirty = true;
        this.calculateScaledLength = true;
    }

    public void calculateLength() {
        this.calculateLength = true;
    }

    public void calculateScaledLength() {
        this.calculateScaledLength = true;
    }

    public void dirty() {
        this.dirty = true;
    }

    public void translate(float x, float y) {
        this.x += x;
        this.y += y;
        this.dirty = true;
    }
}
