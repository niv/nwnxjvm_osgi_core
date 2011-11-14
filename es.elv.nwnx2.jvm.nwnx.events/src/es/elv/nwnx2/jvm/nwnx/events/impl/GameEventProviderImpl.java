package es.elv.nwnx2.jvm.nwnx.events.impl;

import java.util.LinkedList;
import java.util.List;

import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWVector;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Item;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;
import es.elv.nwnx2.jvm.hierarchy.events.GameEventProvider;
import es.elv.nwnx2.jvm.nwnx.events.CreatureAttack;
import es.elv.nwnx2.jvm.nwnx.events.CreatureCastSpell;
import es.elv.nwnx2.jvm.nwnx.events.CreatureExamineObject;
import es.elv.nwnx2.jvm.nwnx.events.CreaturePossessFamiliar;
import es.elv.nwnx2.jvm.nwnx.events.CreatureQuickchat;
import es.elv.nwnx2.jvm.nwnx.events.CreatureToggleMode;
import es.elv.nwnx2.jvm.nwnx.events.CreatureTogglePause;
import es.elv.nwnx2.jvm.nwnx.events.CreatureUseFeat;
import es.elv.nwnx2.jvm.nwnx.events.CreatureUseSkill;
import es.elv.nwnx2.jvm.nwnx.events.ItemActivate;
import es.elv.nwnx2.jvm.nwnx.events.PlayerCreatureSaveCharacter;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

public class GameEventProviderImpl extends GameEventProvider {
	final private NWNXPlugin events = new NWNXPlugin("events", 128);

	public static final int NWNXEVENT_TYPE_ALL               = 0;
	public static final int NWNXEVENT_TYPE_SAVE_CHAR         = 1;
	public static final int NWNXEVENT_TYPE_PICKPOCKET        = 2;
	public static final int NWNXEVENT_TYPE_ATTACK            = 3;
	public static final int NWNXEVENT_TYPE_USE_ITEM          = 4;
	public static final int NWNXEVENT_TYPE_QUICKCHAT         = 5;
	public static final int NWNXEVENT_TYPE_EXAMINE           = 6;
	public static final int NWNXEVENT_TYPE_USE_SKILL         = 7;
	public static final int NWNXEVENT_TYPE_USE_FEAT          = 8;
	public static final int NWNXEVENT_TYPE_TOGGLE_MODE       = 9;
	public static final int NWNXEVENT_TYPE_CAST_SPELL        = 10;
	public static final int NWNXEVENT_TYPE_TOGGLE_PAUSE      = 11;
	public static final int NWNXEVENT_TYPE_POSSESS_FAMILIAR  = 12;

	public GameEventProviderImpl() {
		setEventResolver("nwnx_event", resolver);
	}

	private ClassResolver resolver = new ClassResolver() {

		@Override
		public List<GameEvent<? extends GameObject>> apply(GameObject actor) {

			List<GameEvent<? extends GameObject>> ret =
				new LinkedList<GameEvent<? extends GameObject>>();

			final int eventType = events.callIntMethod(actor, "GET_EVENT_ID");
			final int eventSubType = events.callIntMethod(actor, "GET_EVENT_SUBID");
			final GameObject on = (GameObject) events.callObjectMethod(actor, "TARGET");
			final NWVector atv = events.callVectorMethod(actor, "GET_EVENT_POSITION");
			final NWLocation at = new NWLocation(actor.getLocation().getArea(),
					atv.getX(), atv.getY(), atv.getZ(), actor.getLocation().getFacing());

			switch (eventType) {
				case NWNXEVENT_TYPE_SAVE_CHAR:
					ret.add(new PlayerCreatureSaveCharacter(actor));
					break;

				case NWNXEVENT_TYPE_ATTACK:
					ret.add(new CreatureAttack(events, actor, on));
					break;

				case NWNXEVENT_TYPE_EXAMINE:
					ret.add(new CreatureExamineObject(events, actor, on));
					break;

				case NWNXEVENT_TYPE_QUICKCHAT:
					ret.add(new CreatureQuickchat(events, actor, eventSubType));
					break;

				case NWNXEVENT_TYPE_USE_SKILL:
					ret.add(new CreatureUseSkill(events, actor, eventSubType, on, at));
					break;

				case NWNXEVENT_TYPE_USE_FEAT:
					ret.add(new CreatureUseFeat(events, actor, eventSubType, on, at));
					break;

				case NWNXEVENT_TYPE_TOGGLE_MODE:
					ret.add(new CreatureToggleMode(events, actor, eventSubType));
					break;

				case NWNXEVENT_TYPE_USE_ITEM:
					final Item item = (Item) events.callObjectMethod(actor, "ITEM");
					ret.add(new ItemActivate(events, actor, item, on, at));
					break;

				case NWNXEVENT_TYPE_CAST_SPELL:
					ret.add(new CreatureCastSpell(events, actor, eventSubType, on, at));
					break;

				case NWNXEVENT_TYPE_TOGGLE_PAUSE:
					ret.add(new CreatureTogglePause(events, actor));
					break;

				case NWNXEVENT_TYPE_POSSESS_FAMILIAR:
					ret.add(new CreaturePossessFamiliar(events, actor));
					break;

				default:
					log.debug("Unhandled NWNX event: " + eventType + ":" + eventSubType);
					break;
			}

			return ret;
		}

	};
}
