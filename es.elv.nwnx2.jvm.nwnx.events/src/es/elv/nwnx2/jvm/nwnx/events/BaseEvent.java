package es.elv.nwnx2.jvm.nwnx.events;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

abstract class BaseEvent extends GameEvent<Creature> {
	final private NWNXPlugin events;

	final public Creature creature;

	public BaseEvent(NWNXPlugin events, GameObject actor) {
		super(actor);

		this.events = events;
		this.creature = (Creature) actor;
	}

	private boolean suppressed = false;

	public void suppress() {
		if (!suppressed) {
			events.callMethod(actor, "BYPASS", "1");
			suppressed = true;
		}
	}

	public boolean isSuppressed() {
		return suppressed;
	}

}
