package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Transition;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class TransitionClick extends GameEvent<Transition> {
	final public Transition transition;
	final public Creature clicker;

	public TransitionClick(GameObject actor) {
		super(actor);

		transition = (Transition) actor;
		clicker = (Creature) NWScript.getClickingObject();
	}
}
