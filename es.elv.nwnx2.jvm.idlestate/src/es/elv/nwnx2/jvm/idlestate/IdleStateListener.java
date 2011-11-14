package es.elv.nwnx2.jvm.idlestate;

import es.elv.nwnx2.jvm.hierarchy.Creature;

public interface IdleStateListener {

	/**
	 * Gets called when a creature does something (anything that is not
	 * Action.INVALID).
	 *
	 * @param c
	 * @param idleTime the number of milliseconds the creature was idle.
	 */
	public void creatureActive(Creature c, long idleTime);

	/**
	 * Gets called when a creature changes location.
	 *
	 * @param c
	 * @param idleTime the number of milliseconds the creature was immobile.
	 */
	public void creatureMoved(Creature c, long idleTime);


	/**
	 * Called when a Creature becomes immobile. Uses the value returned
	 * by getImmobileTimeout() to determine if the creature triggers this
	 * event.
	 * @param c
	 */
	public void creatureBecomesImmobile(Creature c, long idleTime);

	/**
	 * Return 0 to not trigger creatureBecomesImmobile().
	 * @return
	 */
	public long getImmobileTimeout();


	/**
	 * Called when a Creature becomes immobile. Uses the value returned
	 * by getIdleTimeout() to determine if the creature triggers this
	 * event.
	 * @param c
	 */
	public void creatureBecomesIdle(Creature c, long idleTime);

	/**
	 * Return 0 to not trigger creatureBecomesIdle().
	 * @return
	 */
	public long getIdleTimeout();
}
