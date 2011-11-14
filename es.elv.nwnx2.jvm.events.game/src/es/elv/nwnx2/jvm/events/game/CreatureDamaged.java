package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class CreatureDamaged extends GameEvent<Creature> {
	final public Creature creature;
	final public GameObject damager;

	public CreatureDamaged(GameObject actor) {
		super(actor);

		creature = (Creature) actor;
		damager = (GameObject) NWScript.getLastDamager(actor);
	}
}
