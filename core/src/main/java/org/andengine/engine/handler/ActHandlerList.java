package org.andengine.engine.handler;

import java.util.ArrayList;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 09:45:22 - 31.03.2010
 */
public class ActHandlerList extends ArrayList<IActHandler> implements IUpdateHandler {
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

	public ActHandlerList() {
	}

	public ActHandlerList(final int pCapacity) {
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
            this.get(i).act(pSecondsElapsed);
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
