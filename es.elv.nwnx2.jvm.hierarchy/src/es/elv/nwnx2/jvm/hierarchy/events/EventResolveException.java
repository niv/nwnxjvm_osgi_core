package es.elv.nwnx2.jvm.hierarchy.events;

/**
 * Thrown when a ingame event cannot be resolved to a hierarchy-mapped event class.
 * @author elven
 *
 */
public class EventResolveException extends Exception {
	private static final long serialVersionUID = 6638702537931772463L;

	public EventResolveException(String message, Throwable e) {
		super(message, e);
	}
}
