package es.elv.nwnx2.jvm.nwnx.events;

import org.nwnx.nwnx2.jvm.NWLocation;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

/**
 * Published when a use feat action is being added to a creatures action
 * queue. Can be suppressed.
 */
public class CreatureUseFeat extends BaseEvent {
	final public Creature creature;
	final public int feat;
	final public GameObject on;
	final public NWLocation at;

	public CreatureUseFeat(NWNXPlugin events, GameObject actor, int feat, GameObject on, NWLocation at) {
		super(events, actor);

		this.creature = (Creature) actor;
		this.feat = feat;
		this.on = on;
		this.at = at;
	}

}
