package es.elv.nwnx2.jvm.nwnx.chat.impl;

import java.util.LinkedList;
import java.util.List;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

import es.elv.nwnx2.jvm.hierarchy.Creature;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Module;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;
import es.elv.nwnx2.jvm.hierarchy.events.GameEventProvider;
import es.elv.nwnx2.jvm.nwnx.chat.CreatureChatDM;
import es.elv.nwnx2.jvm.nwnx.chat.CreatureChatParty;
import es.elv.nwnx2.jvm.nwnx.chat.CreatureChatPrivate;
import es.elv.nwnx2.jvm.nwnx.chat.CreatureChatServer;
import es.elv.nwnx2.jvm.nwnx.chat.CreatureChatShout;
import es.elv.nwnx2.jvm.nwnx.chat.CreatureChatSilentShout;
import es.elv.nwnx2.jvm.nwnx.chat.CreatureChatTalk;
import es.elv.nwnx2.jvm.nwnx.chat.CreatureChatWhisper;
import es.elv.nwnx2.jvm.nwnx.chat.NWNXChatEventProvider;
import es.elv.nwnx2.jvm.nwnx.chat.ObjectChatDM;
import es.elv.nwnx2.jvm.nwnx.chat.ObjectChatParty;
import es.elv.nwnx2.jvm.nwnx.chat.ObjectChatPrivate;
import es.elv.nwnx2.jvm.nwnx.chat.ObjectChatServer;
import es.elv.nwnx2.jvm.nwnx.chat.ObjectChatShout;
import es.elv.nwnx2.jvm.nwnx.chat.ObjectChatSilentShout;
import es.elv.nwnx2.jvm.nwnx.chat.ObjectChatTalk;
import es.elv.nwnx2.jvm.nwnx.chat.ObjectChatWhisper;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

public class GameEventProviderImpl extends GameEventProvider implements NWNXChatEventProvider {
	final private NWNXPlugin chatPlugin = new NWNXPlugin("chat", 8 * 128);

	private NWObject i__NWNXChatGetPC(int id) {
		// This depends on the nwscript hooks running.
		return NWScript.getLocalObject(Module.getInstance(), "chatPC_" + id);
	}
	
	public GameEventProviderImpl() {

		setEventResolver("nwnx_chat", new ClassResolver() {

			public List<GameEvent<? extends GameObject>> apply(GameObject actor) {
				List<GameEvent<? extends GameObject>> ret = new LinkedList<GameEvent<? extends GameObject>>();
				
				// Suppress our own events?
				if (Module.getInstance().getInt("chat_last_originator_osgi") > 0) {
					Module.getInstance().setInt("chat_last_originator_osgi", 0);
					Module.getInstance().setInt("_ev_retvar", 1 << 2);
					return ret;
				}				
				
				final String allText = chatPlugin.callStringMethod(actor, "TEXT");
				int modeWithDM;
				int mode;
				try {
					modeWithDM = Integer.parseInt(allText.substring(0, 2).trim());
				} catch (NumberFormatException e) {
					return ret;
				}

				mode = modeWithDM > 16 ? modeWithDM - 16 : modeWithDM;

				GameObject to;
				try {
					final int toID = Integer.parseInt(allText.substring(2, 12).trim());
					to = mode == 4 ? (GameObject) i__NWNXChatGetPC(toID) /*NWObject.apply(toID)*/ : null;
				} catch (NumberFormatException e) {
					return ret;
				}

				final String text = allText.substring(12);

				switch (mode) {
					case 1: ret.add(new ObjectChatTalk(actor, text)); break;
					case 2: ret.add(new ObjectChatShout(actor, text)); break;
					case 3: ret.add(new ObjectChatWhisper(actor, text)); break;
					case 4: ret.add(new ObjectChatPrivate(actor, to, text)); break;
					case 5: ret.add(new ObjectChatServer(actor, text)); break;
					case 6: ret.add(new ObjectChatParty(actor, text)); break;
					case 13: ret.add(new ObjectChatSilentShout(actor, text)); break;
					case 14: ret.add(new ObjectChatDM(actor, text)); break;
				}

				if (actor instanceof Creature) {
					switch (mode) {
						case 1: ret.add(new CreatureChatTalk(actor, text)); break;
						case 2: ret.add(new CreatureChatShout(actor, text)); break;
						case 3: ret.add(new CreatureChatWhisper(actor, text)); break;
						case 4: ret.add(new CreatureChatPrivate(actor, to, text)); break;
						case 5: ret.add(new CreatureChatServer(actor, text)); break;
						case 6: ret.add(new CreatureChatParty(actor, text)); break;
						case 13: ret.add(new CreatureChatSilentShout(actor, text)); break;
						case 14: ret.add(new CreatureChatDM(actor, text)); break;
					}
				}

				return ret;
			}
		});
	}
}
