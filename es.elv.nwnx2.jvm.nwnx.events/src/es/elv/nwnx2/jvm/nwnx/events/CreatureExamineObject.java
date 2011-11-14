package es.elv.nwnx2.jvm.nwnx.events;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

/**
 * Published when a examine action is being added to a creatures action
 * queue. Can be suppressed.
 */
public class CreatureExamineObject extends BaseEvent {
	final public Creature creature;
	final public GameObject examined;

	public CreatureExamineObject(NWNXPlugin events, GameObject actor, GameObject examined) {
		super(events, actor);

		this.creature = (Creature) actor;
		this.examined = examined;
	}

}
