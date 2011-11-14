package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.Door;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class DoorAttacked extends GameEvent<Door> {
	final public Door door;
	final public Creature attacker;
	final public int attackMode;
	final public int attackType;

	public DoorAttacked(GameObject actor) {
		super(actor);

		door = (Door) actor;
		attacker = (Creature) NWScript.getLastAttacker(actor);
		attackMode = NWScript.getLastAttackMode(actor);
		attackType = NWScript.getLastAttackType(actor);
	}
}
