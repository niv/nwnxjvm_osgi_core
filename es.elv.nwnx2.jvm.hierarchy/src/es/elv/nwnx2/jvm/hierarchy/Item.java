package es.elv.nwnx2.jvm.hierarchy;

import java.util.HashSet;
import java.util.Set;

import org.nwnx.nwnx2.jvm.NWItemProperty;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.constants.DurationType;
import org.nwnx.nwnx2.jvm.constants.ObjectType;


abstract public class Item extends GameObject {
	public static final ItemFactory<Item> factory =
		new ItemFactory<Item>(ObjectType.ITEM);

	public Item(int oid) {
		super(oid);
	}

	public void copy(NWObject toInventory) {
		NWScript.copyItem(this, toInventory, true);
	}

	public Set<ItemProperty> getItemProperties() {
		HashSet<ItemProperty> ret = new HashSet<ItemProperty>();
		for (NWItemProperty p : NWScript.getItemProperties(this)) {
			ItemProperty pp = (ItemProperty) p;
			pp.setItem(this);
			ret.add(pp);
		}
		return ret;
	}

	public void addPermanentItemProperty(ItemProperty prp) {
		NWScript.addItemProperty(DurationType.PERMANENT, prp, this, 0);
		prp.setItem(this);
	}

	public void addTemporaryItemProperty(ItemProperty prp, long duration) {
		NWScript.addItemProperty(DurationType.TEMPORARY, prp, this, duration);
		prp.setItem(this);
	}

	public void removeItemProperty(ItemProperty itemProperty) {
		NWScript.removeItemProperty(this, itemProperty);
	}

	public int getBaseType() {
		return NWScript.getBaseItemType(this);
	}
	public int getWeight() {
		return NWScript.getGoldPieceValue(this);
	}

	public int getGoldValue() {
		return NWScript.getGoldPieceValue(this);
	}

	public boolean isIdentified() {
		return NWScript.getIdentified(this);
	}
	public void setIdentified(boolean identified) {
		NWScript.setIdentified(this, identified);
	}
	public boolean isStolen() {
		return NWScript.getStolenFlag(this);
	}
	public void setStolen(boolean stolen) {
		NWScript.setStolenFlag(this, stolen);
	}

	public GameObject getPossessor() {
		NWObject ret = NWScript.getItemPossessor(this);
		return ret == null ? null : (GameObject) ret;
	}

}
