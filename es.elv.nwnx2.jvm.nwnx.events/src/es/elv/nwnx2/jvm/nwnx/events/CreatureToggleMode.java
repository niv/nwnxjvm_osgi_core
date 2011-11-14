package es.elv.nwnx2.jvm.nwnx.events;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

/**
 * Published when a Creature tries to toggle a ActionMode.
 * Can be suppressed.
 */
public class CreatureToggleMode extends BaseEvent {
	final public Creature creature;
	final public int actionMode;

	public CreatureToggleMode(NWNXPlugin events, GameObject actor, int actionMode) {
		super(events, actor);

		this.creature = (Creature) actor;
		this.actionMode = actionMode;
	}

}
