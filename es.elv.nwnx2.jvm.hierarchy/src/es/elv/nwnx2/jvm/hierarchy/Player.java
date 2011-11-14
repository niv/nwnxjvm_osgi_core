package es.elv.nwnx2.jvm.hierarchy;

import java.util.LinkedList;
import java.util.List;

import org.nwnx.nwnx2.jvm.NWScript;

public class Player extends PlayerCreature {

	public Player(int oid) {
		super(oid);
	}

	public void giveXP(int amount) {
		NWScript.giveXPToCreature(this, amount);
	}


	/**
	 * Returns an array of all Players online (excluding DMs).
	 */
	public static Player[] all() {
		List<Player> ret = new LinkedList<Player>();
		for (PlayerCreature p : PlayerCreature.all())
			if (p instanceof Player)
				ret.add((Player) p);
		return ret.toArray(new Player[ret.size()]);
	}
}
