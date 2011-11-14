package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Item;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class ItemUnacquired extends GameEvent<Item> {
	final public GameObject item;
	final public GameObject by;

	public ItemUnacquired(GameObject actor) {
		super(actor);

		item = (GameObject) NWScript.getModuleItemLost();
		by = (GameObject) NWScript.getModuleItemLostBy();
	}
}
