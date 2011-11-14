package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Area;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class AreaLeave extends GameEvent<Area> {
	final public Area area;
	final public GameObject leaving;

	public AreaLeave(GameObject actor) {
		super(actor);
		area = (Area) actor;
		leaving = (GameObject) NWScript.getExitingObject();
	}
}
