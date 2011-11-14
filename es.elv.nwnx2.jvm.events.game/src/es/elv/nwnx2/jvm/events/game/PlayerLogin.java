package es.elv.nwnx2.jvm.events.game;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.PlayerCreature;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class PlayerLogin extends GameEvent<PlayerCreature> {
	final public PlayerCreature player;

	public PlayerLogin(GameObject actor) {
		super(actor);

		player = (PlayerCreature) actor;

		player.updateLastLoginDate();
	}
}
