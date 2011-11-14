package es.elv.nwnx2.jvm.hierarchy;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

public class NPC extends Creature {

	public NPC(int oid) {
		super(oid);
	}

	public int getAILevel() {
		return NWScript.getAILevel(this);
	}
	public void setAILevel(int level) {
		NWScript.setAILevel(this, level);
	}

	public Creature getMaster() {
		NWObject ret = NWScript.getMaster(this);
		return ret == null ? null : (Creature) ret;
	}

}
