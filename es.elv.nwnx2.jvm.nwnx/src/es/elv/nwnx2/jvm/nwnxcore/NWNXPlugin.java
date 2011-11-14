package es.elv.nwnx2.jvm.nwnxcore;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.NWVector;

public class NWNXPlugin {
	private final String nwnxKey;

	private final String nwnxBuffer;

	public NWNXPlugin(String key, int bufSize) {
		nwnxKey = key.toUpperCase();
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < bufSize; i++)
			b.append(" ");
		nwnxBuffer = b.toString();
	}

	private String getForKey(String key, String ... arguments) {
		String va = "";
		if (arguments.length > 0) {
			for (String a : arguments)
				va += "!" + a;
		}

		return "NWNX!" + nwnxKey + "!" + key + va;
	}

	private String setGet(NWObject obj, String key, String value) {
		NWScript.setLocalString(obj, getForKey(key), value);
		return NWScript.getLocalString(obj, getForKey(key));
	}

	public void callMethod(NWObject obj, String key, String value) {
		NWScript.setLocalString(obj, getForKey(key), value);
	}

	public NWObject callObjectMethod(NWObject obj, String key, String ... arguments) {
		return NWScript.getLocalObject(obj, getForKey(key));
	}

	public String callStringMethod(NWObject obj, String key, String ... arguments) {
		return setGet(obj, key, nwnxBuffer);
	}

	public int callIntMethod(NWObject obj, String key, String ... arguments) {
		try {
			return Integer.parseInt(callStringMethod(obj, key));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public float callFloatMethod(NWObject obj, String key, String ... arguments) {
		try {
			return Float.parseFloat(callStringMethod(obj, key));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public NWVector callVectorMethod(NWObject obj, String key, String ... arguments) {
		try {
			return new NWVector(callStringMethod(obj, key));
		} catch (NumberFormatException e) {
			return NWVector.ORIGIN;
		} catch (IllegalArgumentException e) {
			return NWVector.ORIGIN;
		}
	}
}
