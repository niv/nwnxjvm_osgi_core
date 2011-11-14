package es.elv.nwnx2.jvm.nwnx.events;

import org.nwnx.nwnx2.jvm.NWLocation;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

/**
 * Published when a spell casting action is being added to a creatures action
 * queue. Can be suppressed.
 */
public class CreatureCastSpell extends BaseEvent {
	final public Creature creature;
	final public int spell;
	final public GameObject on;
	final public NWLocation at;

	public CreatureCastSpell(NWNXPlugin events, GameObject actor, int spell, GameObject on, NWLocation at) {
		super(events, actor);

		this.creature = (Creature) actor;
		this.spell = spell;
		this.on = on;
		this.at = at;
	}

}
