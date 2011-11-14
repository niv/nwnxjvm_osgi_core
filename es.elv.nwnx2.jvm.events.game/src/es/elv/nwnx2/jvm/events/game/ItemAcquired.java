package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.ObjectType;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Item;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class ItemAcquired extends GameEvent<Item> {

	final public Item item;
	final public GameObject by;
	final public GameObject from;
	final public int stackSize;
	final public boolean firstLogin;

	public ItemAcquired(GameObject actor) {
		super(actor);

		item = (Item) NWScript.getModuleItemAcquired();
		by = (GameObject) NWScript.getModuleItemAcquiredBy();
		from = (GameObject) NWScript.getModuleItemAcquiredFrom();
		stackSize = NWScript.getModuleItemAcquiredStackSize();
		firstLogin = NWScript.getModuleItemAcquiredBy().objectType() == ObjectType.INVALID;
	}
}
