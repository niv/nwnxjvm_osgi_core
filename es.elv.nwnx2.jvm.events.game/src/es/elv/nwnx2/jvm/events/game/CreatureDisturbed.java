package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class CreatureDisturbed extends GameEvent<Creature> {
	public final Creature creature;
	final public GameObject disturber;

	public CreatureDisturbed(GameObject actor) {
		super(actor);

		creature = (Creature) actor;
		disturber = (GameObject) NWScript.getLastDisturbed();
	}
}
