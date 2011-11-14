package es.elv.nwnx2.jvm.hierarchy;

import org.nwnx.nwnx2.jvm.NWLocation;
import org.nwnx.nwnx2.jvm.NWScript;

public class GameObjectFactory<T extends GameObject> {
	private final int objectType;
	public GameObjectFactory(int objectType) {
		this.objectType = objectType;
	}

	@SuppressWarnings("unchecked")
	public T create(NWLocation at, String sTemplate, String tag) {
		return (T) NWScript.createObject(objectType, sTemplate, at, false, tag);
	}

	public T create(NWLocation at, String sTemplate) {
		return create(at, sTemplate, "");
	}
}
