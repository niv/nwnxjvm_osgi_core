package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class CreatureAttacked extends GameEvent<Creature> {
	final public Creature creature;
	final public Creature attacker;
	final public int attackMode;
	final public int attackType;

	public CreatureAttacked(GameObject actor) {
		super(actor);

		creature = (Creature) actor;
		attacker = (Creature) NWScript.getLastAttacker(actor);
		attackMode = NWScript.getLastAttackMode(actor);
		attackType = NWScript.getLastAttackType(actor);
	}
}
