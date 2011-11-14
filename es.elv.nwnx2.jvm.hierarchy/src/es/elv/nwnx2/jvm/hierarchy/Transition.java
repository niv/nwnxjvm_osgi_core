package es.elv.nwnx2.jvm.hierarchy;

import org.nwnx.nwnx2.jvm.NWScript;


abstract public class Transition extends GameObject {
	public Transition(int oid) {
		super(oid);
	}

	public Transition getTransitionTarget() {
		return (Transition) NWScript.getTransitionTarget(this);
	}
}
