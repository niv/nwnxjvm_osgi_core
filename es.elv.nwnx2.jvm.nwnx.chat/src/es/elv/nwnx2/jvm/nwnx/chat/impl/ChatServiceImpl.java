package es.elv.nwnx2.jvm.nwnx.chat.impl;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Module;
import es.elv.nwnx2.jvm.nwnx.chat.ChatService;
import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

public class ChatServiceImpl implements ChatService {
	final private NWNXPlugin chatPlugin = new NWNXPlugin("chat", 8 * 128);

	@Override
	public void sendPrivateMessage(GameObject speaker, GameObject to,
			String message) {

		if (speaker == null || !speaker.valid())
			return;
		if (to == null || !to.valid())
			return;

		String t = String.format("%x¬%x¬%d¬%s", speaker.getObjectId(),
				to.getObjectId(), 4, ChatService.CHANNEL_PRIVATE);

		Module.getInstance().setInt("chat_last_originator_osgi", 1);
		
		chatPlugin.callMethod(speaker, "SPEAK", t);
	}

	@Override
	public void sendToChannel(GameObject speaker, Channel channel,
			String message) {

		if (speaker == null || !speaker.valid())
			return;

		int mode;

		switch (channel) {
			case TALK: mode = ChatService.CHANNEL_TALK; break;
			case WHSIPER: mode = ChatService.CHANNEL_WHISPER; break;
			case PARTY: mode = ChatService.CHANNEL_PARTY; break;
			case SERVER: mode = ChatService.CHANNEL_SERVER; break;
			case DM: mode = ChatService.CHANNEL_DM; break;
			case SHOUT: mode = ChatService.CHANNEL_SHOUT; break;
			case SILENT_SHOUT: mode = ChatService.CHANNEL_SILENT_SHOUT; break;

			default: return;
		}

		String t = String.format("%x¬%x¬%d¬%s", speaker.getObjectId(),
				0, mode, ChatService.CHANNEL_PRIVATE);

		Module.getInstance().setInt("chat_last_originator_osgi", 1);
		
		chatPlugin.callMethod(speaker, "SPEAK", t);
	}
}
