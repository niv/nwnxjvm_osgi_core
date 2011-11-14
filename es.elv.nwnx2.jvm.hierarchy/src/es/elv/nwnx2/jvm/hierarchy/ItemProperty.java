package es.elv.nwnx2.jvm.hierarchy;

import org.nwnx.nwnx2.jvm.NWItemProperty;
import org.nwnx.nwnx2.jvm.NWScript;

public class ItemProperty extends NWItemProperty {
	private Item item;


	public ItemProperty(int oid) {
		super(oid);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void remove() {
		item.removeItemProperty(this);
	}

	public int getType() {
		return NWScript.getItemPropertyType(this);
	}

	public int getSubType() {
		return NWScript.getItemPropertySubType(this);
	}

	public int getDurationType() {
		return NWScript.getItemPropertyDurationType(this);
	}

	public int getParam1() {
		return NWScript.getItemPropertyParam1(this);
	}
	public int getParam1Value() {
		return NWScript.getItemPropertyParam1Value(this);
	}

	public int getCostTable() {
		return NWScript.getItemPropertyCostTable(this);
	}
	public int getCostTableValue() {
		return NWScript.getItemPropertyCostTableValue(this);
	}

}
