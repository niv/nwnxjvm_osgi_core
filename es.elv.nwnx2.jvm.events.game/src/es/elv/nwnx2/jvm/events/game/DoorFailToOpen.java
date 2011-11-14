package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.Door;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class DoorFailToOpen extends GameEvent<Door> {
	final public Door door;
	final public Creature clicker;

	public DoorFailToOpen(GameObject actor) {
		super(actor);

		door = (Door) actor;
		clicker = (Creature) NWScript.getClickingObject();
	}
}
