package es.elv.nwnx2.jvm.hierarchy;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class ItemFactory<T extends Item> extends GameObjectFactory<T> {
	public ItemFactory(int objectType) {
		super(objectType);
	}

	@SuppressWarnings("unchecked")
	public T create(NWObject onInventory, String resRef, String tag, int nStackSize) {
		return (T) NWScript.createItemOnObject(resRef, onInventory, nStackSize, tag);
	}

	public T create(NWObject onInventory, String resRef, String tag) {
		return create(onInventory, resRef, tag, 1);
	}

	public T create(NWObject onInventory, String resRef) {
		return create(onInventory, resRef, "", 1);
	}

}
