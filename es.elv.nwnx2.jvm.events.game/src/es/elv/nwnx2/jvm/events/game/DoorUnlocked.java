package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Door;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class DoorUnlocked extends GameEvent<Door> {
	final public Door door;
	final public GameObject unlocker;

	public DoorUnlocked(GameObject actor) {
		super(actor);
		door = (Door) actor;

		unlocker = (GameObject) NWScript.getLastUnlocked();
	}
}
