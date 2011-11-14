package es.elv.nwnx2.jvm.events.combatlog.impl;


import es.elv.nwnx2.jvm.events.combatlog.CombatLogMessage;
import es.elv.nwnx2.jvm.hierarchy.events.GameEventProvider;

public class GameEventProviderImpl extends GameEventProvider {
	public GameEventProviderImpl() {
		setStaticEventClass("combatlog_message", CombatLogMessage.class);
	}
}
