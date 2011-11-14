package es.elv.nwnx2.jvm.nwnx.events;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

/**
 * Published when a quickchat action is being added to a creatures action
 * queue. Can be suppressed.
 */
public class CreatureQuickchat extends BaseEvent {
	final public Creature creature;
	final int phrase;

	public CreatureQuickchat(NWNXPlugin events, GameObject actor, int quickChat) {
		super(events, actor);

		this.creature = (Creature) actor;
		this.phrase = quickChat;
	}

}
