package es.elv.nwnx2.jvm.events.game;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Item;

public class ItemSpellCastAt extends ObjectSpellCastAt<Item> {
	public final Item item;

	public ItemSpellCastAt(GameObject actor) {
		super(actor);

		item = (Item) object;
	}
}
