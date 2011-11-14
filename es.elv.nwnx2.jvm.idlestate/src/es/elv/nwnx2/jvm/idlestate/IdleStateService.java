package es.elv.nwnx2.jvm.idlestate;

import es.elv.nwnx2.jvm.hierarchy.Creature;


public interface IdleStateService {

	/**
	 * Gets the number of milliseconds the given Creature was/is immobile
	 * (not changing Position or Facing even by a bit),
	 * or 0 if no time has been recorded yet.
	 * @param c
	 * @return
	 */
	public long immobileFor(Creature c);

	/**
	 * Gets the number of milliseconds the given creatue didn't do anything,
	 * or 0 if no time has been recorded yet.
	 * @param c
	 * @return
	 */
	public long idleFor(Creature c);

	/**
	 * Resets the idle time of given creature.
	 * @param c
	 * @return
	 */
	public void resetIdle(Creature c);

	/**
	 * Resets the immobile time of the given creature.
	 * @param c
	 * @return
	 */
	public void resetImmobile(Creature c);
}
