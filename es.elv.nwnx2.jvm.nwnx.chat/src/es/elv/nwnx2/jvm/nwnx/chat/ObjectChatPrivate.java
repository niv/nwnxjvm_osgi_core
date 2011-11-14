package es.elv.nwnx2.jvm.nwnx.chat;

import es.elv.nwnx2.jvm.hierarchy.GameObject;

public class ObjectChatPrivate extends ObjectChat {
	public final GameObject to;

	public ObjectChatPrivate(GameObject actor, GameObject to, String text) {
		super(actor, text);
		this.to = to;
	}
}
