package es.elv.nwnx2.jvm.nwnx.events;

import org.nwnx.nwnx2.jvm.NWLocation;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Item;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

/**
 * Published when a item activate action is being added to a creatures action
 * queue. Can be suppressed.
 */
public class ItemActivate extends BaseEvent {
	final public Creature creature;
	final public Item item;
	final public GameObject on;
	final public NWLocation at;


	public ItemActivate(NWNXPlugin events, GameObject actor, Item item, GameObject on, NWLocation at) {
		super(events, actor);

		this.creature = (Creature) actor;
		this.item = item;
		this.on = on;
		this.at = at;
	}

}
