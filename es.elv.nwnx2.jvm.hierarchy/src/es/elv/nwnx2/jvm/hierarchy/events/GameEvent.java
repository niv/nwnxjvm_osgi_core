package es.elv.nwnx2.jvm.hierarchy.events;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import es.elv.nwnx2.jvm.hierarchy.GameObject;



public abstract class GameEvent<T extends GameObject> extends CoreEvent {
	final public GameObject actor;

	protected GameEvent(GameObject actor) {
		this.actor = actor;
	}

	@Override
	public String toString() {
		StringBuilder fields = new StringBuilder();
		for (Field f : this.getClass().getFields()) {
			if (Modifier.isPublic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())) {
				try {
					fields.append(f.getName());
					fields.append("=");
					fields.append(f.get(this));
					fields.append(" ");
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return this.getClass().getName() + ": " + fields.toString().trim();
	}
}