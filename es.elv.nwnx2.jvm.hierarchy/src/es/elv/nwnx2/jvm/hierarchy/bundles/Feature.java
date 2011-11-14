package es.elv.nwnx2.jvm.hierarchy.bundles;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.nwnx.nwnx2.jvm.ScheduledAnon;
import org.nwnx.nwnx2.jvm.ScheduledEvery;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

import es.elv.nwnx2.jvm.hierarchy.events.CoreEvent;
import es.elv.nwnx2.jvm.hierarchy.events.EventListener;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;
import es.elv.nwnx2.jvm.hierarchy.events.Handler;
import es.elv.osgi.base.SCRHelper;

public abstract class Feature extends SCRHelper implements EventListener {
	private Set<ScheduledEvery> scheds = new HashSet<ScheduledEvery>();
	private ServiceRegistration el;

    @Override
    protected void activate(ComponentContext ctxt) throws Exception {
    	this.componentContext = ctxt;
    	this.bundleContext = ctxt.getBundleContext();

		el = bundleContext.registerService(EventListener.class.getName(), this, null);

		try {
			Every every = null;
			final Object invokeOn = this;
			for (final Method m : getClass().getDeclaredMethods())
				if ((every = m.getAnnotation(Every.class)) != null) {
					ScheduledEvery sched = new ScheduledEvery(every.interval(),
						new ScheduledAnon() {
							@Override
							public void e(final ScheduledEvery x) {
								m.setAccessible(true);
								try {
									if (m.getParameterTypes().length == 1)
										m.invoke(invokeOn, x);
									else if (m.getParameterTypes().length == 0)
										m.invoke(invokeOn);
								} catch (IllegalArgumentException e1) {
									log.error(e1);
								} catch (IllegalAccessException e1) {
									log.error(e1);
								} catch (InvocationTargetException e1) {
									log.error(e1);
								}
							}
					}, every.policy());
					sched.setTiming(every.timing());
					every(sched);
				}

		} catch (Exception e) {

			for (ScheduledEvery se : scheds)
				if (!se.isStopped()) se.stop();
			scheds.clear();

			el.unregister();

			throw e;
		}

		super.activate(ctxt);
    }


    @Override
    protected void deactivate(ComponentContext ctxt) throws Exception {
		for (ScheduledEvery se : scheds)
			if (!se.isStopped()) se.stop();
		scheds.clear();

		el.unregister();

    	super.deactivate(ctxt);
    }


	/**
	 * Schedules the given ScheduledEvery and automatically stops it when the current
	 * bundle is stopped.
	 * @param sched
	 */
	protected void every(ScheduledEvery sched) {
		scheds.add(sched);
		Scheduler.every(sched);
	}

	@Override
	final public void handleEvent(CoreEvent event) {
		Handler handler;

		for (Method m : getClass().getDeclaredMethods())
			if ((handler = m.getAnnotation(Handler.class)) != null) {
				boolean assignable = false;
				
				for (Class<?> c : handler.value())
					if (event instanceof GameEvent<?>) {
						if (c.isAssignableFrom(((GameEvent<?>)event).actor.getClass())) {
							assignable = true;
							break;
						}
							
						/* always allow CoreEvents to be @Handlered since they don't have an actor to match */
					} else if (event instanceof CoreEvent)
							assignable = true;
				
				if (!assignable)
					continue;

				if(m.getReturnType() != void.class)
					continue;

				Class<?>[] types = m.getParameterTypes();

				if (types.length != 1)
					continue;

				if (!types[0].isAssignableFrom(event.getClass()))
					continue;

				try {
					m.setAccessible(true);
					m.invoke(this, event);
				} catch (Exception ex) {
					log.error(ex);
				}
			}
	}
}
