package es.elv.nwnx2.jvm.nwnx.events;

import org.nwnx.nwnx2.jvm.NWLocation;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

/**
 * Published when a use skill action is being added to a creatures action
 * queue. Can be suppressed.
 */
public class CreatureUseSkill extends BaseEvent {
	final public Creature creature;
	final public int skill;
	final public GameObject on;
	final public NWLocation at;

	public CreatureUseSkill(NWNXPlugin events, GameObject actor, int skill, GameObject on, NWLocation at) {
		super(events, actor);

		this.creature = (Creature) actor;
		this.skill = skill;
		this.on = on;
		this.at = at;
	}

}
