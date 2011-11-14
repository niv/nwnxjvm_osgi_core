package es.elv.nwnx2.jvm.nwnx.events;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

/**
 * Published when an attack action is added to a Creatures action queue.
 * Can be suppressed.
 */
public class CreatureAttack extends BaseEvent {
	final public Creature creature;
	final public GameObject attacked;

	public CreatureAttack(NWNXPlugin events, GameObject actor, GameObject attacked) {
		super(events, actor);

		this.creature = (Creature) actor;
		this.attacked = attacked;
	}

}
