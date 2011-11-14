package es.elv.nwnx2.jvm.events.combatlog;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Module;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class CombatLogMessage extends GameEvent<Creature> {
	final public Creature creature;
	final public String message;
	
	public CombatLogMessage(GameObject actor) {
		super(actor);
		
		this.creature = (Creature) actor;
		this.message = Module.getInstance().getString("combatlog_message");
	}
}