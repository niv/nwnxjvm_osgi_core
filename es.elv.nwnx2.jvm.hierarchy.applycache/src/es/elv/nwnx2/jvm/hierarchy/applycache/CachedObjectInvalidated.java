package es.elv.nwnx2.jvm.hierarchy.applycache;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.events.CoreEvent;

/**
 * Sent out when a GameObject instance gets removed from the cache.
 */
public class CachedObjectInvalidated extends CoreEvent {
	public final GameObject object;

	public CachedObjectInvalidated(GameObject gameObject) {
		this.object = gameObject;
	}

}
