package es.elv.nwnx2.jvm.hierarchy.webconsole.activator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWObject.ApplyListener;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.applycache.ApplyCacheService;
import es.elv.nwnx2.jvm.hierarchy.bundles.Feature;
import es.elv.nwnx2.jvm.hierarchy.bundles.HierarchyService;
import es.elv.nwnx2.jvm.hierarchy.events.CoreEvent;
import es.elv.nwnx2.jvm.hierarchy.events.Handler;

public class Impl extends Feature implements Servlet, ApplyListener, ManagedService {
	private HierarchyService hs;
	private ApplyCacheService as;

	private Map<String, Long> objectClassStatistics =
		Collections.synchronizedMap(new TreeMap<String, Long>());

	private Map<String, Long> eventStatistics =
		Collections.synchronizedMap(new TreeMap<String, Long>());

	private float a = 0.0125f;
	private float ctxLastSecSmoothed = -1;
	private long ctxLastSec = -1;


	@SuppressWarnings("unchecked")
	@Override
	public void updated(Dictionary c) throws ConfigurationException {
		if (null == c)
			return;

		if (c.get("smoothing_alpha") != null)
			a = (Float) c.get("smoothing_alpha");
		if (a > 1.0)
			a = 1.0f;
		if (a < 0)
			a = 0.0f;
	}

	final private Timer timer = new Timer();

	public void load() {
		hs = (HierarchyService) componentContext.locateService("HierarchyService");
		as = (ApplyCacheService) componentContext.locateService("ApplyCacheService");
		NWObject.addApplyListener(this);
		timer.scheduleAtFixedRate(task, 0, 1000);
	}

	public void unload() {
		NWObject.removeApplyListener(this);
		timer.cancel();
		timer.purge();
	}

	final private TimerTask task = new TimerTask() {
		private long ctxPrevious = -1L;

		@Override
		public void run() {
			long ctxNow = hs.getContextSwitchCount();
			if (ctxPrevious == -1)
				ctxPrevious = ctxNow;
			ctxLastSec = ctxNow - ctxPrevious;
			if (ctxLastSecSmoothed == -1)
				ctxLastSecSmoothed = ctxLastSec;
			ctxLastSecSmoothed = a * ctxLastSec + (1f - a) * ctxLastSecSmoothed;
			ctxPrevious = ctxNow;
		}
	};

	@Override
	public void destroy() {
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
	}

	@Override
	synchronized public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {

		PrintWriter w = response.getWriter();
		final List<GameObject> cache = as.getCache();

		w.write("<pre>");

		synchronized(cache) {
			w.write("Hierarchy activated on: " +  DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL).format(new Time(hs.getStartedOn())) + "\n");
			w.write("Context switches occured: " + hs.getContextSwitchCount() + "\n");
			w.write("Context switches per second (&#945; = " + a + "): " + ctxLastSecSmoothed + "\n");
			w.write("Cache Pressure: " + cache.size() + "/" + as.getCacheLimit() + "\n\n");

			w.write("Cache contents:\n");
			Map<String, Long> count = new TreeMap<String, Long>();
			for (NWObject c : cache) {
				if (!count.containsKey(c.getClass().getName()))
					count.put(c.getClass().getName(), 0L);
				count.put(c.getClass().getName(), count.get(c.getClass().getName()) + 1);
			}

			for (Entry<String, Long> v : count.entrySet()) {
				w.write(String.format(" %8d %s\n", v.getValue(), v.getKey()));
			}

			w.write("\n");
			w.write("Factored NWObjects:\n");
			synchronized (objectClassStatistics) {
				for (Entry<String, Long> e : objectClassStatistics.entrySet()) {
					w.write(String.format("  %8d %s\n", e.getValue(), e.getKey()));
				}
			}

			w.write("\n");
			w.write("Factored CoreEvents:\n");
			synchronized (eventStatistics) {
				for (Entry<String, Long> e : eventStatistics.entrySet()) {
					w.write(String.format("  %8d %s  (%f / second)\n", e.getValue(), e.getKey(),
							(float) e.getValue() / (float) ((System.currentTimeMillis() - hs.getStartedOn()) / 1000)));
				}
			}

		}

		w.write("</pre>");
	}

	@Override
	public void newObject(boolean valid, int objectType, String resRef,
			String tag, NWObject o) {
		String className = o.getClass().getName();
		if (!objectClassStatistics.containsKey(className))
			objectClassStatistics.put(className, 0L);
		objectClassStatistics.put(className, objectClassStatistics.get(className) + 1);
	}

	@Override
	public void postApply(int oid, boolean valid, int objectType,
			String resRef, String tag, NWObject o) {
	}

	@Override
	public NWObject preApply(int oid, boolean valid, int objectType,
			String resRef, String tag, NWObject o) {
		return null;
	}


	@Handler void onEvent(CoreEvent v) {
		String className = v.getClass().getName();
		if (!eventStatistics.containsKey(className))
			eventStatistics.put(className, 0L);

		eventStatistics.put(className, eventStatistics.get(className) + 1);
	}


}
