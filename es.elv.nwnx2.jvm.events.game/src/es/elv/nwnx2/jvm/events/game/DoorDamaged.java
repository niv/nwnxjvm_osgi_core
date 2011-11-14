package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Door;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class DoorDamaged extends GameEvent<Door> {
	final public Door door;
	final public GameObject damager;

	public DoorDamaged(GameObject actor) {
		super(actor);

		door = (Door) actor;
		damager = (GameObject) NWScript.getLastDamager(actor);
	}
}
