package es.elv.nwnx2.jvm.events.game;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;

public class CreatureHears extends CreaturePerception {
	public final Creature creature;

	public CreatureHears(GameObject actor) {
		super(actor);

		creature = (Creature) actor;
	}
}
