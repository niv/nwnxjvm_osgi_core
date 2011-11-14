package es.elv.nwnx2.jvm.events.game;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class CreatureRest extends GameEvent<Creature> {
	public final Creature creature;

	public CreatureRest(GameObject actor) {
		super(actor);

		creature = (Creature) actor;
	}
}
