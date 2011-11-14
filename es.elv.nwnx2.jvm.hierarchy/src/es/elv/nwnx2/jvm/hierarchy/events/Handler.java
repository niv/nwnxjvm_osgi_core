package es.elv.nwnx2.jvm.hierarchy.events;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import es.elv.nwnx2.jvm.hierarchy.GameObject;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Handler {
	/**
	 * Only run on events where the actor matches the given class.
	 */
	public Class<? extends GameObject>[] value() default { GameObject.class };
}
