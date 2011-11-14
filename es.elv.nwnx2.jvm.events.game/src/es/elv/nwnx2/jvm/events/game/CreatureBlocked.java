package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.Door;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class CreatureBlocked extends GameEvent<Creature> {
	public final Creature creature;
	public final Door blocker;

	public CreatureBlocked(GameObject actor) {
		super(actor);

		creature = (Creature) actor;
		blocker = (Door) NWScript.getBlockingDoor();
	}
}
