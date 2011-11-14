/**
 *
 */
package es.elv.nwnx2.jvm.hierarchy.applycache;

import org.nwnx.nwnx2.jvm.NWObject;

public class EventLog {
	public enum EventType { TOKEN, EVENT, MISSEDTOKEN, CONTEXT };

	final public long ts;
	final public NWObject objSelf;
	final public String event;
	final public EventType eventType;

	public EventLog(NWObject objSelf, EventType etype, String event) {
		ts = System.currentTimeMillis();
		this.objSelf = objSelf;
		this.event = event;
		this.eventType = etype;
	}

}