package es.elv.nwnx2.jvm.hierarchy;

import java.util.LinkedList;
import java.util.List;

public class DM extends PlayerCreature {

	public DM(int oid) {
		super(oid);
	}

	/**
	 * Returns an array of all DMs online (excluding regular Players).
	 */
	public static DM[] all() {
		List<DM> ret = new LinkedList<DM>();
		for (PlayerCreature p : PlayerCreature.all())
			if (p instanceof DM)
				ret.add((DM) p);
		return ret.toArray(new DM[ret.size()]);
	}
}
