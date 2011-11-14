package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class CreatureConversation extends GameEvent<Creature> {
	final public Creature creature;
	final public Creature speaker;

	public CreatureConversation(GameObject actor) {
		super(actor);

		creature = (Creature) actor;
		speaker = (Creature) NWScript.getLastSpeaker();
	}
}
