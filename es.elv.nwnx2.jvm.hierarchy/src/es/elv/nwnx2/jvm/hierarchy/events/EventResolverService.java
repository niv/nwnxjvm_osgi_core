package es.elv.nwnx2.jvm.hierarchy.events;

import java.util.List;

import es.elv.nwnx2.jvm.hierarchy.GameObject;

public interface EventResolverService {
	public List<GameEvent<? extends GameObject>> resolve(GameObject objSelf, String event)
		throws EventResolveException;
}
