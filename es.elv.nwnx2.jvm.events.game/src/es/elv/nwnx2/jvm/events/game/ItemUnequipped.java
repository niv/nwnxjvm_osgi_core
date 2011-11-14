package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Item;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class ItemUnequipped extends GameEvent<Item> {
	final public Item item;
	final public Creature by;

	public ItemUnequipped(GameObject actor) {
		super(actor);

		item = (Item) NWScript.getPCItemLastUnequipped();
		by = (Creature) NWScript.getPCItemLastUnequippedBy();
	}
}
