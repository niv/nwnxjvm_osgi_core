package es.elv.nwnx2.jvm.events.game;

import es.elv.nwnx2.jvm.hierarchy.events.CoreEvent;

public interface EventListener {
	/**
	 * Receives CoreEvent instances. Used by Feature and the @Handler annotation.
	 */
	public void handleEvent(CoreEvent event);
}
