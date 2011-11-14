package es.elv.nwnx2.jvm.nwnx.chat;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

abstract public class ObjectChat extends GameEvent<GameObject> {
	final public GameObject object;
	final public String text;

	public ObjectChat(GameObject actor, String text) {
		super(actor);

		object = actor;
		this.text = text;
	}

	private boolean suppressed = false;

	public void suppress() {
		if (!suppressed)
			NWScript.setLocalString(actor, "NWNX!CHAT!SUPRESS", "1");
		suppressed = true;
	}

	public boolean isSuppressed() {
		return suppressed;
	}
}
