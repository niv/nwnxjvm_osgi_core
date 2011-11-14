package es.elv.nwnx2.jvm.hierarchy.bundles;

import org.nwnx.nwnx2.jvm.NWObject;

public interface ObjectClassService {
	public NWObject handleObjectClass(NWObject oid, boolean valid, int objectType, String resRef, String tag);
}
