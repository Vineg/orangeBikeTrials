/*******************************************************************************
 * Copyright 2012 David Saltares
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

package com.badlogic.gdx.gleed;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * @author David Saltares
 * @date 02/11/2012
 *
 * @brief GLEED2D texture element implementation
 */
public class TextureElement extends LevelObject {
	String path = "";
	TextureRegion region = new TextureRegion();
	Vector2 position = new Vector2(0.0f, 0.0f);

	public TextureElement() {
		super();
	}

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    float scaleX = 1.0f;
	float scaleY = 1.0f;
	float originX = 1.0f;
	float originY = 1.0f;

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    float rotation = 0.0f;


	/**
	 * @return texture path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @return region
	 */
	public TextureRegion getRegion() {
		return region;
	}
	
	/**
	 * @return element 2D world coordinates
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @return horizontal scale
	 */
	public float getScaleX() {
		return scaleX;
	}

	/**
	 * @return vertical scale
	 */
	public float getScaleY() {
		return scaleY;
	}

	/**
	 * @return texture units from left to centre
	 */
	public float getOriginX() {
		return originX;
	}

	/**
	 * @return texture units from top to centre
	 */
	public float getOriginY() {
		return getRegion().getRegionHeight()-originY;
	}
	
	/**
	 * @return rotation in radians
	 */
	public float getRotation() {
		return rotation;
	}

    public Vector2 getOrigin() {
        return new Vector2(getOriginX(),getOriginY());
    }
}
