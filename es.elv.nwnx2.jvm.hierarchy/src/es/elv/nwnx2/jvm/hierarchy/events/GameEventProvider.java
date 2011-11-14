package es.elv.nwnx2.jvm.hierarchy.events;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.nwnx.nwnx2.jvm.NWObject;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.bundles.Feature;

/**
 * A GameEventProvider can be extended upon to map nwnxjvm-events to
 * GameEvent classes.
 */
abstract public class GameEventProvider extends Feature implements EventResolverService {
	public interface ClassResolver {
		List<GameEvent<? extends GameObject>> apply(GameObject actor);
	}

	private Map<String, String> eventAliases =
		new HashMap<String, String>();

	private Map<String, Class<? extends GameEvent<? extends GameObject>>> staticMap =
		new HashMap<String, Class<? extends GameEvent<? extends GameObject>>>();

	private Map<String, ClassResolver> resolverMap =
		new HashMap<String, ClassResolver>();

	@Override
	public List<GameEvent<? extends GameObject>> resolve(GameObject objSelf,
			String event) throws EventResolveException {

		List<GameEvent<? extends GameObject>> ret =
			new LinkedList<GameEvent<? extends GameObject>>();

		if (eventAliases.containsKey(event))
			event = eventAliases.get(event);

		if (staticMap.containsKey(event))
			ret.add(factoryEvent(objSelf, staticMap.get(event)));
		if (resolverMap.containsKey(event))
			ret.addAll(resolverMap.get(event).apply(objSelf));

		return ret;
	}

	private GameEvent<?> factoryEvent(NWObject objSelf,
			Class<? extends GameEvent<?>> klass) throws EventResolveException {

		try {
			Constructor<? extends GameEvent<?>> z = klass.getConstructor(GameObject.class);
			return z.newInstance(objSelf);

		} catch (NoSuchMethodException e) {
			throw new EventResolveException("while factoring " + klass + " for " + objSelf, e);
		} catch (IllegalArgumentException e) {
			throw new EventResolveException("while factoring " + klass + " for " + objSelf, e);
		} catch (InstantiationException e) {
			throw new EventResolveException("while factoring " + klass + " for " + objSelf, e);
		} catch (IllegalAccessException e) {
			throw new EventResolveException("while factoring " + klass + " for " + objSelf, e);
		} catch (InvocationTargetException e) {
			throw new EventResolveException("while factoring " + klass + " for " + objSelf, e);
		}
	}

	protected void setEventAlias(String originalEvent, String alias) {
		eventAliases.put(originalEvent, alias);
	}

	protected void setStaticEventClass(String eventName, Class<? extends GameEvent<? extends GameObject>> eventClass) {
		staticMap.put(eventName, eventClass);
	}

	protected void setEventResolver(String eventName, ClassResolver resolver) {
		resolverMap.put(eventName, resolver);
	}
}
