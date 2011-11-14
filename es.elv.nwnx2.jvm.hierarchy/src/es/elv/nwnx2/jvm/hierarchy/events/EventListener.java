package es.elv.nwnx2.jvm.hierarchy.events;

public interface EventListener {
	/**
	 * Receives CoreEvent instances. Used by Feature and the @Handler annotation.
	 */
	public void handleEvent(CoreEvent event);
}
