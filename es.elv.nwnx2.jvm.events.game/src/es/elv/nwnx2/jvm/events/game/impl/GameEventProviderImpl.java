package es.elv.nwnx2.jvm.events.game.impl;

import java.util.LinkedList;
import java.util.List;

import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.events.game.AreaEnter;
import es.elv.nwnx2.jvm.events.game.AreaLeave;
import es.elv.nwnx2.jvm.events.game.CreatureAttacked;
import es.elv.nwnx2.jvm.events.game.CreatureBlocked;
import es.elv.nwnx2.jvm.events.game.CreatureConversation;
import es.elv.nwnx2.jvm.events.game.CreatureDamaged;
import es.elv.nwnx2.jvm.events.game.CreatureDeath;
import es.elv.nwnx2.jvm.events.game.CreatureDisturbed;
import es.elv.nwnx2.jvm.events.game.CreatureEndOfRound;
import es.elv.nwnx2.jvm.events.game.CreatureHears;
import es.elv.nwnx2.jvm.events.game.CreatureHeartbeat;
import es.elv.nwnx2.jvm.events.game.CreatureNoLongerHears;
import es.elv.nwnx2.jvm.events.game.CreatureNoLongerSees;
import es.elv.nwnx2.jvm.events.game.CreatureRest;
import es.elv.nwnx2.jvm.events.game.CreatureSees;
import es.elv.nwnx2.jvm.events.game.CreatureSpawn;
import es.elv.nwnx2.jvm.events.game.CreatureSpellCastAt;
import es.elv.nwnx2.jvm.events.game.DoorAttacked;
import es.elv.nwnx2.jvm.events.game.DoorClick;
import es.elv.nwnx2.jvm.events.game.DoorClosed;
import es.elv.nwnx2.jvm.events.game.DoorDamaged;
import es.elv.nwnx2.jvm.events.game.DoorDeath;
import es.elv.nwnx2.jvm.events.game.DoorFailToOpen;
import es.elv.nwnx2.jvm.events.game.DoorLocked;
import es.elv.nwnx2.jvm.events.game.DoorOpen;
import es.elv.nwnx2.jvm.events.game.DoorSpellCastAt;
import es.elv.nwnx2.jvm.events.game.DoorUnlocked;
import es.elv.nwnx2.jvm.events.game.ItemAcquired;
import es.elv.nwnx2.jvm.events.game.ItemActivated;
import es.elv.nwnx2.jvm.events.game.ItemEquipped;
import es.elv.nwnx2.jvm.events.game.ItemSpellCastAt;
import es.elv.nwnx2.jvm.events.game.ItemUnacquired;
import es.elv.nwnx2.jvm.events.game.ItemUnequipped;
import es.elv.nwnx2.jvm.events.game.ModuleHeartbeat;
import es.elv.nwnx2.jvm.events.game.ModuleLoad;
import es.elv.nwnx2.jvm.events.game.PlayerCutsceneAbort;
import es.elv.nwnx2.jvm.events.game.PlayerDeath;
import es.elv.nwnx2.jvm.events.game.PlayerDying;
import es.elv.nwnx2.jvm.events.game.PlayerLevelup;
import es.elv.nwnx2.jvm.events.game.PlayerLogin;
import es.elv.nwnx2.jvm.events.game.PlayerLogout;
import es.elv.nwnx2.jvm.events.game.PlayerRespawn;
import es.elv.nwnx2.jvm.events.game.PlayerRest;
import es.elv.nwnx2.jvm.events.game.TransitionClick;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;
import es.elv.nwnx2.jvm.hierarchy.events.GameEventProvider;

public class GameEventProviderImpl extends GameEventProvider {
	public GameEventProviderImpl() {
		setStaticEventClass("module_hb", ModuleHeartbeat.class);
		setStaticEventClass("module_load", ModuleLoad.class);

		setStaticEventClass("transition_click", TransitionClick.class);
		setStaticEventClass("area_enter", AreaEnter.class);
		setStaticEventClass("area_leave", AreaLeave.class);

		setStaticEventClass("creature_spawn", CreatureSpawn.class);
		setStaticEventClass("creature_hb", CreatureHeartbeat.class);
		setStaticEventClass("creature_attacked", CreatureAttacked.class);
		setStaticEventClass("creature_conversation", CreatureConversation.class);
		setStaticEventClass("creature_damaged", CreatureDamaged.class);
		setStaticEventClass("creature_death", CreatureDeath.class);
		setStaticEventClass("creature_blocked", CreatureBlocked.class);
		setStaticEventClass("creature_disturbed", CreatureDisturbed.class);
		setStaticEventClass("creature_endround", CreatureEndOfRound.class);
		setStaticEventClass("creature_rest", CreatureRest.class);
		setStaticEventClass("creature_spell_cast_at", CreatureSpellCastAt.class);
		setEventResolver("creature_perceive", new ClassResolver() {
			@Override
			public List<GameEvent<? extends GameObject>> apply(GameObject actor) {
				List<GameEvent<? extends GameObject>> ret =
					new LinkedList<GameEvent<? extends GameObject>>();

				if (NWScript.getLastPerceptionHeard())
					ret.add(new CreatureHears(actor));
				if (NWScript.getLastPerceptionInaudible())
					ret.add(new CreatureNoLongerHears(actor));
				if (NWScript.getLastPerceptionSeen())
					ret.add(new CreatureSees(actor));
				if (NWScript.getLastPerceptionVanished())
					ret.add(new CreatureNoLongerSees(actor));

				return ret;
			}
		});

		setStaticEventClass("player_login", PlayerLogin.class);
		setStaticEventClass("player_logout", PlayerLogout.class);
		setStaticEventClass("player_dying", PlayerDying.class);
		setStaticEventClass("player_death", PlayerDeath.class);
		setStaticEventClass("player_rest", PlayerRest.class);
		setStaticEventClass("player_levelup", PlayerLevelup.class);
		setStaticEventClass("player_respawn", PlayerRespawn.class);
		setStaticEventClass("player_cutscene_abort", PlayerCutsceneAbort.class);

		setStaticEventClass("item_acquire", ItemAcquired.class);
		setStaticEventClass("item_unacquire", ItemUnacquired.class);
		setStaticEventClass("item_equip", ItemEquipped.class);
		setStaticEventClass("item_unequip", ItemUnequipped.class);
		setStaticEventClass("item_spell_cast_at", ItemSpellCastAt.class);
		setStaticEventClass("item_activate", ItemActivated.class);

		setStaticEventClass("door_click", DoorClick.class);
		setStaticEventClass("door_attacked", DoorAttacked.class);
		setStaticEventClass("door_closed", DoorClosed.class);
		setStaticEventClass("door_damaged", DoorDamaged.class);
		setStaticEventClass("door_death", DoorDeath.class);
		setStaticEventClass("door_failtoopen", DoorFailToOpen.class);
		setStaticEventClass("door_lock", DoorLocked.class);
		setStaticEventClass("door_open", DoorOpen.class);
		setStaticEventClass("door_spell_cast_at", DoorSpellCastAt.class);
		setStaticEventClass("door_unlock", DoorUnlocked.class);
	}
}
