package es.elv.nwnx2.jvm.hierarchy.activator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.nwnx.nwnx2.jvm.ExceptionHandler;
import org.nwnx.nwnx2.jvm.NWEffect;
import org.nwnx.nwnx2.jvm.NWItemProperty;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.SchedulerListener;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import es.elv.nwnx2.jvm.hierarchy.ExceptionListener;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.bundles.Feature;
import es.elv.nwnx2.jvm.hierarchy.bundles.HierarchyService;
import es.elv.nwnx2.jvm.hierarchy.events.CoreEvent;
import es.elv.nwnx2.jvm.hierarchy.events.EventListener;
import es.elv.nwnx2.jvm.hierarchy.events.EventResolveException;
import es.elv.nwnx2.jvm.hierarchy.events.EventResolverService;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class HierarchyServiceImpl extends Feature implements ManagedService, HierarchyService {
	private EventAdmin m_eventAdmin;

	@Override
	protected void load() throws Exception {
		m_eventAdmin = (EventAdmin) componentContext.locateService("EventAdmin");

		NWObject.setObjectInvalidIsNull(true);

		Scheduler.addSchedulerListener(schedulerListener);

		Scheduler.registerExceptionHandler(exceptionHandler);
		NWObject.registerExceptionHandler(exceptionHandler);
		NWEffect.registerExceptionHandler(exceptionHandler);
		NWItemProperty.registerExceptionHandler(exceptionHandler);

		loadedOn = System.currentTimeMillis();
	}

	@Override
	protected void unload() throws Exception {
		loadedOn = -1;

		Scheduler.unregisterExceptionHandler(exceptionHandler);
		NWObject.unregisterExceptionHandler(exceptionHandler);
		NWEffect.unregisterExceptionHandler(exceptionHandler);
		NWItemProperty.unregisterExceptionHandler(exceptionHandler);

		Scheduler.removeSchedulerListener(schedulerListener);
	}

	private String exceptionDumpDirectory = null;

	private long contextSwitchCount = 0;
	private long loadedOn = -1;

	private void handleException(final Throwable e) {
		log.error(e);

		File dumpPathFile = null;
		if (exceptionDumpDirectory != null && !exceptionDumpDirectory.equals("")) {

			String dumpPath = exceptionDumpDirectory + File.separator +
				System.currentTimeMillis() + "_" + e.getClass().getName();
			dumpPathFile = new File(dumpPath);
			for (int i = 0; dumpPathFile.exists(); i++)
				dumpPathFile = new File(dumpPath + "_" + i);

			if (!dumpPathFile.mkdir()) {
				System.err.println("Cannot create target directory for dump: " + dumpPathFile.toString());
				return;
			}

			try {
				FileWriter w = new FileWriter(dumpPath + File.separator + "stackTrace");
				PrintWriter pw = new PrintWriter(w);
				try {
					e.printStackTrace(pw);

				} finally {
					w.close();
				}

			} catch (IOException exx) {
				System.err.println(exx.getMessage() + " while trying to dump exception data");
			}
		}

		for (ExceptionListener el : locateServicesFor(ExceptionListener.class)) {
			try {
				el.onException(e, dumpPathFile);
			} catch (Exception ea) {
				log.error("In ExceptionListener", ea);
			}
		}
	}

	private ExceptionHandler exceptionHandler = new ExceptionHandler() {
		@Override
		public boolean handleException(Exception e) {
			HierarchyServiceImpl.this.handleException(e);
			return true;
		}
	};

	private SchedulerListener schedulerListener = new SchedulerListener() {
		@Override
		public void postFlushQueues(int remainingTokens) {
		}

		@Override
		public void missedToken(NWObject objSelf, String token) {
		}

		@Override
		public void context(NWObject objSelf) {
			contextSwitchCount++;
		}

		@Override
		public void event(NWObject objSelf, String event) {
			if (!(objSelf instanceof GameObject)) {
				log.warn("objSelf not a GameObject, cannot factory events for: " + objSelf);
				return;
			}

			if (m_eventAdmin != null) {
				Dictionary<String, Object> properties = new Hashtable<String, Object>();
				properties.put("objectSelf", objSelf);
				properties.put("event", event);
				Event x = new Event("org/nwnx/nwnx2/jvm/osgi/event", properties);
				try{
					m_eventAdmin.sendEvent(x);
				} catch (RuntimeException ee) {
					handleException(ee);
				}

			} else {
				System.err.println("Cannot get EventAdmin service, cannot publish event.");
			}

			// int resolvedEventCount = 0;
			for (EventResolverService r : locateServicesFor(EventResolverService.class)) {
				try {

					List<GameEvent<? extends GameObject>> resolvedEvents =
						r.resolve((GameObject) objSelf, event);
					// resolvedEventCount += resolvedEvents.size();

					for (GameEvent<? extends GameObject> v : resolvedEvents)
						try {
							sendEventInternal(v);
						} catch (RuntimeException e) {
							handleException(e);
						}

				} catch (EventResolveException ee) {
					handleException(ee);

				} catch (RuntimeException ee) {
					handleException(ee);
				} catch (Error ee) {
					handleException(ee);
				}
			}
			//if (resolvedEventCount == 0)
			//	log.debug("Cannot resolve event " + event + " on " + objSelf.getObjectId());

			try {
				Scheduler.flushQueues();
			} catch (RuntimeException ee) {
				handleException(ee);
			} catch (Error ee) {
				handleException(ee);
			}
		}
	};

	@Override
	public void updated(@SuppressWarnings("rawtypes") Dictionary c) throws ConfigurationException {
		if (c == null)
			return;

		exceptionDumpDirectory = (String) c.get("exception_dump_directory");
	}

	@Override
	public long getContextSwitchCount() {
		return contextSwitchCount;
	}

	@Override
	public long getStartedOn() {
		return loadedOn;
	}
	private void sendEventInternal(CoreEvent e) {
		for (EventListener ll : locateServicesFor(EventListener.class))
			try {
				ll.handleEvent(e);
			} catch (RuntimeException ee) {
				handleException(ee);
			} catch (Error ee) {
				handleException(ee);
			}
	}

	@Override
	public void sendEvent(CoreEvent e) {
		sendEventInternal(e);
	}
}
