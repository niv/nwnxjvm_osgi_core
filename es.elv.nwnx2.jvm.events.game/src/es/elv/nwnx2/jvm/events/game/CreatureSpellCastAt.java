package es.elv.nwnx2.jvm.events.game;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;

public class CreatureSpellCastAt extends ObjectSpellCastAt<Creature> {
	public final Creature creature;

	public CreatureSpellCastAt(GameObject actor) {
		super(actor);

		creature = (Creature) object;
	}
}
