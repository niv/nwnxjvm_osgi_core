package es.elv.nwnx2.jvm.nwnx.chat;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;

public class CreatureChatSilentShout extends ObjectChat {
	public final Creature creature;

	public CreatureChatSilentShout(GameObject actor, String text) {
		super(actor, text);

		this.creature = (Creature) actor;
	}
}
