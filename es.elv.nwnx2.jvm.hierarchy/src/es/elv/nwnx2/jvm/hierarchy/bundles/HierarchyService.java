package es.elv.nwnx2.jvm.hierarchy.bundles;

import es.elv.nwnx2.jvm.hierarchy.events.CoreEvent;


public interface HierarchyService {
	/**
	 * Returns the number of NWN context switches that happened
	 * since the Hierarchy bundle was started.
	 * @return number of context switches
	 */
	public long getContextSwitchCount();

	/**
	 * @return the System.currentTimeMillis() at load time, or -1 if
	 *   the bundle is not active
	 */
	public long getStartedOn();

	/**
	 * Send out a (custom) event synchronously. Will never raise
	 * Exceptions to the caller; instead, Hierarchy will handle
	 * them as configured.
	 * @param e the event to send
	 */
	public void sendEvent(CoreEvent e);
}
