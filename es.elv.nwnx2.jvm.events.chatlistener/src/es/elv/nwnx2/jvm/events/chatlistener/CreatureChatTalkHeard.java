package es.elv.nwnx2.jvm.events.chatlistener;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

/**
 * Fires for each Creature that hears another Creature talking.
 */
public class CreatureChatTalkHeard extends GameEvent<Creature> {
	final public Creature listener;
	final public Creature speaker;
	final public String text;

	public CreatureChatTalkHeard(GameObject actor, Creature speaker, String text) {
		super(actor);

		this.listener = (Creature) actor;
		this.speaker = speaker;
		this.text = text;
	}

}
