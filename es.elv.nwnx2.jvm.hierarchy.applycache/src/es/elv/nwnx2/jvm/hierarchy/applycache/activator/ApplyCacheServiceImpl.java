package es.elv.nwnx2.jvm.hierarchy.applycache.activator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.Scheduler;
import org.nwnx.nwnx2.jvm.SchedulerListener;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import es.elv.nwnx2.jvm.events.game.ModuleLoad;
import es.elv.nwnx2.jvm.hierarchy.ExceptionListener;
import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.applycache.ApplyCacheService;
import es.elv.nwnx2.jvm.hierarchy.applycache.CachedObjectInvalidated;
import es.elv.nwnx2.jvm.hierarchy.applycache.EventLog;
import es.elv.nwnx2.jvm.hierarchy.bundles.Feature;
import es.elv.nwnx2.jvm.hierarchy.bundles.HierarchyService;
import es.elv.nwnx2.jvm.hierarchy.events.Handler;

public class ApplyCacheServiceImpl extends Feature implements ApplyCacheService, ManagedService, ExceptionListener {
	private HierarchyService m_hierarchy;

	private List<GameObject> cache = Collections.synchronizedList(new LinkedList<GameObject>());

	private List<EventLog> eventLog = new LinkedList<EventLog>();

	private int cacheSize = 1000;

	@Override
	protected void load() throws Exception {
		m_hierarchy = (HierarchyService) componentContext.locateService("HierarchyService");
		NWObject.addApplyListener(nwObjectApplyListener);
		Scheduler.addSchedulerListener(schedulerListener);
	}

	@Override
	protected void unload() throws Exception {
		NWObject.removeApplyListener(nwObjectApplyListener);
		Scheduler.removeSchedulerListener(schedulerListener);
		clearCache();
	}

	@Handler void moduleLoad(ModuleLoad e) {
		clearCache();
	}


	final private SchedulerListener schedulerListener = new SchedulerListener() {
		@Override
		public void context(NWObject objSelf) {
			if (eventLog.size() >= 1000)
				eventLog.remove(0);
			eventLog.add(new EventLog(objSelf, EventLog.EventType.CONTEXT, null));
		}

		@Override
		public void event(NWObject objSelf, String event) {
			if (eventLog.size() >= 1000)
				eventLog.remove(0);
			eventLog.add(new EventLog(objSelf, EventLog.EventType.EVENT, event));
		}

		@Override
		public void missedToken(NWObject objSelf, String token) {
			if (eventLog.size() >= 1000)
				eventLog.remove(0);
			eventLog.add(new EventLog(objSelf, EventLog.EventType.MISSEDTOKEN, token));
		}

		@Override
		public void postFlushQueues(int remainingTokens) {
		}
	};


	final private NWObject.ApplyListener nwObjectApplyListener = new NWObject.ApplyListener() {
		@Override
		public void newObject(boolean valid, int objectType, String resRef,
				String tag, NWObject o) {
			synchronized (cache) {
				if (cache.size() >= cacheSize) {
					GameObject removed = cache.remove(0);
					m_hierarchy.sendEvent(new CachedObjectInvalidated(removed));
				}
				if (o instanceof GameObject) {
					GameObject go = (GameObject) o;
					if (go.isCacheable())
						cache.add(0, go);
				}
			}
		}

		@Override
		public void postApply(int oid, boolean valid, int objectType,
				String resRef, String tag, NWObject o) {
		}

		@Override
		public NWObject preApply(int oid, boolean valid, int objectType,
				String resRef, String tag, NWObject o) {

			Iterator<GameObject> i = cache.iterator();
			GameObject match = null;
			while (i.hasNext()) {
				GameObject v = i.next();
				if (v.getObjectId() == oid) {
					m_hierarchy.sendEvent(new CachedObjectInvalidated(v));
					i.remove();
					match = v;
					break;
				}
			}
			if (null != match) {
				cache.add(0, match);
				return match;
			}

			return null;
		}
	};

	@Override
	public void clearCache() {
		synchronized(cache) {
			for (GameObject c : cache)
				m_hierarchy.sendEvent(new CachedObjectInvalidated(c));

			cache.clear();
		}
	}

	@Override
	public List<GameObject> getCache() {
		synchronized(cache) {
			return new LinkedList<GameObject>(cache);
		}
	}

	@Override
	public int getCacheLimit() {
		return cacheSize;
	}

	@Override
	public void setCacheLimit(int newSize) {

		synchronized(cache) {
			if (newSize < cacheSize) {
				List<GameObject> invalidated = cache.subList(newSize, cache.size());
				cache = cache.subList(0, newSize);

				for (GameObject inv : invalidated)
					m_hierarchy.sendEvent(new CachedObjectInvalidated(inv));
			}
		}

		cacheSize = newSize;
	}
	@Override
	public void updated(@SuppressWarnings("rawtypes") Dictionary c) throws ConfigurationException {
		if (c == null)
			return;

		int newSize = (Integer) c.get("cache_size");
		if (newSize < 0) newSize = 0;

		setCacheLimit(newSize);
	}


	@Override
	public void onException(Throwable e, File dumpPath) throws IOException {
		if (null == dumpPath)
			return;

		File cacheDumpStr = new File(dumpPath.getPath() + File.separator + "applyCacheDumpStr");
		FileWriter fw = new FileWriter(cacheDumpStr);
		dumpCacheStr(fw);
		fw.close();

		File eventLogStr = new File(dumpPath.getPath() + File.separator + "eventLog");
		FileWriter fwE = new FileWriter(eventLogStr);
		dumpEventLog(fwE, -1);
		fwE.close();
	}

	private void dumpCacheStr(Writer w) throws IOException {
		synchronized (cache) {
			for (GameObject o : cache) {
				w.write(String.format(
						"oid=%d type=%d class=%s data=%s\n",
						o.getObjectId(), o.objectType(), o.getClass().getName(), o.toString()
				));
			}
		}
	}

	private void dumpEventLog(Writer w, int limit) throws IOException {
		synchronized (eventLog) {
			int i = 0;
			for (EventLog l : eventLog) {
				w.write(String.format(
						"%d t=%s oid=%d %s assoc=[not impl]\n",
						l.ts, l.eventType.toString(), l.objSelf.getObjectId(), l.event
				));

				if (limit >= 0 && i++ > limit)
					break;
			}
		}
	}
}