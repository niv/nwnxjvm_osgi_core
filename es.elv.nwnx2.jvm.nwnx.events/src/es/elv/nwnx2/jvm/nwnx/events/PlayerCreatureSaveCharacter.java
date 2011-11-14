package es.elv.nwnx2.jvm.nwnx.events;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.PlayerCreature;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

/**
 * When a player character is being saved.
 */
public class PlayerCreatureSaveCharacter extends GameEvent<PlayerCreature> {
	final public PlayerCreature player;

	public PlayerCreatureSaveCharacter(GameObject actor) {
		super(actor);

		this.player = (PlayerCreature) actor;
	}

}
