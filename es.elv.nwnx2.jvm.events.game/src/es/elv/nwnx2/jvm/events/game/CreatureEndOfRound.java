package es.elv.nwnx2.jvm.events.game;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class CreatureEndOfRound extends GameEvent<Creature> {
	public final Creature creature;

	public CreatureEndOfRound(GameObject actor) {
		super(actor);

		creature = (Creature) actor;
	}
}
