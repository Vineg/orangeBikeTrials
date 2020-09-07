package com.badlogic.gdx;

import org.andengine.engine.handler.IUpdateHandler;

import java.util.ArrayList;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 09:45:22 - 31.03.2010
 */
public class UpdateHandlerList extends ArrayList<IUpdateHandler> implements IUpdateHandler {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final long serialVersionUID = -8842562717687229277L;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public UpdateHandlerList() {
	}

	public UpdateHandlerList(final int pCapacity) {
		super(pCapacity);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onUpdate(final float pSecondsElapsed) {
		int handlerCount = this.size();
		for(int i = handlerCount - 1; i >= 0; i--) {
            this.get(i).onUpdate(pSecondsElapsed);
		}
	}

	@Override
	public void reset() {
		final int handlerCount = this.size();
		for(int i = handlerCount - 1; i >= 0; i--) {
//			this.get(i).reset();
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
