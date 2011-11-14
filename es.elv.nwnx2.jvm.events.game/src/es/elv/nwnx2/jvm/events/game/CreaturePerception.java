package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public abstract class CreaturePerception extends GameEvent<Creature> {
	public final Creature creature;
	final public GameObject perceived;

	public CreaturePerception(GameObject actor) {
		super(actor);

		creature = (Creature) actor;
		perceived = (GameObject) NWScript.getLastPerceived();
	}
}
