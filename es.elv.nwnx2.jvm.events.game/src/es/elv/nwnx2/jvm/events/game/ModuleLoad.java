package es.elv.nwnx2.jvm.events.game;

import es.elv.nwnx2.jvm.hierarchy.GameObject;
import es.elv.nwnx2.jvm.hierarchy.Module;
import es.elv.nwnx2.jvm.hierarchy.events.GameEvent;

public class ModuleLoad extends GameEvent<Module> {
	public ModuleLoad(GameObject actor) {
		super(actor);
	}
}
