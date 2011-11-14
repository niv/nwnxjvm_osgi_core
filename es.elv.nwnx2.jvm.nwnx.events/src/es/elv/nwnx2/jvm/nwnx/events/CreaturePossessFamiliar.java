package es.elv.nwnx2.jvm.nwnx.events;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

/**
 * Published when a possess familiar action is being added to a creatures action
 * queue. Can be suppressed.
 */
public class CreaturePossessFamiliar extends BaseEvent {
	final public Creature creature;

	public CreaturePossessFamiliar(NWNXPlugin events, GameObject actor) {
		super(events, actor);

		this.creature = (Creature) actor;
	}

}
