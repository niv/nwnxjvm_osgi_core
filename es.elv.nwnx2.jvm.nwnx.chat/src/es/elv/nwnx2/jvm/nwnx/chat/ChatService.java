package es.elv.nwnx2.jvm.nwnx.chat;

import es.elv.nwnx2.jvm.hierarchy.GameObject;

public interface ChatService {
	public enum Channel {
		TALK,
		SHOUT,
		WHSIPER,
		SERVER,
		PARTY,
		SILENT_SHOUT,
		DM
	}

	public int CHANNEL_TALK = 1;
	public int CHANNEL_SHOUT = 2;
	public int CHANNEL_WHISPER = 3;
	public int CHANNEL_PRIVATE = 4;
	public int CHANNEL_SERVER = 5;
	public int CHANNEL_PARTY = 6;
	public int CHANNEL_SILENT_SHOUT = 13;
	public int CHANNEL_DM = 14;
	public int CHANNEL_MODE_DM = 16;

	public void sendToChannel(GameObject speaker, Channel channel, String message);
	public void sendPrivateMessage(GameObject speaker, GameObject to, String message);
}
