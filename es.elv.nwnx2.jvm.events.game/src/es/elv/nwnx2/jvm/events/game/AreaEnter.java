package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Area;
import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class AreaEnter extends GameEvent<Area> {
	final public Area area;
	final public Creature entering;

	public AreaEnter(GameObject actor) {
		super(actor);
		area = (Area) actor;
		entering = (Creature) NWScript.getEnteringObject();
	}
}
