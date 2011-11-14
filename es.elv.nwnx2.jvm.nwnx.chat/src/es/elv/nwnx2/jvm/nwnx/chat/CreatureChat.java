package es.elv.nwnx2.jvm.nwnx.chat;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;

abstract public class CreatureChat extends ObjectChat {
	final public Creature creature;

	public CreatureChat(GameObject actor, String text) {
		super(actor, text);

		creature = (Creature) actor;
	}


}
