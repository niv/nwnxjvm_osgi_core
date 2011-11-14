package es.elv.nwnx2.jvm.events.game;

import es.elv.nwnx2.jvm.hierarchy.Door;
import es.elv.nwnx2.jvm.hierarchy.GameObject;

public class DoorSpellCastAt extends ObjectSpellCastAt<Door> {
	final public Door door;

	public DoorSpellCastAt(GameObject actor) {
		super(actor);

		door = (Door) actor;
	}
}
