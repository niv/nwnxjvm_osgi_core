package es.elv.nwnx2.jvm.nwnx.events;

import org.nwnx.nwnx2.jvm.NWObject;

import es.elv.nwnx2.jvm.nwnxcore.NWNXPlugin;

public class ConversationUtil {
	private ConversationUtil() { }

	private final static NWNXPlugin p =
		new NWNXPlugin("events", 128);

	public static final int LANGUAGE_ENGLISH = 0;
	public static final int LANGUAGE_FRENCH = 1;
	public static final int LANGUAGE_GERMAN = 2;
	public static final int LANGUAGE_ITALIAN = 3;
	public static final int LANGUAGE_SPANISH = 4;
	public static final int LANGUAGE_POLISH = 5;
	public static final int LANGUAGE_KOREAN = 128;
	public static final int LANGUAGE_CHINESE_TRADITIONAL = 129;
	public static final int LANGUAGE_CHINESE_SIMPLIFIED = 130;
	public static final int LANGUAGE_JAPANESE = 131;

	public enum NodeType {
		START, ENTRY, REPLY
	}

	public enum Gender {
		MALE, FEMALE;

		public int toInt() {
			if (Gender.this.equals(MALE))
				return 0;
			else
				return 1;
		}
	}

	public static NodeType getCurrentNodeType() {
		int t = p.callIntMethod(NWObject.MODULE, "GET_NODE_TYPE");
		switch (t) {
			case 0:
				return NodeType.START;
			case 1:
				return NodeType.ENTRY;
			case 2:
				return NodeType.REPLY;
			default:
				throw new IllegalArgumentException("Invalid return value from GET_NODE_TYPE: " + t);
		}
	}

	public static int getCurrentNodeId() {
		return p.callIntMethod(NWObject.MODULE, "GET_NODE_ID");
	}

	public static int getCurrentAbsoluteNodeId() {
		return p.callIntMethod(NWObject.MODULE, "GET_ABSOLUTE_NODE_ID");
	}

	public static String getCurrentNodeText(int lang) {
		return getCurrentNodeText(lang, Gender.MALE);
	}

	public static String getCurrentNodeText(int lang, Gender gender) {
		if (gender != Gender.FEMALE)
			gender = Gender.MALE;
		return p.callStringMethod(NWObject.MODULE, "GET_NODE_TEXT",
				Integer.toString(lang * 2 + gender.toInt()));
	}

	public static void setCurrentNodeText(String text, int lang) {
		setCurrentNodeText(text, lang, Gender.MALE);
	}

	public static void setCurrentNodeText(String text, int lang, Gender gender) {
		if (gender != Gender.FEMALE)
			gender = Gender.MALE;

		p.callMethod(NWObject.MODULE, "SET_NODE_TEXT",
				Integer.toString(lang * 2 + gender.toInt()) + "¬" + text);
	}

	public static int getSelectedNodeId() {
		return p.callIntMethod(NWObject.MODULE, "GET_SELECTED_NODE_ID");
	}

	public static int getSelectedAbsoluteNodeId() {
		return p.callIntMethod(NWObject.MODULE, "GET_SELECTED_ABSOLUTE_NODE_ID");
	}

	public static String getSelectedNodeText(int lang) {
		return getSelectedNodeText(lang, Gender.MALE);
	}

	public static String getSelectedNodeText(int lang, Gender gender) {
		if (gender != Gender.FEMALE)
			gender = Gender.MALE;
		return p.callStringMethod(NWObject.MODULE, "GET_SELECTED_NODE_TEXT",
				Integer.toString(lang * 2 + gender.toInt()));

	}
}
