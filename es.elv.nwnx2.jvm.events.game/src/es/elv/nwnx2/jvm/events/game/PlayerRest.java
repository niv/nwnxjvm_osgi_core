package es.elv.nwnx2.jvm.events.game;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.PlayerCreature;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class PlayerRest extends GameEvent<PlayerCreature> {
	final public PlayerCreature player;

	public PlayerRest(GameObject actor) {
		super(actor);

		player = (PlayerCreature) NWScript.getLastPCRested();
	}
}
