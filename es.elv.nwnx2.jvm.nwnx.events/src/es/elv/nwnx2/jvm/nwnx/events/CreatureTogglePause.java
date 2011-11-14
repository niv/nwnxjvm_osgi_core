package es.elv.nwnx2.jvm.nwnx.events;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

/**
 * Published when a creature pauses/unpauses the game.
 * Can be suppressed.
 */
public class CreatureTogglePause extends BaseEvent {
	final public Creature creature;

	public CreatureTogglePause(NWNXPlugin events, GameObject actor) {
		super(events, actor);

		this.creature = (Creature) actor;
	}

}
