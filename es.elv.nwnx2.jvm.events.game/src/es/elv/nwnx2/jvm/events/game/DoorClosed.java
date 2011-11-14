package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Door;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class DoorClosed extends GameEvent<Door> {
	final public Door door;
	final public GameObject closer;

	public DoorClosed(GameObject actor) {
		super(actor);

		door = (Door) actor;
		closer = (GameObject) NWScript.getLastClosedBy();
	}
}
