package es.elv.nwnx2.jvm.nwnx.chat;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;

public class CreatureChatPrivate extends ObjectChat {
	public final GameObject to;
	public final Creature creature;

	public CreatureChatPrivate(GameObject actor, GameObject to, String text) {
		super(actor, text);

		this.to = to;
		this.creature = (Creature) actor;
	}
}
