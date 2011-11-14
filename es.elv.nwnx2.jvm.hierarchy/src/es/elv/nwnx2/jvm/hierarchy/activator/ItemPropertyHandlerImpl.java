package es.elv.nwnx2.jvm.hierarchy.activator;

import org.nwnx.nwnx2.jvm.NWItemProperty;
import org.nwnx.nwnx2.jvm.NWItemProperty.ItemPropertyHandler;

import es.elv.nwnx2.jvm.hierarchy.ItemProperty;
import es.elv.nwnx2.jvm.hierarchy.bundles.Feature;

public class ItemPropertyHandlerImpl extends Feature implements ItemPropertyHandler {

	@Override
	protected void load() throws Exception {
		NWItemProperty.registerItemPropertyHandler(this);
	}

	@Override
	protected void unload() throws Exception {
		NWItemProperty.unregisterItemPropertyHandler(this);
	}

	@Override
	public NWItemProperty handleItemPropertyClass(NWItemProperty eff) {
		return new ItemProperty(eff.getItemPropertyId());
	}

}
