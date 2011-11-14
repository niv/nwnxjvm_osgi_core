package es.elv.nwnx2.jvm.events.game;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class CreatureSpawn extends GameEvent<Creature> {
	public final Creature creature;

	public CreatureSpawn(GameObject actor) {
		super(actor);

		creature = (Creature) actor;
	}
}
