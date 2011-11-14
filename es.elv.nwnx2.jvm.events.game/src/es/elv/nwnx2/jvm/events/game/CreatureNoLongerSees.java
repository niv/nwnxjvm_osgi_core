package es.elv.nwnx2.jvm.events.game;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;


public class CreatureNoLongerSees extends CreaturePerception {
	public final Creature creature;

	public CreatureNoLongerSees(GameObject actor) {
		super(actor);

		creature = (Creature) actor;
	}
}
