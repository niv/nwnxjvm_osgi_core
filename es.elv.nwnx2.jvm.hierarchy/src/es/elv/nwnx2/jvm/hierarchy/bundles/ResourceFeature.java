package es.elv.nwnx2.jvm.hierarchy.bundles;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWObject.ObjectHandler;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

import es.elv.nwnx2.jvm.hierarchy.GameObject;

public class ResourceFeature extends Feature implements ObjectClassService {

	private Set<ObjectHandler> handlers = new HashSet<ObjectHandler>();

	private ServiceRegistration<?> ocs;

	@Override
	protected void activate(ComponentContext ctxt) throws Exception {
		super.activate(ctxt);

		ocs = bundleContext.registerService(ObjectClassService.class.getName(), this, null);
	}

	@Override
	protected void deactivate(ComponentContext ctxt) throws Exception {
		super.deactivate(ctxt);

		ocs.unregister();
		handlers.clear();
	}

	@Override
	final public NWObject handleObjectClass(NWObject obj, boolean valid, int objectType, String resRef,
			String tag) {
		for (ObjectHandler h : handlers) {
			NWObject r = h.handleObjectClass(obj, valid, objectType, resRef, tag);
			if (r != null)
				return r;
		}
		return null;
	}

	/**
	 * Registers a NWObject.ObjectHandler that will be unregistered
	 * on bundle stop.
	 * @param k
	 */
	protected void registerObjectHandler(ObjectHandler k) {
		handlers.add(k);
	}

	/**
	 * @see mapObjectToClass
	 */
	protected void mapResRefRegExToClass(final String resRefRegEx, final Class<? extends GameObject> cls) {
		mapObjectToClass(-1, resRefRegEx, null, cls);
	}

	/**
	 * @see mapObjectToClass
	 */
	protected void mapTagRegExToClass(final String tagfRegEx, final Class<? extends GameObject> cls) {
		mapObjectToClass(-1, null, tagfRegEx, cls);
	}

	/**
	 * Maps a GameObject-descendant to the given parameters.
	 * @param objectType The OBJECT_TYPE constant to match against
	 * @param resRefRegEx a (anchored) regular expression that needs to mach the object resRef
	 * @param tagRegEx a (anchored) regular expression that needs to match the object tag
	 * @param cls the class to register
	 */
	protected void mapObjectToClass(final int objectType, final String resRefRegEx,
			final String tagRegEx, final Class<? extends GameObject> cls) {
		registerObjectHandler(new ObjectHandler() {
			private final Pattern resRefPattern = resRefRegEx != null ?
				Pattern.compile(resRefRegEx, Pattern.CASE_INSENSITIVE) : null;
			private final Pattern tagPattern = tagRegEx != null ?
				Pattern.compile(tagRegEx, Pattern.CASE_INSENSITIVE) : null;

			@Override
			public NWObject handleObjectClass(NWObject obj, boolean valid,
					int y, String r, String t) {

				try {
					return (
						(resRefPattern == null || resRefPattern.matcher(r) .matches()) &&
						(tagPattern == null || tagPattern.matcher(t).matches()) &&
						(objectType == -1 || objectType == y)
					) ?	cls.getConstructor(int.class).newInstance(obj.getObjectId()) : null;

				} catch (NoSuchMethodException e) {
					log.error(e);
					return null;
				} catch (IllegalArgumentException e) {
					log.error(e);
					return null;
				} catch (SecurityException e) {
					log.error(e);
					return null;
				} catch (InstantiationException e) {
					log.error(e);
					return null;
				} catch (IllegalAccessException e) {
					log.error(e);
					return null;
				} catch (InvocationTargetException e) {
					log.error(e);
					return null;
				}
			}
		});
	}

}
