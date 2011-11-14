package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Item;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class ItemActivated extends GameEvent<Item> {
	final public Item item;
	final public Creature by;
	final public GameObject on;
	final public NWLocation at;

	public ItemActivated(GameObject actor) {
		super(actor);

		item = (Item) NWScript.getItemActivated();
	    by = (Creature) NWScript.getItemActivator();
	    on = (GameObject) NWScript.getItemActivatedTarget();
	    at = NWScript.getItemActivatedTargetLocation();
	}
}
